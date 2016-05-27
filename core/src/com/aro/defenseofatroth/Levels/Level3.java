package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.ActionResolver;
import com.aro.defenseofatroth.Game.CollisionControl;
import com.aro.defenseofatroth.Game.CustomDragAndDrop;
import com.aro.defenseofatroth.Game.EnemyFactory;
import com.aro.defenseofatroth.Game.Generator;
import com.aro.defenseofatroth.Game.Message;
import com.aro.defenseofatroth.Game.ProyectileFactory;
import com.aro.defenseofatroth.Game.TextureLoader;
import com.aro.defenseofatroth.Game.TowerFactory;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.BaseScreen;
import com.aro.defenseofatroth.Screens.Hud;
import com.aro.defenseofatroth.Screens.MenuScreen;
import com.aro.defenseofatroth.Screens.Selector;
import com.aro.defenseofatroth.Tools.Constants;
import com.aro.defenseofatroth.Tools.GestureHandlerPruebas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;

/**
 * Created by elementary on 28/03/16.
 */
public class Level3 extends BaseScreen implements ActionResolver,Level{


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
    Generator generator;
    private boolean rondaActiva;

    //ELementos del HUD
    private Hud hud;
    private Selector selector;

    //Cargador de las texturas, necesarios para crear las factorias
    private TextureLoader textureLoader;
    //Factorias
    private CollisionControl collisionControl;
    private EnemyFactory enemyFactory;
    private ProyectileFactory proyectileFactory;
    private TowerFactory towerFactory;

    //Lineas de debug
    Box2DDebugRenderer debugRenderer;

    //Drag and drop
    CustomDragAndDrop customDragAndDrop;
    FPSLogger logger;


    public Level3(MainClass game) {
        super(game);
        create();
        render(Gdx.graphics.getDeltaTime());
    }

    private void create(){

        mundoBatch = new SpriteBatch();
        camera = new OrthographicCamera(); //camara orthografica, es en 2D!
        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
        GestureHandlerPruebas gestureHandler=new GestureHandlerPruebas( camera);
        gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE,
                TAP_COUNT_INTERVAL,
                LONG_PRESS_DURATION,
                MAX_FLING_DELAY,
                gestureHandler);
        world = new World(new Vector2(0f,0f),false);
        stage = new Stage(viewport);
        textureLoader=TextureLoader.getInstance();
        textureLoader.setMundo(world);
        textureLoader.setEscenario(stage);
        textureLoader.cargar();
        collisionControl = new CollisionControl(this);
        world.setContactListener(collisionControl);

        hud=new Hud(mundoBatch);
        selector=new Selector(mundoBatch);
        //Factorias
        enemyFactory=new EnemyFactory();
        proyectileFactory=new ProyectileFactory();
        towerFactory=new TowerFactory();
        enemyFactory.setTextureLoader(textureLoader);
        enemyFactory.crearPools();
        proyectileFactory.setTextureLoader(textureLoader);
        towerFactory.setTextureLoader(textureLoader);
        towerFactory.setProyectileFactory(proyectileFactory);
        proyectileFactory.crearPools();
        gestureHandler.setTowerFactory(towerFactory); // <-------Ñapa
        //Generador de enemigos
        generator=new Generator();
        generator.setEnemyFactory(enemyFactory);
        generator.setDefaultLevel2(); //Pa debugear
        //Se crea el dragAndDrop
        customDragAndDrop=new CustomDragAndDrop();
        Array<Vector2> posciones=new Array<Vector2>();
        posciones.add(new Vector2(500,-200));
        posciones.add(new Vector2(500, 500));
        posciones.add(new Vector2(0, 500));
        posciones.add(new Vector2(500,0));
        customDragAndDrop.setSources(selector.getImagenes());
        customDragAndDrop.setTowerFactory(towerFactory);
        customDragAndDrop.setStage(stage);
        customDragAndDrop.setHud(hud);
        customDragAndDrop.setPosiciones(posciones);
        customDragAndDrop.bind();
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage,selector.stage,gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
        debugRenderer=new Box2DDebugRenderer(true,true,true,true,true,true);
        towerFactory.obtenerBasicTower(500, -200);
        towerFactory.obtenerLaserTower(500,500);
        rondaActiva=false;
        //Gdx.input.setInputProcessor(gestureDetector);
        logger=new FPSLogger();
        Message.getInstance().setStage(hud.stage);
        //Ñapa para fin de partida
        BodyDef cuerpoDef=new BodyDef();
        cuerpoDef.type = BodyDef.BodyType.DynamicBody;
        cuerpoDef.bullet=true;
        Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
        cuerpo.setUserData("Zona"); //Añadimos un puntero al cuerpo con la informacion del tanke
        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape =  new CircleShape(); //El shape tambien puede ser un cuadrado, si eso se camia aki
        shape.setRadius(400f);
        fixtureDef.shape = shape;
        fixtureDef.isSensor=true;
        fixtureDef.filter.categoryBits = 0xFF; //su categoria
        fixtureDef.filter.maskBits = com.aro.defenseofatroth.Game.Enemy.ENEMY_BIT; //con quien choca
        cuerpo.createFixture(fixtureDef);
        shape.dispose();
        cuerpo.setTransform(3000, 0, 0); //zona donde acaba el juego
        hud.addGold(200); //Dinero inicial

    }

    @Override
    public void render(float delta) {
        logger.log();
        updateGame(delta);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        world.step(delta, 6, 2);
        mundoBatch.setProjectionMatrix(camera.combined);
        debugRenderer.render(world, camera.combined);
        stage.draw();
        hud.stage.act();
        hud.stage.draw();
        selector.stage.draw();
    }

    private void updateGame(float delta){
        generator.actualizar(delta);
        if ((enemyFactory.getEnemies().size==0) && ((generator.getCreados()-generator.getSize())==0)){ //No hay mas enemigos en pantalla ni se ban a generar
            //Se genera la siguiente oleada
            hud.addWave();
            generator.reset();
            //showChartBoostIntersititial(); //si jub3r kiere ser muchimillonario descomentar esto
        }
    }
    @Override
    public void dispose() {
        world.dispose();
        mundoBatch.dispose();
        stage.dispose();
        hud.dispose();
        selector.stage.dispose();
        textureLoader.dispose();
    }

    @Override
    public void showChartBoostIntersititial() {
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
        Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void fin() {
        Message.getInstance().finalSay("¡Has perdido!");
        //TODO subir puntuacion
        Timer.Task t = new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MenuScreen(game));
            }
        };
        Message.getInstance().say("Puntuacion: " + Hud.getScore());
        Timer.schedule(t, 4);
    }
}
