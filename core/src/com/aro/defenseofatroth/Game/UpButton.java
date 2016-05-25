package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Esta clase tiene el icono para actualizar una torre
 * Created by Sergio on 25/05/2016.
 */
public class UpButton extends Actor{
    protected int coste;
    protected Texture imagen;
    private BitmapFont font;
    protected String texto;


    public UpButton(final Tower tower,int coste,float x,float y){
        imagen= MainClass.getManager().get("mejorar.png", Texture.class);
        font=new BitmapFont(Gdx.files.internal("data/default.fnt"),Gdx.files.internal("data/default.png"), false);
        font.setColor(Color.BLUE);
        font.getData().setScale(4f);
        this.coste=coste;
        texto=String.valueOf(coste);
        this.setWidth(imagen.getWidth());
        this.setHeight(imagen.getHeight());
        this.setPosition(x, y);// + imagen.getHeight() * 0.5f
        this.setDebug(true);
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
