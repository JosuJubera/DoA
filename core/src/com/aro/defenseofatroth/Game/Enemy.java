package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.BarraVida;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    protected Array<Vector2> ruta; //ruta a seguir por la unidad



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

    public void setCuerpo(Body cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
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
        if (destino.x!=posicion.x && destino.y!=posicion.y) {
            angulo = MathUtils.atan2(destino.y - posicion.y, destino.x - posicion.x); //obtengo el angulo a seguir para el destino
            velocidad.x = MathUtils.cos(angulo) * velocidadM; //fisica de 1º
            velocidad.y = MathUtils.sin(angulo) * velocidadM;
            cuerpo.setLinearVelocity(velocidad); //añadimos la velocidad al cuerpo
            this.destino = destino;
        }
        //TODO poner la animacion que sea segun la rotacion

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
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
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

    public void daniar(int danio){
        this.vida-=danio;
        if (vida<=0){
            viva=false;
        }
        barraVida.setValor(vida/vidaMaxima);
    }

    @Override
    public void draw(Batch batch, float delta){
        animationTime+=delta;
        batch.draw(animacionActual.getKeyFrame(animationTime),posicion.x,posicion.y);
    }

    @Override
    public void act(float delta){
        if (viva){ //esta viva, actualizamos posicion
            posicion=cuerpo.getPosition();
            super.setPosition(posicion.x,posicion.y);
            if (posicion.x==destino.x && posicion.x==posicion.y){ //comprobamos si llegamos al destino (tambien se puede calcular usando la distancia)
                if (ruta!=null && ruta.size>0) {
                    setDestino(ruta.pop()); //ponemos como destino el siguiente punto de la ruta
                }else{ //hemos llegado al final de la ruta
                    velocidad.setZero();
                    cuerpo.setLinearVelocity(velocidad); //recordard, la velocidad la lleva el CUERPO
                }
            }
        }else{ //esta muerta, si ya ha pasado su tiempo de animacion, se libera
            if (animacionMuerte.isAnimationFinished(animationTime+delta)){
                poolOrigen.remove(this); //nos liberamos
            }
        }
    }

    @Override
    public void reset() {
        //Animaciones, Cuerpos y Pools no se libera, por que son constantes a todos los enemigos (salvo que algun hijo lso modifique)
        super.remove();//ya no se dibuja
        cuerpo.setLinearVelocity(0f,0f);
        cuerpo.setTransform(0f,0f,0f);
        cuerpo.setAngularVelocity(0f); //no queremos que gire sobre si mismo
        cuerpo.setLinearDamping(0f); //resistencia
        posicion.setZero();
        velocidad.setZero();
        destino.setZero();
        velocidadM=0;
        vida=vidaMaxima;
        barraVida.setValor(1f);
    }
}
