package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.Entities.Enemy;
import com.aro.defenseofatroth.Entities.Torre;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.BaseScreen;
import com.aro.defenseofatroth.Screens.Hud;
import com.aro.defenseofatroth.Screens.Selector;
import com.aro.defenseofatroth.Tools.GestureHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.GameDragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_HEIGHT;
import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_WIDTH;
import static com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;

/**
 * Created by elementary on 28/03/16.
 */
public class Level3 extends BaseScreen{

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private Stage stage;

    private World world;

    private Torre torre;
    private Array<Enemy> bots;

    //gestion de entrada
    private GestureDetector gestureDetector;
    private static final float HALF_TAP_SQUARE_SIZE = 20.0f;
    private static final float TAP_COUNT_INTERVAL = 0.4f;
    private static final float LONG_PRESS_DURATION = 1.1f;
    private static final float MAX_FLING_DELAY = 0.15f;

    private boolean daino;
    // Pa las colisiones tutorial mario, filtrar colisiones queda por hacer
    public static final short DEFAULT_BIT = 1;
    public static final short TORRE_BIT = 2;
    public static final short ENEMY_BIT = 4;
    public static final short DEAD_BIT = 8;

    Timer.Task t;          // Pal spwan crea una tarea
    private int contadorBotsCreados = 0;

    Box2DDebugRenderer renderer; // Pa debugear
    OrthographicCamera cam;      // camara pa renderer, debug

    private Hud hud;
    private Selector selector;
    private SpriteBatch batch;

    public Level3(final MainClass game) {

        super(game);
        camera = new OrthographicCamera(); //camara orthografica, es en 2D!
        camera.zoom = 0.5f;
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE,
                TAP_COUNT_INTERVAL,
                LONG_PRESS_DURATION,
                MAX_FLING_DELAY,
                new GestureHandler(camera));

        stage = new Stage(viewport);

        batch = new SpriteBatch();
        hud = new Hud(batch);
        selector = new Selector(batch);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(selector.stage, gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
        world = new World(new Vector2(0, 0), true);

renderer = new Box2DDebugRenderer(true,true,true,true,true,true);                                 // Renderer pa debug de bodies y cosas
        cam = new OrthographicCamera(72,72);                                                      // Camara pa renderer
cam.update();

        hud.addGold(200);

        // Cuando se haga bien cambiar por WorldContactListener
        world.setContactListener(new ContactListener() {                                           // Poner listener de choque al mundo coge todos los choques

            // Colision 2 fixtures, no 2 bodies
            // hay que poner colision filters pa no chocar enemy con enemy
            private boolean areCollided(Contact contact, Object userA, Object userB) {             // Mira quien choca
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

                if (areCollided(contact, "alcance", "enemy")) {                                 // Cuando hay contacto si son alcance y enemy

                    Fixture bodyA = contact.getFixtureA();                                      // Cogemos lo que choca es decir la fixture
                    Fixture bodyB = contact.getFixtureB();

                    if (bodyA.getUserData().equals("enemy")) {                                  // Vemos cual es el enemy A o B
                        Iterator<Enemy> iterator = bots.iterator();
                        while (iterator.hasNext()){
                            Enemy en = iterator.next();
                            if (en.getBody().equals(bodyA.getBody()) && en.isAlive()){          // Coger el que es y si esta vivo herir
                                en.setHerir(true);
                            }
                        }
                    }
                    if (bodyB.getUserData().equals("enemy")) {
                        Iterator<Enemy> iterator = bots.iterator();
                        while (iterator.hasNext()){
                            Enemy en = iterator.next();
                            if (en.getBody().equals(bodyB.getBody()) && en.isAlive()){
                                en.setHerir(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

                if (areCollided(contact, "alcance", "enemy")) {

                    Fixture bodyA = contact.getFixtureA();
                    Fixture bodyB = contact.getFixtureB();

                    if (bodyA.getUserData().equals("enemy")) {
                        Iterator<Enemy> iterator = bots.iterator();
                        while (iterator.hasNext()){
                            Enemy en = iterator.next();
                            if (en.getBody().equals(bodyA.getBody()) && en.isAlive()){
                                en.setHerir(false);
                            }
                        }
                    }
                    if (bodyB.getUserData().equals("enemy")) {
                        Iterator<Enemy> iterator = bots.iterator();
                        while (iterator.hasNext()){
                            Enemy en = iterator.next();
                            if (en.getBody().equals(bodyB.getBody()) && en.isAlive()){
                                en.setHerir(false);
                            }
                        }
                    }
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

        Timer.schedule(t, 0.5f, 1);                                                         // Fijar timer (cuando empezar desde aki, cada cuanto)


//        Texture torreTex = game.getManager().get("torre.png", Texture.class);
//        torre = new Torre(world, torreTex, new Vector2(1, 2));;
//        stage.addActor(torre);

        final Skin skin = new Skin();
        skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        skin.add("badlogic", new Texture("badlogic.jpg"));

        final Image validTargetImage = new Image(skin, "badlogic");
        validTargetImage.setBounds(0, 0, 100, 100);
        stage.addActor(validTargetImage);

        Image validTargetImage2 = new Image(skin, "badlogic");
        validTargetImage2.setBounds(2460, 0, 100, 100);
        stage.addActor(validTargetImage2);

        Image validTargetImage3 = new Image(skin, "badlogic");
        validTargetImage3.setBounds(0, 1340, 100, 100);
        stage.addActor(validTargetImage3);

        Image validTargetImage4 = new Image(skin, "badlogic");
        validTargetImage4.setBounds(1280, 720, 100, 100);
        stage.addActor(validTargetImage4);


        Image invalidTargetImage = new Image(skin, "badlogic");
        invalidTargetImage.setBounds(2460, 1340, 100, 100);
        stage.addActor(invalidTargetImage);


        final ArrayList<Image> validTargets = new ArrayList();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++){
                Image target = new Image(game.getManager().get("target.png", Texture.class));
                target.setBounds(650 * i, 380 * j, 100, 100);
                validTargets.add(target);
                stage.addActor(target);
            }
        }


        GameDragAndDrop dragAndDrop = new GameDragAndDrop();
        dragAndDrop.addSource(new Source(selector.stage.getActors().first()) {
            @Override
            public Payload dragStart (InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
                payload.setObject("Some payload!");

                payload.setDragActor(new Label("Some payload!", skin));

                Label validLabel = new Label("Some payload!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some payload!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {

                if (target != null && hud.getMoney() >= torre.getCoste()) {
                    hud.addGold(-torre.getCoste());
                    Texture torreTex = game.getManager().get("torre.png", Texture.class);
                    Torre torreNew = new Torre(world, torreTex, new Vector2(target.getActor().getX(), target.getActor().getY()));
                    stage.addActor(torreNew);
                    for (int i = 0; i < validTargets.size(); i++) {
                        if (validTargets.get(i).equals(target.getActor())) {
                            validTargets.get(i).remove();
                        }
                    }
//                    torre.remove();
//                    world.destroyBody(torre.getBody());
                }
            }
        });

        for (int i = 0; i < validTargets.size(); i++) {
            dragAndDrop.addTarget(new Target(validTargets.get(i)) {
                public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                    getActor().setColor(Color.GREEN);
                    return true;
                }

                public void reset(Source source, Payload payload) {
                    getActor().setColor(Color.WHITE);
                }

                public void drop(Source source, Payload payload, float x, float y, int pointer) {
                }
            });
        }

        dragAndDrop.addTarget(new Target(invalidTargetImage) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.RED);
                return false;
            }

            public void reset (Source source, Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
            }
        });

    }

    @Override
    public void show() {
//        Texture torreTex = game.getManager().get("torre.png", Texture.class);
//        torre = new Torre(world, torreTex, new Vector2(1, 2));;
//        stage.addActor(torre);
    }

    @Override
    public void hide() {
//        torre.remove();
    }

    @Override
    public void dispose() {

        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {                                                    // Renderiza t odo 30 - 60 vecer por minuto por eso usar *delta

        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);                                        // Color de fondo y transparencia
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                                        // Limpiar el buffer de la pantalla
cam.update();                                                                            // actualizar camara de renderer
        renderer.render(world,cam.combined);                                             // mostrar renderer para ver bodies y cosas
        stage.act();                                                                     // Actualizar stage (movimientos y esas cosas antes de dibujar)
        world.step(delta, 6, 2);                                                         // Actualiza el mundo los parametros no cambiar


        for (int i = 0; i < bots.size; i++) {                                            // Coge los bots
            Enemy e = bots.get(i);
            if (e.getHerir()) {
                e.dainar(24 * delta);                                                    // Daña lo que seria 10 por segundo
            }
            if (!e.isAlive()){
                bots.removeIndex(i);                                                     // Quitar del array
                e.remove();                                                              // Elimina nose que pero hay que usar
                world.destroyBody(e.getBody());                                          // Eliminar actor del mundo
                hud.addScore(e.getScore());
                hud.addGold(e.getGold());
            }
            if (bots.size == 0) {
                hud.addWave();
            }
        }

        if (contadorBotsCreados >= 10){
            t.cancel();                                                                  // Terminar el timer, terminar el spawn
        }

        Iterator<Enemy> iterator = bots.iterator();                                      // Iterar array
        while (iterator.hasNext()){
            Enemy en = iterator.next();
            stage.addActor(en);                                                          // Añadir actor al stage
        }

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        selector.stage.draw();



        stage.draw();                                                                    // Dibujar el stage
    }

    public void spawnBots() {

        Texture enemyTex2 = game.getManager().get("barraRoja.png", Texture.class);       // Cargar textura del assetManager
        Enemy enemy = new Enemy(world, enemyTex2, new Vector2(40, (new Random().nextFloat() * 10)), 100); // Crear un enemigo mundo por ahora no se usa
        bots.add(enemy);                                                                 // Añadir al array de enemigos
        contadorBotsCreados++;                                                           // Contador de enemigos
    }
}
