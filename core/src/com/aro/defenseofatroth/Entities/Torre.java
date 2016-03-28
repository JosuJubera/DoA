package com.aro.defenseofatroth.Entities;

import com.aro.defenseofatroth.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.aro.defenseofatroth.Constants.PIXELS_IN_METER;

/**
 * Created by elementary on 28/03/16.
 */
public class Torre extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public Torre(World world, Texture texture, Vector2 position) {

        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);

        PolygonShape torreShape =  new PolygonShape();
        torreShape.setAsBox(1, 2);
        fixture = body.createFixture(torreShape, 3);
        fixture.setUserData("torre");
        torreShape.dispose();
        setSize(PIXELS_IN_METER * 4, PIXELS_IN_METER * 8);
        debug();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * PIXELS_IN_METER, body.getPosition().y * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {

    }
}
