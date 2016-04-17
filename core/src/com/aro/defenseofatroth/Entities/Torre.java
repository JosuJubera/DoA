package com.aro.defenseofatroth.Entities;

import com.aro.defenseofatroth.Levels.Level3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.aro.defenseofatroth.Tools.Constants.PIXELS_IN_METER;

/**
 * Created by elementary on 28/03/16.
 */
public class Torre extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private static int coste = 100;

    public Torre(World world, Texture texture, Vector2 position) {

        this.world = world;
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x / PIXELS_IN_METER, position.y / PIXELS_IN_METER);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();

        // Quitau choque a torre, los enemies ya no chocan contra la torre
//        PolygonShape shape =  new PolygonShape();
//        shape.setAsBox(2, 4);

//        fixtureDef.shape = shape;
//        fixtureDef.filter.categoryBits=Level3.TORRE_BIT;
//        fixtureDef.filter.maskBits=Level3.ENEMY_BIT; //Si no se quiere que se choque contra la torre comentar esto
//        fixtureDef.density=0f;
//        fixture = body.createFixture(fixtureDef);
//        fixture.setUserData("torre");
//        shape.dispose();

        setSize(PIXELS_IN_METER * 1.3f, PIXELS_IN_METER * 2.6f);
        debug();

        CircleShape alcance = new CircleShape();
        alcance.setRadius(5);
        fixtureDef.shape = alcance;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Level3.TORRE_BIT; // SU tipo
        fixtureDef.filter.maskBits = Level3.DEFAULT_BIT | Level3.ENEMY_BIT; // Con los que puede colisionar

        body.createFixture(fixtureDef).setUserData("alcance");
        alcance.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * PIXELS_IN_METER, body.getPosition().y * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {

    }

    public Body getBody() {
        return body;
    }

    public static int getCoste() {
        return coste;
    }
}
