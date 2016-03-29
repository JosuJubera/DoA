package com.aro.defenseofatroth.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.aro.defenseofatroth.Constants.PIXELS_IN_METER;

/**
 * Created by elementary on 28/03/16.
 */
public class Enemy extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive;
    public boolean jump;

    public Enemy(World world, Texture texture, Vector2 position) {

        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        CircleShape enemyShape =  new CircleShape();
        enemyShape.setRadius(2);
        fixture = body.createFixture(enemyShape, 3);
        fixture.setUserData("enemy");
        enemyShape.dispose();

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);

jump = false;


        broja = new TextureRegion(new Texture(Gdx.files.internal("barraRoja.png")));
        bverde = new TextureRegion(new Texture(Gdx.files.internal("barraVerde.png")));
        barraVidaFondo = new NinePatch(broja, 0, 0, 0, 0); //es una prueba
        barraVidaDelante = new NinePatch(bverde,0,0,0,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * PIXELS_IN_METER, body.getPosition().y * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());



        barraVidaFondo.draw(batch, body.getPosition().x * PIXELS_IN_METER,
                body.getPosition().y * PIXELS_IN_METER + getHeight() + 0.1f * PIXELS_IN_METER,
                vidaMaxima * getWidth() / PIXELS_IN_METER / 1.5f, 1 * PIXELS_IN_METER / 7.2f);
        barraVidaDelante.draw(batch, body.getPosition().x * PIXELS_IN_METER,
                body.getPosition().y * PIXELS_IN_METER + getHeight() + 0.1f * PIXELS_IN_METER,
                vidaActual * getWidth() / PIXELS_IN_METER / 1.5f, 1 * PIXELS_IN_METER / 7.2f);

    }

    @Override
    public void act(float delta) {
        body.setLinearVelocity(-1, 0);
        if (jump) {
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(200, 20, position.x, position.y, true);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }





    private int vidaMaxima=100;

    public int getVidaActual() {
        return vidaActual;
    }

    private int vidaActual=100;
    private NinePatch barraVidaFondo;
    private NinePatch barraVidaDelante;

    TextureRegion broja;
    TextureRegion bverde;
    private float animationTime=0;
    public Animation animacion;
    protected Vector2 posicion;

    public void dainar(float daino) {

        this.vidaActual -= daino;
        if (vidaActual < 0) {
            vidaActual = vidaMaxima;
        }
    }

}