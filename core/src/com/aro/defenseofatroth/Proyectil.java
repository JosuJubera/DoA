package com.aro.defenseofatroth;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Esta clase representa un proyectil e una torre
 * Created by Sergio on 09/04/2016.
 */
public class Proyectil extends Entidad {
    private Unidad objetivo;
    private int danio;


    @Override
    public void draw(Batch batch, float delta) {
        setDestino(objetivo.destino);//en cada frame hay que actualizar el destino, ya que se mueve!
        actuar(delta);

        batch.draw(texture,posicion.x,posicion.y);
    }

    @Override
    public void dispose() {

    }
    private void actuar(float delta){
        posicion.x+=velocidad.x*delta;
        posicion.y+=velocidad.y*delta;

        if ((posicion.x==objetivo.getPosicion().x) && (posicion.y==objetivo.getDestino().y)){
            objetivo.danar(danio);
        }
    }
}
