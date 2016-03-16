package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Juber on 11/03/2016.
 */
public class SplashScreen extends BaseScreen {

    protected MainClass game;;

    private SpriteBatch batch;
    private Texture title;

    public SplashScreen(MainClass game) {
        super(game);
        this.game = game;
        batch = new SpriteBatch();
        title = new Texture("title.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(title, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        title.dispose();
    }
}