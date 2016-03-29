package com.aro.defenseofatroth;

import com.aro.defenseofatroth.Screens.LoginScreen;
import com.aro.defenseofatroth.Screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class MainClass extends Game {

	private static long SPLASH_MINIMUM_MILLIS = 1000L;

	protected Game game;

	private static AssetManager manager;

	public static AssetManager getManager() {
		return manager;
	}

	public MainClass() {
		super();
	}

	@Override
	public void create () {
		game =  this;
		//setScreen(new Menu(this)); //si queremos quitar el splashscreen
		// SplashScreen

		setScreen(new SplashScreen(this));

		final long splash_start_time = System.currentTimeMillis();
		new Thread(new Runnable() {
			@Override
			public void run() {

				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						// ... carga de datos
						// ... carga de fuentes tipograficas
						// ... carga de sonidos
						// ... carga de imagenes
						// ... carga de recursos de internacionalizacion
						// ... otros

						manager = new AssetManager();
						manager.load("music.ogg", Music.class);
						manager.load("torre.png", Texture.class);
						manager.load("barraRoja.png", Texture.class);
						manager.load("barraVerde.png", Texture.class);
						manager.finishLoading();

						// Se muestra el menu principal tras la SpashScreen
						long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;
						if (splash_elapsed_time < MainClass.SPLASH_MINIMUM_MILLIS) {
							Timer.schedule(
									new Timer.Task() {
										@Override
										public void run() {
											MainClass.this.setScreen(new LoginScreen((MainClass) game));
										}
									}, (float)(MainClass.SPLASH_MINIMUM_MILLIS - splash_elapsed_time) / 1000f);
						} else {
							MainClass.this.setScreen(new LoginScreen((MainClass) game));
						}
					}
				});
			}
		}).start();

		// SplashScreen
	}

	@Override
	public void dispose() {
		//stage.dispose();
		//getScreen().dispose();
		//game.dispose();
		manager.dispose();
		Gdx.app.exit();
	}
}
