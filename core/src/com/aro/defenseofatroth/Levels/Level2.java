package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.ActionResolver;
import com.aro.defenseofatroth.Entities.Torre;
import com.aro.defenseofatroth.Game.CollisionControl;
import com.aro.defenseofatroth.Game.EnemyFactory;
import com.aro.defenseofatroth.Game.ProyectileFactory;
import com.aro.defenseofatroth.Game.TextureLoader;
import com.aro.defenseofatroth.Game.TowerFactory;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.BaseScreen;
import com.aro.defenseofatroth.Screens.Hud;
import com.aro.defenseofatroth.Screens.Selector;
import com.aro.defenseofatroth.Tools.Constants;
import com.aro.defenseofatroth.Tools.GestureHandlerPruebas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.GameDragAndDrop;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.sql.Time;
import java.util.ArrayList;

/** TODO completar. El drag and drop debe de ir en otra clase, junto con el generador
 * Created by Sergio on 18/05/2016.
 */
public class Level2 extends BaseScreen implements ActionResolver{
    //Variables del MUNDO
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch mundoBatch;
    private World world;
    private GestureDetector gestureDetector;
    private FitViewport viewport;
    // Es para el zoom y control de input del usuario. Kitar
    private static final float HALF_TAP_SQUARE_SIZE = 20.0f;
    private static final float TAP_COUNT_INTERVAL = 0.4f;
    private static final float LONG_PRESS_DURATION = 1.1f;
    private static final float MAX_FLING_DELAY = 0.15f;

    //Generador de bots, al final ira en otra clase
    Timer.Task t;

    //ELementos del HUD
    private Hud hud;
    private Selector selector;
    private SpriteBatch hudBatch;

    //Cargador de las texturas, necesarios para crear las factorias
    private TextureLoader textureLoader;
    //Factorias
    private CollisionControl collisionControl;
    private EnemyFactory enemyFactory;
    private ProyectileFactory proyectileFactory;
    private TowerFactory towerFactory;

    //Lineas de debug
    Box2DDebugRenderer debugRenderer;



    public Level2(MainClass game) {
        super(game);
        create();
        render(Gdx.graphics.getDeltaTime());
    }

    private void create(){

        mundoBatch = new SpriteBatch();
        camera = new OrthographicCamera(); //camara orthografica, es en 2D!
        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
        gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE,
                TAP_COUNT_INTERVAL,
                LONG_PRESS_DURATION,
                MAX_FLING_DELAY,
                new GestureHandlerPruebas( camera));
        InputMultiplexer inputMultiplexer = new InputMultiplexer(selector.stage,gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
        world = new World(new Vector2(0f,0f),true);
        textureLoader = new TextureLoader();
        stage = new Stage(viewport);
        textureLoader.setMundo(world);
        textureLoader.setEscenario(stage);
        textureLoader.cargar();
        collisionControl = new CollisionControl();
        world.setContactListener(collisionControl);
        t= new Timer.Task() {
            @Override
            public void run() {
                spawnBots();
            }
        };
        //Generador de enemigos (falta lo mio)
        Timer.schedule(t,0.5f,1);
        //Imagenes diciendo donde se pueden poner las  torres
        final ArrayList<Image> validTargets = new ArrayList();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++){
                Image target = new Image(game.getManager().get("target.png", Texture.class));
                target.setBounds(650 * i, 380 * j, 100, 100);
                validTargets.add(target);
                stage.addActor(target);
            }
        }
        //Drag and drop. Sacar en otra clase. Ver documentacion y que sea por cada torre correspondiente
        GameDragAndDrop dragAndDrop=new GameDragAndDrop();
        dragAndDrop.addSource(new DragAndDrop.Source(selector.stage.getActors().first()) {
            @Override
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();

                payload.setDragActor(new Label("Some payload!", skin)); //Aqui iria la imagen de la torre

                Label validLabel = new Label("Some payload!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel); //Imagen que aparece si es valido (tintar de verde)

                Label invalidLabel = new Label("Some payload!", skin); //Idem que antes
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }

            @Override //Se llama cuando dejas de mover el objeto. En target va el actor sobre el que estas. Cambiar TORRE por la correspondiendte
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {

                if (target != null && hud.getMoney() >= Torre.getCoste()) {
                    hud.addGold(-Torre.getCoste());
                    Texture torreTex = game.getManager().get("torre.png", Texture.class);
                    Torre torreNew = new Torre(world, torreTex, new Vector2(target.getActor().getX(), target.getActor().getY())); //Se añade en la posicion de la imagen que genera
                    stage.addActor(torreNew);
                    for (int i = 0; i < validTargets.size(); i++) {
                        if (validTargets.get(i).equals(target.getActor())) {
                            validTargets.get(i).remove();
                        }
                    }
                }
            }
        });

        //Añade los actores que señalan las posiciones que señalan las zonas validas
        for (int i = 0; i < validTargets.size(); i++) {
            dragAndDrop.addTarget(new DragAndDrop.Target(validTargets.get(i)) {
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    getActor().setColor(Color.GREEN);
                    return true;
                }

                public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                    getActor().setColor(Color.WHITE);
                }

                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                }
            });
        }

        //Factorias. Deberan estar en la clase generador
        //Variables  auxilires, estas se borrarian
        enemyFactory=new EnemyFactory();
        proyectileFactory=new ProyectileFactory();
        towerFactory=new TowerFactory();
        enemyFactory.setTextureLoader(textureLoader);
        enemyFactory.crearPools();
        proyectileFactory.setTextureLoader(textureLoader);
        towerFactory.setTextureLoader(textureLoader);
        towerFactory.setProyectileFactory(proyectileFactory);
        //textureLoader.niapadePrueba(anima.get(1),new Texture(Gdx.files.internal("barraRoja.png")));
        proyectileFactory.crearPools();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        world.step(delta, 6, 2);
        mundoBatch.setProjectionMatrix(camera.combined);
        hud.stage.draw();
        selector.stage.draw();


    }

    @Override
    public void dispose() {
        world.dispose();
        hudBatch.dispose();
        mundoBatch.dispose();
        stage.dispose();
        textureLoader.dispose();
    }

    @Override
    public void showChartBoostIntersititial() {
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
        Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
    }
}
