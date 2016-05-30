package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_HEIGHT;
import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_WIDTH;

/**
 * Created by Juber on 11/03/2016.
 */
public class SplashScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private ProgressBar progressBar;
    private Texture logo;
    private SpriteBatch batch;

    public SplashScreen(MainClass game) {
        super(game);

        stage = new Stage(new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

//        Table table = new Table(skin);
//        table.setFillParent(true);

        batch = new SpriteBatch();
        logo = new Texture(Gdx.files.internal("logo.png"));

        progressBar = new ProgressBar(0, 100, 1, false, skin);
//        progressBar.setAnimateDuration(.75f);


//        table.add(progressBar).size(500, 10);

        stage.addActor(progressBar);
        progressBar.setPosition(VIRTUAL_WIDTH /2 - VIRTUAL_WIDTH /8 , VIRTUAL_HEIGHT * 2 /8);
        progressBar.setSize(VIRTUAL_WIDTH / 3 , 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(logo, 0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);
        batch.end();

        if (game.getManager().update()) {
            game.finishLoading();
        } else {
            int progress = (int) (game.getManager().getProgress() * 100);
            progressBar.setValue(progress);
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}