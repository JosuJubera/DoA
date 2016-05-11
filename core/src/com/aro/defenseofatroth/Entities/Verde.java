package com.aro.defenseofatroth.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by elementary on 20/04/16.
 */
public class Verde extends Enemy {

    public Verde(World world, Texture texture, Vector2 position, float vidaMaxima) {
        super(world, texture, position, vidaMaxima);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
