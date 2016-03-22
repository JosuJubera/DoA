package com.aro.defenseofatroth;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Esta clase representa a toda cosa que sea dibujable
 * Created by Sergio on 22/03/2016.
 */
public abstract class Entidad{

    public Sprite texture; //guarda la textura sin mas
    public Animation animacion; //guarda la animacion si tiene
    public Vector2 posicion; //posicion del objeto
    public Vector2 velocidad; //velocidad del objeto
    public Vector2 aceleracion; //aceleracion del objeto
    public

    Entidad(){
        texture=null;
        animacion=null;
    }

    public void setTexture(TextureRegion text){
        texture.setRegion(text);
    }

    /**
     *  Dibuja la entidad en el batch. Este se ha de llamar dentro de batch.begin()
     * @param batch donde dibujar
     * @param parentAlpha tiempo
     */
    public  abstract void draw(Batch batch,float parentAlpha);
}
