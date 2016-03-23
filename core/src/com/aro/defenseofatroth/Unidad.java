package com.aro.defenseofatroth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sergio on 16/03/2016.
 */
public class Unidad extends Entidad {
    private int vidaMaxima;
    private int vidaActual;
    private NinePatch barraVidaFondo;
    private NinePatch barraVidaDelante;

    TextureRegion broja;
    private float animationTime=0;
    public Unidad(){
        //new NinePatch(new TextureRegion(texture, x, y, width, height), left, right, top, bottom);
        // De la roja: ancho: 36 47 alto 39 47
        broja=new TextureRegion(new Texture(Gdx.files.internal("barraRoja.png")));
        barraVidaFondo=new NinePatch(broja, 35, 35, 39, 39); //es una prueba
    }

    @Override
    public void draw(Batch batch, float delta) {
        animationTime+=delta;
        TextureRegion animaconFrame = animacion.getKeyFrame(animationTime);
        batch.draw(animaconFrame,posicion.x,posicion.y);
        barraVidaFondo.draw(batch,0,0,300,30);
        //batch.draw(broja,0,0);

    }

    @Override
    public void dispose() {
        //Si hay k limpiar algo, aqui ira
    }
}
