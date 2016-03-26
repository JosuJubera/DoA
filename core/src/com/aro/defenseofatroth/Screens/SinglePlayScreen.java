package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.Levels.Level1;
import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.TextField;

/**
 * Created by Juber on 11/03/2016.
 */
public class SinglePlayScreen extends BaseScreen {

    private static int VIRTUAL_WIDTH = 800;
    private static int VIRTUAL_HEIGHT = 600;
    protected MainClass game;

    private Viewport viewport;
    private Stage stage;

    private BitmapFont font;
    private Skin skin;
    private boolean musica;

    public SinglePlayScreen(MainClass game, boolean musica){
        super(game);
        this.game = game;
        this.musica = musica;
        create();
        render(Gdx.graphics.getDeltaTime());
    }

    public void create() {

        VIRTUAL_WIDTH = Gdx.app.getGraphics().getWidth();
        VIRTUAL_HEIGHT = Gdx.app.getGraphics().getHeight();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        stage = new Stage(viewport);

        //Create a font
        font = new BitmapFont(Gdx.files.internal("data/default.fnt"));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() /10,(int)Gdx.graphics.getHeight()/7, Pixmap.Format.RGB888); //Tamaño boton relacion alto = 0.75 * ancho
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        TextButton back = new TextButton("Volver", skin); // Use the initialized skin
        back.setPosition(VIRTUAL_WIDTH - VIRTUAL_WIDTH * 1 /10 , VIRTUAL_HEIGHT * 6 / 7); // desde bottomleft
        stage.addActor(back);

        // Back button listener
        back.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Menu(game));
            };
        });

        // Levels
        TextButton n1 = new TextButton("N1", skin);
        n1.setPosition(VIRTUAL_WIDTH - VIRTUAL_WIDTH * 7 / 10, VIRTUAL_HEIGHT * 5 / 7);
        stage.addActor(n1);

        n1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level1(game, musica));
            }
        });

        TextButton n2 = new TextButton("N2", skin);
        n2.setPosition(VIRTUAL_WIDTH - VIRTUAL_WIDTH * 5 / 10, VIRTUAL_HEIGHT * 2 / 7);
        stage.addActor(n2);

        n2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level1(game, musica));
            }
        });

        TextButton n3 = new TextButton("N3", skin);
        n3.setPosition(VIRTUAL_WIDTH - VIRTUAL_WIDTH * 3 / 10, VIRTUAL_HEIGHT * 4 / 7);
        stage.addActor(n3);

        n3.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level1(game, musica));
            };
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1 / 60f));
        stage.draw();
    }

    @Override
    public void dispose() {
        game.dispose();
        stage.dispose();
        font.dispose();
        skin.dispose();
    }
}
