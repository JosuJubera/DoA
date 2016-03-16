package com.aro.defenseofatroth;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sergio on 16/03/2016.
 */
public class Unidad {
    //almacena la textura
    public Sprite texture;

    Unidad(){
        texture=new Sprite();
    }
    public void setTexture(TextureRegion text){
        texture.setRegion(text);
    }
}
