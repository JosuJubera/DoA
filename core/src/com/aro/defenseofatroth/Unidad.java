package com.aro.defenseofatroth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sergio on 16/03/2016.
 */
public class Unidad extends Entidad {
    private int vidaMaxima=100;
    private int vidaActual=100;
    private BarraVida barraVida;
    private float animationTime=0;
    private float velocidadM;// modulo de la velocidad
    public Unidad(){
        super();
        barraVida=new BarraVida();
        barraVida.setPosition(posicion);
    }

    @Override
    public void draw(Batch batch, float delta) {
        this.actuar(delta); //si se decide extender de Actor, este seria el metodo act
        animationTime+=delta;
        TextureRegion animaconFrame = animacion.getKeyFrame(animationTime);
        batch.draw(animaconFrame,posicion.x,posicion.y);
        barraVida.draw(batch, delta); //tambien podria dibujarlo en el bucle principal... ya veremos k hacemos

    }

    @Override
    public void dispose() {
        //Si hay k limpiar algo, aqui ira
    }
    public void danar(int dano){
        this.vidaActual-=dano;
        if (vidaActual<0){
            vidaActual=vidaMaxima;
        }
        barraVida.setValor((float)vidaActual);
    }
    protected void actuar(float delta){
        posicion.x+=velocidad.x*delta;
        posicion.y+=velocidad.y*delta;

    }
    //Kizas esto deberia ir en la clase superior... pero no se
    @Override
    public void setDestino(Vector2 destino){
        float angulo=MathUtils.atan2(destino.y-posicion.y,destino.x-posicion.x); //obtengo el angulo a seguir para el destino
        velocidad.x=MathUtils.cos(angulo)*velocidadM; //fisica de 1º
        velocidad.y=MathUtils.sin(angulo)*velocidadM;


    }
    public void setVelocidad(float velocidad){
        this.velocidadM=velocidad;
    }
}
