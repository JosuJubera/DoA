package com.aro.defenseofatroth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.prism.image.ViewPort;

public class MainClass  extends InputAdapter implements ApplicationListener {
	private OrthographicCamera camera; //camara principal.
	private SpriteBatch batch; //representa el MUNDO.
	private TextureAtlas atlas; //imagen con las texturas Tambien se puede hacer mediante codigo Pag:166
	private Sprite background; //se usa para manejar tamaño y posicion de texturas (Se puede cargar desde un Atlas)
	private FitViewport viewport; //representa la imagen en PANTALLA
	private Texture levelTexture;
	private GestureDetector gestureDetector;
	//Constantes de Camara
	private static final float CAMERA_SPEED = 2.0f;
	private static final float CAMERA_ZOOM_SPEED = 2.0f;
	private static final float CAMERA_ZOOM_MAX = 1.0f;
	private static final float CAMERA_ZOOM_MIN = 0.01f;
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


	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(); //camara orthografica, es en 2D!
		viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //al viewport se le pasa la camara ¡Si no no muestra nada!
		atlas= new TextureAtlas(Gdx.files.internal("prehistoric.atlas"));
		background= new Sprite(atlas.findRegion("background"));
		background.setPosition(-background.getWidth() * 0.5f, -background.getHeight() * 0.5f);
		background.scale(2f);;
		gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE,
				TAP_COUNT_INTERVAL,
				LONG_PRESS_DURATION,
				MAX_FLING_DELAY,
				new GestureHandler(camera));

		Gdx.input.setInputProcessor(gestureDetector);
	}

	@Override
	public void render () {
		//Limpiamos el frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();


		camera.update();
		batch.setProjectionMatrix(camera.combined);
		//Preparamos para dibujar
		batch.begin();
		//Dibujamos, to.do lo que haya que renderizar ira aqui
		background.draw(batch);
		/*batch.draw(levelTexture,
				0.0f, 0.0f,
				0.0f, 0.0f,
				levelTexture.getWidth(), levelTexture.getHeight(),
				0.01f, 0.01f,
				0.0f,
				0, 0,
				levelTexture.getWidth(), levelTexture.getHeight(),
				false, false);*/
		//No nos olvidemos de terminar el dibujo. Si algo se renderiza despues de esto, la aplicacion PETARA!
		batch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose(){
		//Se libera memoria, necesario para evitar memory leaks
		//Liberar batch y atlas sobretodo!!!
		batch.dispose();
		atlas.dispose();
		//Camaras y sprites no se necesitan limpiar
	}

	public class GestureHandler implements GestureDetector.GestureListener
	{
        private OrthographicCamera camera;
        Vector3 touch;
        GestureHandler(OrthographicCamera camera){
            this.camera=camera;
            touch=new Vector3(0f,0f,0f);
        }
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			//addMessage("touchDown: x(" + x + ") y(" + y + ") pointer(" + pointer + ") button(" + button +")");
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			//addMessage("tap: x(" + x + ") y(" + y + ") count(" + count + ") button(" + button +")");
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			//addMessage("longPress: x(" + x + ") y(" + y + ")");
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			//addMessage("fling: velX(" + velocityX + ") velY(" + velocityY + ") button(" + button +")");
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
            //translate mueve la camara segun esas coordenadas (igual que camera.x+=x)
            camera.translate(-deltaX,deltaY);

			return false;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			//addMessage("panStop: x(" + x + ") y(" + y + ") pointer(" + pointer + ") button(" + button +")");
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			//addMessage("zoom: initialDistance(" + initialDistance + ") distance(" + distance + ")");
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
			//addMessage("pinch: initialP1(" + initialPointer1 + ") initialP2(" + initialPointer2 + ") p1(" + pointer1 + ") p2(" + pointer2 +")");
			return false;
		}

	}
}
