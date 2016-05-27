package com.aro.defenseofatroth;

import com.aro.defenseofatroth.Screens.LoginScreen;
import com.aro.defenseofatroth.Screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class MainClass extends Game{

	private static AssetManager manager;
	private SplashScreen splashScreen;

	@Override
	public void create() {

		manager = new AssetManager();
		manager.load("atlas/texturas.pack", TextureAtlas.class);
        manager.load("atlas/Camion.pack", TextureAtlas.class);
        manager.load("atlas/Inf.pack", TextureAtlas.class);
        manager.load("atlas/spr.pack", TextureAtlas.class);
        manager.load("data/uiskin.json", Skin.class);
        manager.load("mapaFinal.png", Texture.class);
        //
		manager.load("torre.png", Texture.class);
        manager.load("torre2.png", Texture.class);
		manager.load("barraRoja.png", Texture.class);
		manager.load("barraRojaBuena.png", Texture.class);
		manager.load("barraVerdeBuena.png", Texture.class);
		manager.load("music.ogg", Music.class);
		manager.load("barraVerde.png", Texture.class);
		manager.load("caveman-sheet.png", Texture.class);
		manager.load("prehistoric.png", Texture.class);
		manager.load("target.png", Texture.class);
		manager.load("title.png", Texture.class);
		//Carga de las texturas del juego en si
		manager.load("barrasVida.atlas", TextureAtlas.class);
        manager.load("caveman.atlas", TextureAtlas.class);
		manager.load("mejorar.png", Texture.class);
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
