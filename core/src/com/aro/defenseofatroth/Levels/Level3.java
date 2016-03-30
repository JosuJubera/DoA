package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.Entities.Enemy;
import com.aro.defenseofatroth.Entities.Torre;
import com.aro.defenseofatroth.Tools.GestureHandler;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.BaseScreen;
import com.aro.defenseofatroth.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Iterator;
import java.util.Random;

import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_HEIGHT;
import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_WIDTH;

/**
 * Created by elementary on 28/03/16.
 */
public class Level3 extends BaseScreen{

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private Stage stage;

    private World world;

    private Torre torre;
    private Enemy enemy;
    private Array<Enemy> bots;
    private GestureHandler gestureHandler;

    //gestion de entrada
    private GestureDetector gestureDetector;
    private static final float HALF_TAP_SQUARE_SIZE = 20.0f;
    private static final float TAP_COUNT_INTERVAL = 0.4f;
    private static final float LONG_PRESS_DURATION = 1.1f;
    private static final float MAX_FLING_DELAY = 0.15f;

    private boolean daino;
    // Pa las colisiones tutorial mario
    public static final short DEFAULT_BIT = 1;
    public static final short TORRE_BIT = 2;
    public static final short ENEMY_BIT = 4;
    public static final short DEAD_BIT = 8;

    Timer.Task t;
    private Array<Contact> colided;

    Box2DDebugRenderer renderer;
    OrthographicCamera cam;

    public Level3(MainClass game) {

        super(game);
        camera = new OrthographicCamera(); //camara orthografica, es en 2D!
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE,
                TAP_COUNT_INTERVAL,
                LONG_PRESS_DURATION,
                MAX_FLING_DELAY,
                new GestureHandler(camera));

        Gdx.input.setInputProcessor(gestureDetector);
        stage = new Stage(viewport);
        world = new World(new Vector2(0, 0), true);
        daino = false;
renderer = new Box2DDebugRenderer(true,true,true,true,true,true);
        cam = new OrthographicCamera(72,72);
cam.update();

        // Cuando se haga bien cambiar por WorldContactListener
        world.setContactListener(new ContactListener() {

            // Colision 2 fixtures, no 2 bodies
            private boolean areCollided(Contact contact, Object userA, Object userB) {
                Object userDataA = contact.getFixtureA().getUserData();
                Object userDataB = contact.getFixtureB().getUserData();

                if (userDataA == null || userDataB == null) {
                    return false;
                }

                return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                        (userDataA.equals(userB) && userDataB.equals(userA));
            }

        @Override
        public void beginContact(Contact contact) {

            if (areCollided(contact, "torre", "enemy")) {
                if (enemy.isAlive()) {
                    daino = true;
                    System.out.println("*************");
                }
            }
            if (areCollided(contact, "alcance", "enemy")) {
                if (enemy.isAlive()) {
                    daino = true;
                    System.out.println("/////////////");
                }
            }
        }

        @Override
        public void endContact(Contact contact) {
            if (enemy.isAlive()) {
                daino = false;
            }
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {}

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {}
        });


        bots = new Array<Enemy>();

        t = new Timer.Task() {
            @Override
            public void run() {
                spawnBots();
            }
        };

        Timer.schedule(t, 2, 5);
    }

    @Override
    public void show() {
        Texture torreTex = game.getManager().get("torre.png", Texture.class);
        Texture enemyTex = game.getManager().get("barraRoja.png", Texture.class);
        torre = new Torre(world, torreTex, new Vector2(1, 2));
        enemy = new Enemy(world, enemyTex, new Vector2(10, 2));
        enemy.setAlive(true);
        stage.addActor(torre);
        stage.addActor(enemy);
    }

    @Override
    public void hide() {
        torre.remove();
        enemy.remove();
    }

    @Override
    public void dispose() {

        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
cam.update();
        renderer.render(world,cam.combined);
        stage.act();
        world.step(delta, 6, 2);


        if (daino){
            enemy.dainar(10 * delta);
            if (enemy.getVidaActual() <= 0) {
                enemy.setAlive(false);
            }
            if (enemy.getVidaActual() < 50) {
                enemy.jump = true;
            }
        }
        if (!enemy.isAlive()) {
            enemy.remove();
        }


        if (bots.size >= 10){
            t.cancel();
        }


        Iterator<Enemy> iterator = bots.iterator();
        while (iterator.hasNext()){
            Enemy en = iterator.next();
            stage.addActor(en);
        }
        stage.draw();
    }

    public void spawnBots() {

        Texture enemyTex2 = game.getManager().get("barraRoja.png", Texture.class);
        enemy = new Enemy(world, enemyTex2, new Vector2(10, (new Random().nextInt(6))));
        bots.add(enemy);
    }
}
