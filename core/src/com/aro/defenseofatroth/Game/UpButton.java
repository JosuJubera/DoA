package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Esta clase tiene el icono para actualizar una torre
 * Created by Sergio on 25/05/2016.
 */
public class UpButton extends Actor{
    protected int coste;
    protected TextureRegion imagen;
    private BitmapFont font;
    protected String texto;


    public UpButton(final Tower tower,int coste,float x,float y){
        imagen=TextureLoader.getInstance().obtenerMejorar();
        font=TextureLoader.getInstance().obtenerFont();
        font.setColor(Color.BLUE);
        font.getData().setScale(4f);
        this.coste=coste;
        texto=String.valueOf(coste);
        this.setWidth(imagen.getRegionWidth());
        this.setHeight(imagen.getRegionHeight());
        this.setPosition(x, y);// + imagen.getHeight() * 0.5f
        final UpButton ñapa=this;
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tower.mejorar();
                ñapa.remove();
            }
        });
    }
    @Override
    public void draw(Batch batch,float delta){
        batch.draw(imagen, getX(), getY());
        font.draw(batch, texto, getX()+70, getY()+50);
    }
    public void dispose() {
        font.dispose();
    }
}
