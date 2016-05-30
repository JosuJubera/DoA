package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.ActionResolver;
import com.aro.defenseofatroth.Game.CollisionControl;
import com.aro.defenseofatroth.Game.CustomDragAndDrop;
import com.aro.defenseofatroth.Game.Enemy;
import com.aro.defenseofatroth.Game.EnemyFactory;
import com.aro.defenseofatroth.Game.Generator;
import com.aro.defenseofatroth.Game.Message;
import com.aro.defenseofatroth.Game.ProyectileFactory;
import com.aro.defenseofatroth.Game.TextureLoader;
import com.aro.defenseofatroth.Game.TowerFactory;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.BaseScreen;
import com.aro.defenseofatroth.Screens.Hud;
import com.aro.defenseofatroth.Screens.LoginScreen;
import com.aro.defenseofatroth.Screens.MenuScreen;
import com.aro.defenseofatroth.Screens.Selector;
import com.aro.defenseofatroth.Tools.Constants;
import com.aro.defenseofatroth.Tools.GestureHandlerPruebas;
import com.aro.defenseofatroth.WS.ResponseWS;
import com.aro.defenseofatroth.WS.WebServices;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
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



/** TODO completar. El drag and drop debe de ir en otra clase, junto con el generador
 * Created by Sergio on 18/05/2016.
 */
public class Level2 extends BaseScreen implements ActionResolver,Level{
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
    private Sprite fondo;

    //Lineas de debug
    Box2DDebugRenderer debugRenderer;
    ParticleEffect particula;

    //Drag and drop
    CustomDragAndDrop customDragAndDrop;
    FPSLogger logger;


    public Level2(MainClass game) {
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
        proyectileFactory.setTextureLoader(textureLoader);
        towerFactory.setTextureLoader(textureLoader);
        towerFactory.setProyectileFactory(proyectileFactory);
        proyectileFactory.crearPools();
        gestureHandler.setTowerFactory(towerFactory); // <-------Ñapa
       //Generador de enemigos
        generator=new Generator();
        generator.setEnemyFactory(enemyFactory);
        generator.setDefaultLevel2(); //Pa debugear
        //Generacion del fondo
        float scaleX=3.5f,scaleY=3.5f; //Escala del mapa
        fondo=new Sprite(MainClass.getManager().get("mapaFinal.png",Texture.class));
        fondo.setScale(scaleX, scaleY);
        fondo.setOrigin(0, 0);
        fondo.setPosition(0, 0);
        Array<Vector2> posiciones=new Array<Vector2>();
        posiciones.add(new Vector2(proyecion(240, 123)));
        posiciones.add(new Vector2(proyecion(212, 380)));
        posiciones.add(new Vector2(proyecion(420,200)));
        posiciones.add(new Vector2(proyecion(60, 410)));
        posiciones.add(new Vector2(proyecion(375, 430)));

        //Ruta
        Array<Vector2> ruta=new Array<Vector2>();
        ruta.add(new Vector2(proyecion(200, 74)));
        ruta.add(new Vector2(proyecion(165, 215)));
        ruta.add(new Vector2(proyecion(138, 400)));
        ruta.add(new Vector2(proyecion(232, 420)));
        ruta.add(new Vector2(proyecion(350, 375)));
        ruta.add(new Vector2(proyecion(360, 260)));
        ruta.add(new Vector2(proyecion(400, 140)));
        ruta.add(new Vector2(proyecion(500, 95)));
        enemyFactory.setRuta(ruta);
        enemyFactory.crearPools();
        //Se crea el dragAndDrop
        customDragAndDrop=new CustomDragAndDrop();
        customDragAndDrop.setSources(selector.getImagenes());
        customDragAndDrop.setTowerFactory(towerFactory);
        customDragAndDrop.setStage(stage);
        customDragAndDrop.setHud(hud);
        customDragAndDrop.setPosiciones(posiciones);
        customDragAndDrop.bind();
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage,selector.stage,gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
        debugRenderer=new Box2DDebugRenderer(true,true,true,true,true,true);

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
        shape.setRadius(200f);
        fixtureDef.shape = shape;
        fixtureDef.isSensor=true;
        fixtureDef.filter.categoryBits = 0xFF; //su categoria
        fixtureDef.filter.maskBits = Enemy.ENEMY_BIT; //con quien choca
        cuerpo.createFixture(fixtureDef);
        shape.dispose();
        Vector2 pry=new Vector2(proyecion(510,90));
        cuerpo.setTransform(pry.x, pry.y, 0); //zona donde acaba el juego

        Hud.addGold(500); //Oro inicial

        particula=new ParticleEffect();
        particula.load(Gdx.files.internal("data/explosion.particle"), Gdx.files.internal("data"));
        particula.setPosition(0, 0);
        particula.getEmitters().first().setContinuous(true);
        particula.start();
        particula.scaleEffect(10f);

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
        mundoBatch.begin();
        fondo.draw(mundoBatch);
        particula.draw(mundoBatch,delta);
        mundoBatch.end();
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
        if (!LoginScreen.email.equals("Invitado")){
            ResponseWS rws1 = new WebServices().updateScore(LoginScreen.email,Hud.getScore());
        }
        Timer.Task t = new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MenuScreen(game));
            }
        };
        Message.getInstance().say("Puntuacion: " + Hud.getScore());
        Timer.schedule(t, 4);
    }

    /**
     * Pryecta las coordenadas en la imagen por las del mundo. Cabiar la funcion segun la imagen.
     * @param x
     * @param y
     * @return
     */
    private Vector2 proyecion(float x,float y){
        float newX,newY;
        newX=x*fondo.getScaleX();
        newY=(fondo.getHeight()-y)*fondo.getScaleY();
        return new Vector2(newX,newY);
    }
}
