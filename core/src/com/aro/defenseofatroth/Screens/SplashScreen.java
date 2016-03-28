package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.aro.defenseofatroth.Constants.VIRTUAL_WIDTH;
import static com.aro.defenseofatroth.Constants.VIRTUAL_HEIGHT;

/**
 * Created by Juber on 11/03/2016.
 */
public class SplashScreen extends BaseScreen {

    protected MainClass game;;

    private SpriteBatch batch;
    private Texture title;
    private Label loading;
    private Stage stage;

    public SplashScreen(MainClass game) {
        super(game);
        this.game = game;
        stage = new Stage();
        batch = new SpriteBatch();
        title = new Texture("title.png");
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        loading = new Label("Loading...", skin);
        loading.setPosition(VIRTUAL_WIDTH / 2 - VIRTUAL_WIDTH / 8 , VIRTUAL_HEIGHT * 2 / 8);
        stage.addActor(loading);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(title, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        title.dispose();
        stage.dispose();
    }
}