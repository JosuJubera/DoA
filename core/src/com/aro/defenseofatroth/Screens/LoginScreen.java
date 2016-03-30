package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.WS.WebServices;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.aro.defenseofatroth.WS.User;

/**
 * Created by elementary on 26/03/16.
 */
public class LoginScreen extends BaseScreen {

    private static int VIRTUAL_WIDTH = 800;
    private static int VIRTUAL_HEIGHT = 600;
    protected MainClass game;

    private Viewport viewport;
    private Stage stage;

    private BitmapFont font;
    private Skin skin;

    public LoginScreen(MainClass game) {
        super(game);
        this.game = game;
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

        TextButton back = new TextButton("Saltar", skin); // Use the initialized skin
        back.setPosition(VIRTUAL_WIDTH - VIRTUAL_WIDTH * 1 /10 , VIRTUAL_HEIGHT * 6 / 7); // desde bottomleft
        stage.addActor(back);

        // Back button listener
        back.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        Label nameLabel = new Label("Name",new Label.LabelStyle(font, Color.WHITE));
        nameLabel.setPosition(VIRTUAL_WIDTH / 2 - VIRTUAL_WIDTH / 8, VIRTUAL_HEIGHT * 7 / 8);
        stage.addActor(nameLabel);

        // Login
        final TextField nameField = new TextField("", skin);
        nameField.setPosition(VIRTUAL_WIDTH / 2 - VIRTUAL_WIDTH / 8, VIRTUAL_HEIGHT * 6 / 8);
        nameField.setSize(VIRTUAL_WIDTH / 10, VIRTUAL_HEIGHT / 10);
        stage.addActor(nameField);

        Label passLabel = new Label("Pass",new Label.LabelStyle(font, Color.WHITE));
        passLabel.setPosition(VIRTUAL_WIDTH / 2 - VIRTUAL_WIDTH / 8, VIRTUAL_HEIGHT * 5 / 8);
        stage.addActor(passLabel);

        final TextField passField = new TextField("", skin);
        passField.setPosition(VIRTUAL_WIDTH / 2 - VIRTUAL_WIDTH / 8, VIRTUAL_HEIGHT * 4 / 8);
        passField.setSize(VIRTUAL_WIDTH / 10, VIRTUAL_HEIGHT / 10);
        passField.setPasswordCharacter('*');
        passField.setPasswordMode(true);
        stage.addActor(passField);


        TextButton submit = new TextButton("SUBMIT", skin);
        submit.setPosition(VIRTUAL_WIDTH / 2 - VIRTUAL_WIDTH / 8, VIRTUAL_HEIGHT / 8);
        stage.addActor(submit);

        submit.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = nameField.getText();
                String pass = passField.getText();

                if (login(name, pass)) {

                    System.out.println(name + " " + pass);

                    game.setScreen(new MenuScreen(game));
                }
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

    private boolean login(String name, String pass) {
        User user = new WebServices().login(name,pass);
        System.out.println(user.getNombre()+"  "+user.getEmail());
        if (user!=null){
            return true;
        }else{
            return false;
        }
    }
}
