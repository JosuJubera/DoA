package com.aro.defenseofatroth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sergio on 16/03/2016.
 */
public class Unidad extends Entidad {
    private int vidaMaxima=100;
    private int vidaActual=100;
    private NinePatch barraVidaFondo;
    private NinePatch barraVidaDelante;

    TextureRegion broja;
    TextureRegion bverde;
    private float animationTime=0;
    public Unidad(){
        super();
        //new NinePatch(new TextureRegion(texture, x, y, width, height), left, right, top, bottom);
        // De la roja: ancho: 36 47 alto 39 47
        broja=new TextureRegion(new Texture(Gdx.files.internal("barraRoja.png")));
        bverde=new TextureRegion(new Texture(Gdx.files.internal("barraVerde.png")));
        barraVidaFondo=new NinePatch(broja, 0, 0, 0, 0); //es una prueba
        barraVidaDelante=new NinePatch(bverde,0,0,0,0);
    }

    @Override
    public void draw(Batch batch, float delta) {
        this.actuar(delta);
        animationTime+=delta;
        TextureRegion animaconFrame = animacion.getKeyFrame(animationTime);
        batch.draw(animaconFrame,posicion.x,posicion.y);
        barraVidaFondo.draw(batch,posicion.x,posicion.y,vidaMaxima,10);
        barraVidaDelante.draw(batch,posicion.x,posicion.y,vidaActual,10);

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
    }
    protected void actuar(float delta){
        posicion.x+=1f;

    }
}
