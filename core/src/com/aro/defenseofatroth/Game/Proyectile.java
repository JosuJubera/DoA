package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

/**
 * Esta clase representa a un proyectil. Las clases hijas deberan especificar que tipo de proyectil es.
 * Created by Sergio on 17/04/2016.
 */
public class Proyectile extends Actor implements Pool.Poolable {
    private ObjectPool<Proyectile> poolOrigen;
    public static short PROYECTILE_BIT=0x08;
    private TextureRegion textura;
    private Enemy enemigo;
    private int danio;
    protected Body cuerpo;
    protected Vector2 posicion;
    protected Vector2 velocidad;
    protected float velocidadM;
    private float angulo;
    private boolean dibujable;// que niapa mas grande

    public void setCuerpo(Body cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void setPoolOrigen(ObjectPool<Proyectile> poolOrigen) {
        this.poolOrigen = poolOrigen;
    }

    public void setTextura(TextureRegion textura) {
        this.textura = textura;
        dibujable=true;
    }

    public void setVelocidad(Vector2 velocidad) {
        this.velocidad = velocidad;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }

    public Enemy getEnemigo() {
        return enemigo;
    }

    public void setEnemigo(Enemy enemigo) {
        this.enemigo = enemigo;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public Vector2 getPosicion() {
        return posicion;
    }


    public float getVelocidadM() {
        return velocidadM;
    }

    public void setVelocidadM(float velocidadM) {
        this.velocidadM = velocidadM;
    }

    @Override
    public void draw(Batch batch, float delta){
        if (dibujable) {
            batch.draw(textura, posicion.x, posicion.y);
        }
    }
    @Override
    public void act(float delta){
        //Calculo de la direccion
        posicion=cuerpo.getPosition();
        super.setPosition(posicion.x,posicion.y); //creo k no es necesario pero bueno
        Vector2 enemyPos=enemigo.getPosicion();
        angulo = MathUtils.atan2(enemyPos.y - posicion.y, enemyPos.x - posicion.x);
        velocidad.x = MathUtils.cos(angulo) * velocidadM;
        velocidad.y = MathUtils.sin(angulo) * velocidadM;
        cuerpo.setLinearVelocity(velocidad);
        //Si esta lo suficientemente cerca
        if (posicion.dst(enemyPos)<=20f*velocidadM*delta){
            enemigo.daniar(1);
            this.liberar();
        }
    }
    @Override
    public void reset() {
        this.danio=0;
        this.enemigo=null;
        cuerpo.setLinearVelocity(0f, 0f);
        cuerpo.setTransform(0f,0f,0f);
        posicion.setZero();
        velocidad.setZero();
        velocidadM=0;
        dibujable=false;
    }
    public void liberar(){
        super.remove();
        poolOrigen.remove(this);
    }
}
