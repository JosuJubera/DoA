package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.ActionResolver;
import com.aro.defenseofatroth.Entities.Torre;
import com.aro.defenseofatroth.Game.BasicTower;
import com.aro.defenseofatroth.Game.CollisionControl;
import com.aro.defenseofatroth.Game.CustomDragAndDrop;
import com.aro.defenseofatroth.Game.EnemyFactory;
import com.aro.defenseofatroth.Game.Generator;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;



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
        world = new World(new Vector2(0f,0f),true);
        textureLoader = new TextureLoader();
        stage = new Stage(viewport);
        textureLoader.setMundo(world);
        textureLoader.setEscenario(stage);
        textureLoader.cargar();
        collisionControl = new CollisionControl();
        world.setContactListener(collisionControl);

        hud=new Hud(mundoBatch);
        selector=new Selector(mundoBatch);
        //Factorias
        enemyFactory=new EnemyFactory();
        proyectileFactory=new ProyectileFactory();
        towerFactory=new TowerFactory();
        enemyFactory.setTextureLoader(textureLoader);
        enemyFactory.crearPools();
        enemyFactory.addÑapa(this); //Es una ñapa muy gorda, pero es necesaria. God Save the Ñapas!
        proyectileFactory.setTextureLoader(textureLoader);
        towerFactory.setTextureLoader(textureLoader);
        towerFactory.setProyectileFactory(proyectileFactory);
        proyectileFactory.crearPools();
       //Generador de enemigos
        generator=new Generator();
        generator.setEnemyFactory(enemyFactory);
        generator.setDefault(); //Pa debugear
        //Se crea el dragAndDrop
        customDragAndDrop=new CustomDragAndDrop();
        Array<Vector2> posciones=new Array<Vector2>();
        posciones.add(new Vector2(500,-200));
        posciones.add(new Vector2(500,500));
       /* customDragAndDrop.setSources(selector.getImagenes());
        customDragAndDrop.setTowerFactory(towerFactory);
        customDragAndDrop.setStage(stage);
        customDragAndDrop.setHud(hud);
        customDragAndDrop.setPosiciones(posciones);
        customDragAndDrop.bind();*/
        InputMultiplexer inputMultiplexer = new InputMultiplexer(selector.stage,gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
        debugRenderer=new Box2DDebugRenderer(true,true,true,true,true,true);
        towerFactory.obtenerBasicTower(500,-200);
        towerFactory.obtenerBasicTower(500,500);
        rondaActiva=false;
        //Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    public void render(float delta) {
        updateGame(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        world.step(delta, 6, 2);
        mundoBatch.setProjectionMatrix(camera.combined);
        stage.draw();
        debugRenderer.render(world, camera.combined);
        hud.stage.draw();
        selector.stage.draw();
    }

    private void updateGame(float delta){
        generator.actualizar(delta);
        if (enemyFactory.getEnemies().size==0){ //No hay mas enemigos en pantalla
            //Se genera la siguiente oleada
            hud.addWave();
            //showChartBoostIntersititial(); //si jub3r kiere ser muchimillonario descomentar esto
        }
    }
    @Override
    public void dispose() {
        world.dispose();
        mundoBatch.dispose();
        stage.dispose();
        textureLoader.dispose();
    }

    @Override
    public void showChartBoostIntersititial() {
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
        Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
    }

    public void addMoney(int modey) {
        hud.addGold(modey);
    }
}
