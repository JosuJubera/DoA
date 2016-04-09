package com.aro.defenseofatroth;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

/**
 * Esta clase representa a toda cosa que sea dibujable. En el futuro es posible que herede de Actor
 * y se usen las Actions, pero ya veremos
 * Created by Sergio on 22/03/2016.
 */
public abstract class Entidad implements Disposable{

    public Sprite texture; //guarda la textura sin mas
    public Animation animacion; //guarda la animacion si tiene
    protected Vector2 posicion; //posicion del objeto
    protected Vector2 velocidad; //velocidad del objeto
    protected Vector2 aceleracion; //aceleracion del objeto
    protected Vector2 destino;
    protected float velocidadM;// modulo de la velocidad


    Entidad(){
        texture=null;
        animacion=null;
        destino=new Vector2(0f,0f);
        posicion=new Vector2(0f,0f);
        velocidad=new Vector2(0f,0f);
        aceleracion=new Vector2(0f,0f);
    }

    public void setTexture(TextureRegion text){
        texture.setRegion(text);
    }

    /**
     *  Dibuja la entidad en el batch. Este se ha de llamar dentro de batch.begin()
     * @param batch donde dibujar
     * @param delta tiempo
     */
    public  abstract void draw(Batch batch,float delta);

    public void setAceleracion(Vector2 acel){
        this.aceleracion=acel;
    }

    public Vector2 getAceleracion() {
        return aceleracion;
    }

    public void setVelocidad(Vector2 velocidad){
        this.velocidad=velocidad;
    }
    public Vector2 getVelocidad(){
        return this.velocidad;
    }
    public void setPosicion(Vector2 posicion){
        this.posicion=posicion;
    }

    public Vector2 getPosicion(){
        return this.posicion;
    }

    public Vector2 getDestino(){
        return this.destino;
    }

    public void setDestino(Vector2 destino){
        float angulo= MathUtils.atan2(destino.y - posicion.y, destino.x - posicion.x); //obtengo el angulo a seguir para el destino
        velocidad.x=MathUtils.cos(angulo)*velocidadM; //fisica de 1º
        velocidad.y=MathUtils.sin(angulo)*velocidadM;
    }
    public void setVelocidad(float velocidad){
        this.velocidadM=velocidad;
    }
}
