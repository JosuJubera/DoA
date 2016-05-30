package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * Esta clase representa a un proyectil disparado desde una torre basica
 * Created by Sergio on 17/04/2016.
 */
public class BasicTowerProyectile extends Proyectile{
    ParticleEffect particleEffect;

    public void setParticleEffect() {
        this.particleEffect =new ParticleEffect();
        particleEffect.load(Gdx.files.internal("data/explosion.particle"), Gdx.files.internal("data"));
        this.particleEffect.getEmitters().first().setContinuous(true);
        this.particleEffect.scaleEffect(10f);
        this.particleEffect.start();
    }

    @Override
    public void draw(Batch batch, float delta){
        particleEffect.setPosition(posicion.x, posicion.y);
        batch.end();
        batch.begin();
        particleEffect.draw(batch,delta);
        if (particleEffect.isComplete()){
            particleEffect.reset();
        }
    }
}
