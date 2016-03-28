package com.aro.defenseofatroth.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Juber on 11/03/2016.
 */
public class Canon extends Actor {

    private Texture canon;

    public Canon(Texture canon) {
        this.canon = canon;
        setPosition(500, -250);
        setSize(canon.getWidth(), canon.getHeight());
    }

    @Override
    public void act(float delta) {
        //rotateBy(getRotation() + 0.1f * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(canon, getX(), getY());
    }


}
