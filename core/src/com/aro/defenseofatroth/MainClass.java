package com.aro.defenseofatroth;

import com.aro.defenseofatroth.Screens.LoginScreen;
import com.aro.defenseofatroth.Screens.MenuScreen;
import com.aro.defenseofatroth.Screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

public class MainClass extends Game {

	private static AssetManager manager;
	private SplashScreen splashScreen;

	@Override
	public void create() {

		manager = new AssetManager();
		manager.load("torre.png", Texture.class);
		manager.load("data/uiskin.json", Skin.class);
		manager.load("barraRoja.png", Texture.class);
		manager.load("barraRojaBuena.png", Texture.class);
		manager.load("barraVerdeBuena.png", Texture.class);
		manager.load("music.ogg", Music.class);
		manager.load("badlogic.jpg", Texture.class);
		manager.load("barraVerde.png", Texture.class);
		manager.load("caveman-sheet.png", Texture.class);
		manager.load("libgdx.png", Texture.class);
		manager.load("loading.png", Texture.class);
		manager.load("NewPiskel2.png", Texture.class);
		manager.load("prehistoric.png", Texture.class);
		manager.load("prehistoric2.png", Texture.class);
		manager.load("target.png", Texture.class);
		manager.load("title.png", Texture.class);

		splashScreen = new SplashScreen(this);
		setScreen(splashScreen);
	}

	public void finishLoading() {
		setScreen(new LoginScreen(this));
	}

	public static AssetManager getManager() {
		return manager;
	}
}
