package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.Entidad;
import com.aro.defenseofatroth.Game.BasicTank;
import com.aro.defenseofatroth.Game.BasicTower;
import com.aro.defenseofatroth.Game.CollisionControl;
import com.aro.defenseofatroth.Game.EnemyFactory;
import com.aro.defenseofatroth.Game.ProyectileFactory;
import com.aro.defenseofatroth.Game.TextureLoader;
import com.aro.defenseofatroth.Game.TowerFactory;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Proyectil;
import com.aro.defenseofatroth.Screens.BaseScreen;
import com.aro.defenseofatroth.Tools.GestureHandlerPruebas;
import com.aro.defenseofatroth.Torre;
import com.aro.defenseofatroth.Unidad;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level1  extends BaseScreen {

	private OrthographicCamera camera; //camara principal.
	private SpriteBatch batch; //representa el MUNDO.
//	private TextureAtlas atlas; //imagen con las texturas Tambien se puede hacer mediante codigo Pag:166
//	private Sprite background; //se usa para manejar tamaño y posicion de texturas (Se puede cargar desde un Atlas)
	private FitViewport viewport; //representa la imagen en PANTALLA
	private GestureDetector gestureDetector;
//	atencion, setButtonUpdate gorda, cuidado!
	public static Entidad niapa;
	public static Array<Entidad> entidades; //aqui iran todas la sentidades dibujables
	public static Pool<Proyectil> proyectiles;
	//prueba
	TextureAtlas atextura;

	//Constantes de Camara
	private static final float CAMERA_SPEED = 2.0f;
	private static final float CAMERA_ZOOM_SPEED = 2.0f;
	private static final float CAMERA_ZOOM_MAX = 3.0f; // Lo lejos que esta el zoom
	private static final float CAMERA_ZOOM_MIN = 0.3f; // Lo creca que esta el zoom
	private static final float CAMERA_MOVE_EDGE = 0.2f;

	//Constantes de ViewPort.
	private static final float SCENE_WIDTH = 1280f; //ancho pantalla en PIXELES!!
	private static final float SCENE_HEIGHT = 720f; //alto del pantalla en PIXELES!!
	//Para diferentes dispositivos. Usar como en QT
	/*Tipos de viewPorts:
		Strech: Ocupa toda la pantalla sin proporcion, puede ser distorsionado
		Fit: Ajusta a la pantalla con proporcion. Rellena con bordes negros si se sale del rango
		Screen: Ajusta a la pantalla con proporcion. Si se sale, cortara la imagen.
		Extend: Se mantiene ajustada entre X valores. Si se sale del maximo rellena con bordes negros
	*/

	//gestion de entrada
	private static final int MESSAGE_MAX = 20;
	private static final float HALF_TAP_SQUARE_SIZE = 20.0f;
	private static final float TAP_COUNT_INTERVAL = 0.4f;
	private static final float LONG_PRESS_DURATION = 1.1f;
	private static final float MAX_FLING_DELAY = 0.15f;
	protected MainClass game;

	private Music music;


	//Variables del nivel
    private TextureLoader textureLoader;
    private Stage stage;
    private World world;
	private CollisionControl collisionControl;
    //Variables auxilires, estas se eliminaran
    EnemyFactory enemyFactory;
    ProyectileFactory proyectileFactory;
    TowerFactory towerFactory;
	Box2DDebugRenderer debug;




	public Level1(MainClass game, boolean musica) {
		super(game);
		this.game = game;
		create();
		render(Gdx.graphics.getDeltaTime());
	}


	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(); //camara orthografica, es en 2D!
		viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //al viewport se le pasa la camara ¡Si no no muestra nada!
		/*atlas = new TextureAtlas(Gdx.files.internal("prehistoric.atlas"));
		background = new Sprite(atlas.findRegion("background"));
		background.setPosition(-background.getWidth() * 0.5f, -background.getHeight() * 0.5f);
		background.scale(2f);*/
		gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE,
				TAP_COUNT_INTERVAL,
				LONG_PRESS_DURATION,
				MAX_FLING_DELAY,
				new GestureHandlerPruebas( camera));

		Gdx.input.setInputProcessor(gestureDetector);
		proyectiles=new Pool<Proyectil>() {
			@Override
			protected Proyectil newObject() {
				return new Proyectil();
			}
		};


		//Dbujamos a una unidad
		Unidad prueba = new Unidad();
		atextura = new TextureAtlas(Gdx.files.internal("caveman.atlas"));
		Array<TextureAtlas.AtlasRegion> anima = new Array<TextureAtlas.AtlasRegion>(atextura.getRegions());
		prueba.animacion = new Animation(0.05f, anima, Animation.PlayMode.LOOP);
		entidades = new Array<Entidad>();
		entidades.add(prueba);
		prueba.setVelocidad(100f);
		prueba.setDestino(new Vector2(10f,10f));
		niapa=prueba;
        //Dibujamos torre
        Torre myTorre=new Torre();
        myTorre.setPosicion(new Vector2(1f, 1f));
        entidades.add(myTorre);

		//Se crea el TextureLoader y el mundo, escenario
        textureLoader=new TextureLoader();
        world=new World(new Vector2(0,0),false);
        stage=new Stage(viewport);
        textureLoader.setMundo(world);
        textureLoader.setEscenario(stage);
        textureLoader.cargar();
		collisionControl=new CollisionControl();
        world.setContactListener(collisionControl);
		//Variables  auxilires, estas se borrarian
        enemyFactory=new EnemyFactory();
        proyectileFactory=new ProyectileFactory();
        towerFactory=new TowerFactory();
        enemyFactory.setTextureLoader(textureLoader);
        enemyFactory.crearPools();
        proyectileFactory.setTextureLoader(textureLoader);
        towerFactory.setTextureLoader(textureLoader);
        towerFactory.setProyectileFactory(proyectileFactory);
        textureLoader.niapadePrueba(anima.get(1),MainClass.getManager().get("barraRoja.png",Texture.class));
		proyectileFactory.crearPools();
        BasicTower pruebas=towerFactory.obtenerBasicTower(400, 400);
        Array<Vector2> utas=new Array<Vector2>();
        utas.add(new Vector2(0,0));
        utas.add(new Vector2(100,100));
        utas.add(new Vector2(-500,500));
		utas.add(new Vector2(100,-150));
		utas.add(new Vector2(50,600));
		utas.add(new Vector2(-250, -150));
        enemyFactory.setRuta(utas);
		debug=new Box2DDebugRenderer(true,true,true,true,true,true);
        BasicTank  tankPru=enemyFactory.obtenerTankeBasico();






	}

	public void render(float delta) {
		//Limpiamos el frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
		camera.update();
		stage.act(delta);
        world.step(delta, 6, 2);
		debug.render(world, camera.combined);
		stage.draw();
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		//Se libera memoria, necesario para evitar memory leaks
		//Liberar batch y atlas sobretodo!!!
		batch.dispose();
		//atlas.dispose();
		atextura.dispose();
        world.dispose();
        stage.dispose();
        textureLoader.dispose();
		//Camaras y sprites no se necesitan limpiar
	}

}