package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Esta clase representa a un proyectil disparado desde una torre basica
 * Created by Sergio on 17/04/2016.
 */
public class BasicTowerProyectile extends Proyectile{
    ParticleEffect particula;
    Sprite aux;

    public void setParticleEffect() {
        this.particula =new ParticleEffect();
        particula.load(Gdx.files.internal("data/explosion.particle"), Gdx.files.internal("data"));
        this.particula.getEmitters().first().setContinuous(true);
        this.particula.scaleEffect(10f);
        this.particula.start();
        aux=new Sprite(TextureLoader.getInstance().obtenerBarraRoja());
    }

    @Override
    public void draw(Batch batch, float delta){
        aux.setPosition(posicion.x,posicion.y);
        particula.getEmitters().first().setPosition(posicion.x,posicion.y);
        aux.draw(batch);
        particula.draw(batch, delta);
        if (particula.isComplete()){
            particula.reset();
        }
    }
}
