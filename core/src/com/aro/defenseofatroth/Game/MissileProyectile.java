package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Por ahora no hay que tocar nada...
 * Created by Sergio on 24/05/2016.
 */
public class MissileProyectile extends  Proyectile {

    @Override
    public void draw(Batch batch, float delta){
        //batch.draw(textura, posicion.x-textura.getRegionWidth()*0.5f, posicion.y-textura.getRegionHeight()*0.5f);
        batch.draw(textura,posicion.x-textura.getRegionWidth()*0.5f, posicion.y-textura.getRegionHeight()*0.5f,textura.getRegionWidth()*0.5f,textura.getRegionHeight()*0.5f,textura.getRegionWidth(),textura.getRegionHeight(),1,1,(float) Math.toDegrees(angulo));
    }
}
