package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * Esta clase representa a un proyectil disparado desde una torre basica
 * Created by Sergio on 17/04/2016.
 */
public class BasicTowerProyectile extends Proyectile{
    ParticleEffect particleEffect;

    public void setParticleEffect(ParticleEffect particleEffect) {
        this.particleEffect = particleEffect;
        particleEffect.start();
        particleEffect.getEmitters().first().setContinuous(true);
    }

    @Override
    public void draw(Batch batch, float delta){
        particleEffect.setPosition(posicion.x-textura.getRegionWidth()*0.5f, posicion.y-textura.getRegionHeight()*0.5f);
        particleEffect.draw(batch,delta);
    }
}
