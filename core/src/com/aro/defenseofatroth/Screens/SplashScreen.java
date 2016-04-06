package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Tools.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_WIDTH;
import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_HEIGHT;

/**
 * Created by Juber on 11/03/2016.
 */
public class SplashScreen extends BaseScreen {

//    protected MainClass game;;
//    private Stage stage;
//    private ProgressBar bar;
//
////    private SpriteBatch batch;
////    private Texture title;
////    private Label loading;
////    private Stage stage;
////
////    public SplashScreen(MainClass game) {
////        super(game);
////        this.game = game;
////        stage = new Stage();
////        batch = new SpriteBatch();
////        title = new Texture("title.png");
////        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
////        loading = new Label("Loading...", skin);
////        loading.setPosition(VIRTUAL_WIDTH / 2 - VIRTUAL_WIDTH / 8 , VIRTUAL_HEIGHT * 2 / 8);
////        stage.addActor(loading);
////    }
////
////    @Override
////    public void render(float delta) {
////        Gdx.gl.glClearColor(0, 0, 0, 1);
////        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
////
////        batch.begin();
////        batch.draw(title, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
////        batch.end();
////        stage.act();
////        stage.draw();
////    }
////
////    @Override
////    public void dispose() {
////        batch.dispose();
////        title.dispose();
////        stage.dispose();
////    }
//
//    public SplashScreen(MainClass game) {
//        super(game);
//        this.game = game;
//        create();
//    }
//
//    public void create() {
//        Gdx.input.setInputProcessor(stage = new Stage());
//        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//
//        Table table = new Table(skin);
//        table.setFillParent(true);
//
//        bar = new ProgressBar(0, 100, 1, false, skin);
//        bar.setAnimateDuration(.75f);
//
//        table.add(bar).size(500, 10);
//
//        stage.addActor(table);
//    }
//
//    float time, duration = 1;
//
//
//    public void render() {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        float delta = Gdx.graphics.getDeltaTime();
//
//        if (game.getManager().update()) {
//            game.setScreen(game.setScreen(new LoginScreen(game));
//        } else {
//
//        }
//
//        if((time += delta) > duration) {
//            bar.setValue(bar.getValue() < 50 ? 100 : 0);
//            time = 0;
//        }
//
//        stage.act(delta);
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//    }
//
//    // if used as Screen
//
//    @Override
//    public void show() {
//        create();
//    }
//
//    @Override
//    public void render(float delta) {
//        render();
//    }
//
//    @Override
//    public void hide() {}

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