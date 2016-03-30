package com.aro.defenseofatroth.Levels;

import com.aro.defenseofatroth.Entidad;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.BaseScreen;
import com.aro.defenseofatroth.Unidad;
import com.aro.defenseofatroth.Tools.GestureHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level1  extends BaseScreen {

	private OrthographicCamera camera; //camara principal.
	private SpriteBatch batch; //representa el MUNDO.
	private TextureAtlas atlas; //imagen con las texturas Tambien se puede hacer mediante codigo Pag:166
	private Sprite background; //se usa para manejar tamaño y posicion de texturas (Se puede cargar desde un Atlas)
	private FitViewport viewport; //representa la imagen en PANTALLA
	private Texture levelTexture;
	private Sprite caverman;
	private GestureDetector gestureDetector;
//	atencion, ñapa gorda, cuidado!
	public static Entidad niapa;
	private Array<Entidad> entidades; //aqui iran todas la sentidades dibujables

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
	//En pixeles! Para mantener la proporcion
	private static final float MIN_SCENE_WIDTH = 800.0f;
	private static final float MIN_SCENE_HEIGHT = 600.0f;
	private static final float MAX_SCENE_WIDTH = 1280.0f;
	private static final float MAX_SCENE_HEIGHT = 720.0f;

	//gestion de entrada
	private static final int MESSAGE_MAX = 20;
	private static final float HALF_TAP_SQUARE_SIZE = 20.0f;
	private static final float TAP_COUNT_INTERVAL = 0.4f;
	private static final float LONG_PRESS_DURATION = 1.1f;
	private static final float MAX_FLING_DELAY = 0.15f;
	protected MainClass game;


	private Music music;
	//private Media


	public Level1(MainClass game, boolean musica) {
		super(game);
		this.game = game;
		create();
		render(Gdx.graphics.getDeltaTime());
		try {
			music = MainClass.getManager().get("music.ogg", Music.class);
		}catch (Exception e){
			Gdx.app.error("FILE NOT FOUND", "No se ha encontrado",e);
		}
		if (musica == true){
			music.setLooping(true);
			music.play();
		}
	}


	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(); //camara orthografica, es en 2D!
		viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //al viewport se le pasa la camara ¡Si no no muestra nada!
		atlas = new TextureAtlas(Gdx.files.internal("prehistoric.atlas"));
		background = new Sprite(atlas.findRegion("background"));
		background.setPosition(-background.getWidth() * 0.5f, -background.getHeight() * 0.5f);
		background.scale(2f);
//		camposicion = new Vector2(0f, 0f);
		gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE,
				TAP_COUNT_INTERVAL,
				LONG_PRESS_DURATION,
				MAX_FLING_DELAY,
				new GestureHandler(/*camposicion,*/ camera));

		Gdx.input.setInputProcessor(gestureDetector);

		//Dbujamos a una unidad
		Unidad prueba = new Unidad();
		atextura = new TextureAtlas(Gdx.files.internal("caveman.atlas"));
		Array<TextureAtlas.AtlasRegion> anima = new Array<TextureAtlas.AtlasRegion>(atextura.getRegions());
		prueba.animacion = new Animation(0.05f, anima, Animation.PlayMode.LOOP);
		entidades = new Array();
		entidades.add(prueba);
		prueba.setVelocidad(100f);
		prueba.setDestino(new Vector2(10f,10f));
		niapa=prueba;

	}

	public void render(float delta) {
		//Limpiamos el frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//		camera.translate(camposicion);
		camera.update();
//		camposicion.scl(0.95f);
		batch.setProjectionMatrix(camera.combined);
		//Preparamos para dibujar
		batch.begin();
		//Dibujamos, to.do lo que haya que renderizar ira aqui
		background.draw(batch);
		//Dibujar toda la escena
		for (int i = 0; i < entidades.size; i++) {
			entidades.get(i).draw(batch, delta);
			((Unidad) entidades.get(i)).danar(1);
		}

		//No nos olvidemos de terminar el dibujo. Si algo se renderiza despues de esto, la aplicacion PETARA!
		batch.end();
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
		atlas.dispose();
		atextura.dispose();
		//Camaras y sprites no se necesitan limpiar
	}

}