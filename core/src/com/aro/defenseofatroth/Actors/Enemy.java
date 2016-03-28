package com.aro.defenseofatroth.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Juber on 11/03/2016.
 */
public class Enemy extends Actor {

    private Texture enemy;

    private boolean alive;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Enemy(Texture enemy) {
        this.enemy = enemy;
        this.alive = true;
        setPosition(-100, -250);
        setSize(enemy.getWidth(), enemy.getHeight());
    }

    @Override
    public void act(float delta) {
        //rotateBy(getRotation() + 0.1f * delta);
        setX(getX() + 100 * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemy, getX(), getY());
    }


}
