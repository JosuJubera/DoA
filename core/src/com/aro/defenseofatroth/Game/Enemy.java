package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.BarraVida;
import com.aro.defenseofatroth.Screens.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Esta clase representa a un enemigo en el juego. Las clases hijas deben especificar que clase de
 * enemigo es y sus atributos y habilidades. Los enemigos son generados por el generadordeEnemigos
 * Created by Sergio on 17/04/2016.
 */
public class Enemy extends Actor implements Pool.Poolable {
    private ObjectPool<Enemy> poolOrigen; //Esto es para poder liberarlo cuando no se use mas
    public static short ENEMY_BIT=0x02; //Bits de mascara que tendran las unidades
    //Solo hay animaciones, no hay una imagen fija
    //Animaciones del enemigo en movimiento
    protected Animation animacionHorizontal;
    protected Animation animacionVerticalSup;
    protected Animation animacionVerticalInf;
    protected Animation animacionDiagonalSup;
    protected Animation animacionDiagonalInf;
    protected Animation animacionMuerte;
    private Animation animacionActual; //animacion actual
    private boolean flip; //Si hay que girar la animacion
    protected float animationTime; //se usa para cojer el frame de la animacion correcto
    private float angulo;

    protected Body cuerpo; //cuerpo del enemigo en el mundo
    protected Vector2 posicion; //posicion del enemigo en el mundo. Este dato se obtiene del Body, y hay que actualizalo en Actor
    protected Vector2 velocidad; //este valor se calcula segun el destino. Hay que actualizarlo en body.
    protected Vector2 destino; //Destino del enemigo
    protected float velocidadM; //Velocidad del enemigo en modulo
    protected int vida; //vida del enemigo
    protected int vidaMaxima; //vida maxima del enemigo
    protected BarraVida barraVida; //barra de vida del enemigo
    protected boolean viva; //si la unidad esta viva
    protected Array<Vector2> ruta; //ruta a seguir por la unidad. Este valor es FINAL. NO SE DEBE CAMBIAR
    private int posicionEnRuta;
    protected int money; //Dinero que deja al morir

    Enemy(){
        posicion=new Vector2(0,0);
        velocidad=new Vector2(0,0);
        destino=new Vector2(0,0);
    }

    public void setPosicionEnRuta(int posicionEnRuta) {
        if (ruta!=null && posicionEnRuta<=ruta.size-1) {
            this.posicionEnRuta = posicionEnRuta;
            this.setDestino(ruta.get(posicionEnRuta));
        }
    }

    public void setBarraVida(BarraVida barraVida) {
        this.barraVida = barraVida;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public void setAnimacionHorizontal(Animation animacionHorizontal) {
        this.animacionHorizontal = animacionHorizontal;
    }


    public void setAnimacionVerticalSup(Animation animacionVerticalSup) {
        this.animacionVerticalSup = animacionVerticalSup;
    }

    public void setAnimacionVerticalInf(Animation animacionVerticalInf) {
        this.animacionVerticalInf = animacionVerticalInf;
    }

    public void setAnimacionDiagonalSup(Animation animacionDiagonalSup) {
        this.animacionDiagonalSup = animacionDiagonalSup;
    }
    public void setAnimacionDiagonalInf(Animation animacionDiagonalInf) {
        this.animacionDiagonalInf = animacionDiagonalInf;
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public void setRuta(Array<Vector2> ruta) {
        this.ruta =ruta;
    }

    public void setCuerpo(Body cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
        cuerpo.setTransform(posicion,0);
        super.setPosition(posicion.x,posicion.y);
    }

    public Vector2 getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Vector2 velocidad) {
        cuerpo.setLinearVelocity(velocidad);
        this.velocidad = velocidad;
    }

    public Vector2 getDestino() {
        return destino;
    }

    public void setDestino(Vector2 destino) {
        if ((destino.x!=posicion.x) && destino.y!=posicion.y) { //sino el atan2 falla
            angulo = MathUtils.atan2(destino.y - posicion.y, destino.x - posicion.x); //obtengo el angulo a seguir para el destino
            velocidad.x = MathUtils.cos(angulo) * velocidadM; //fisica de 1º
            velocidad.y = MathUtils.sin(angulo) * velocidadM;
            cuerpo.setLinearVelocity(velocidad); //añadimos la velocidad al cuerpo
            this.destino = destino;
            animacionActual=animacionHorizontal;

        }else{//estoy en el destino, paso al siguiente punto de la ruta
            avanzar();
        }

        //TODO poner la animacion que sea segun la rotacion (si se añaden mas animaciones)
    animacionActual=animacionHorizontal;
    }

    private void avanzar() {
        if ((ruta ==null) || (posicionEnRuta>=(ruta.size-1))){//Estamos en el final, nos liberamos
            velocidad.setZero();
            cuerpo.setLinearVelocity(velocidad);
            //this.liberar();
        }else{
            posicionEnRuta++;
            setPosicionEnRuta(posicionEnRuta);
        }
    }

    public float getVelocidadM() {
        return velocidadM;
    }

    public void setVelocidadM(float velocidadM) {
        this.velocidadM = velocidadM;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
        if (vidaMaxima!=0){
        barraVida.setValor(((float)vida)/((float)vidaMaxima));
        }
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
        if (vidaMaxima!=0){
            barraVida.setValor(((float)vida)/((float)vidaMaxima));
        }
    }
    public void setPoolOrigen(ObjectPool<Enemy> origen){
        this.poolOrigen=origen;
    }


    public void setAnimacionMuerte(Animation animacionMuerte) {
        this.animacionMuerte = animacionMuerte;
    }

    public boolean isViva() {
        return viva;
    }


    public void setViva(boolean viva) {
        this.viva = viva;
    }

    public void dainar(int daino){
        if (vida>0) {
            this.vida -= daino;
        }
        if (vida<=0){
            viva=false;
            vida=0;
        }
        barraVida.setValor(((float) vida )/((float) vidaMaxima));
    }

    @Override
    public void draw(Batch batch, float delta){
        animationTime+=delta;
        TextureRegion fotograma=animacionActual.getKeyFrame(animationTime);

        batch.draw(fotograma,posicion.x-fotograma.getRegionWidth()*0.5f,posicion.y-fotograma.getRegionHeight()*0.5f);
        barraVida.draw(batch,delta);
    }

    @Override
    public void act(float delta){

        if (viva){ //esta viva, actualizamos posicion
            //Creo k esto soluciona una setButtonUpdate
            //this.setDestino(destino);
            //
            posicion=cuerpo.getPosition();
            super.setPosition(posicion.x,posicion.y);
            barraVida.setPosition(posicion);
            if (posicion.dst(destino)<(50f+velocidadM*delta)){ //comprobamos si llegamos al destino (tambien se puede calcular usando la distancia)
                   avanzar();
            }
        }else{ //esta muerta, si ya ha pasado su tiempo de animacion, se libera
           // if (animacionMuerte.isAnimationFinished(animationTime+delta)){
               this.liberar();//liberamos los recursos
           // }
        }
    }
    public void liberar(){
        Hud.addGold(money);
        super.remove(); //no se dibuja
        poolOrigen.remove(this);
    }

    @Override
    public void reset() {
        //Animaciones, Cuerpos y Pools no se libera, por que son constantes a todos los enemigos (salvo que algun hijo lso modifique)
        //super.remove();//ya no se dibuja
        cuerpo.setLinearVelocity(0f,0f);
        cuerpo.setTransform(0f,0f, 0f);
        posicion.setZero();
        velocidad.setZero();
        this.destino=null;
        velocidadM=0;
        viva=false;
        posicionEnRuta=0;
        animationTime=0;
        animacionActual=null;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override //pa debugear
    public String toString(){
        return "Soy un enemigo";
    }
}
