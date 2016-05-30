package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.WS.ResponseWS;
import com.aro.defenseofatroth.WS.WebServices;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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

    public static String userName = "Invitado";
    public static String email = "Invitado";

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
        back.setPosition(VIRTUAL_WIDTH - VIRTUAL_WIDTH * 1 / 10, VIRTUAL_HEIGHT * 6 / 7); // desde bottomleft
        stage.addActor(back);

        // Back button listener
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        Label mailLabelLogin = new Label("Mail",new Label.LabelStyle(font, Color.WHITE));
        mailLabelLogin.setPosition(VIRTUAL_WIDTH / 16, VIRTUAL_HEIGHT * 8 / 10);
        stage.addActor(mailLabelLogin);

        // Login & Register
        final TextField mailFieldLogin = new TextField("", skin);
        mailFieldLogin.setPosition(3*VIRTUAL_WIDTH /16 , VIRTUAL_HEIGHT * 8 / 10);
        mailFieldLogin.setSize(VIRTUAL_WIDTH / 10, VIRTUAL_HEIGHT / 10);
        stage.addActor(mailFieldLogin);

        Label passLabel = new Label("Pass",new Label.LabelStyle(font, Color.WHITE));
        passLabel.setPosition(VIRTUAL_WIDTH / 16, VIRTUAL_HEIGHT * 6 / 10);
        stage.addActor(passLabel);

        final TextField passFieldLogin = new TextField("", skin);
        passFieldLogin.setPosition(3 * VIRTUAL_WIDTH / 16, VIRTUAL_HEIGHT * 6 / 10);
        passFieldLogin.setSize(VIRTUAL_WIDTH / 10, VIRTUAL_HEIGHT / 10);
        passFieldLogin.setPasswordCharacter('*');
        passFieldLogin.setPasswordMode(true);
        stage.addActor(passFieldLogin);

        Label nameLabelRegister = new Label("Name",new Label.LabelStyle(font,Color.WHITE));
        nameLabelRegister.setPosition(VIRTUAL_WIDTH * 8 / 16, VIRTUAL_HEIGHT * 4 / 10);
        stage.addActor(nameLabelRegister);

        final TextField nameFieldRegister = new TextField("", skin);
        nameFieldRegister.setPosition(VIRTUAL_WIDTH * 10 / 16 , VIRTUAL_HEIGHT * 4 / 10);
        nameFieldRegister.setSize(VIRTUAL_WIDTH / 10, VIRTUAL_HEIGHT / 10);
        stage.addActor(nameFieldRegister);

        final TextField passFieldRegister = new TextField("", skin);
        passFieldRegister.setPosition( VIRTUAL_WIDTH*10 / 16, VIRTUAL_HEIGHT * 6 / 10);
        passFieldRegister.setSize(VIRTUAL_WIDTH / 10, VIRTUAL_HEIGHT / 10);
        passFieldRegister.setPasswordCharacter('*');
        passFieldRegister.setPasswordMode(true);
        stage.addActor(passFieldRegister);

        final TextField emailFieldRegister = new TextField("", skin);
        emailFieldRegister.setPosition(VIRTUAL_WIDTH * 10 / 16, VIRTUAL_HEIGHT * 8 / 10);
        emailFieldRegister.setSize(VIRTUAL_WIDTH / 10, VIRTUAL_HEIGHT / 10);
        stage.addActor(emailFieldRegister);

        final TextButton submit = new TextButton("SUBMIT", skin);
        submit.setPosition(3*VIRTUAL_WIDTH /16, VIRTUAL_HEIGHT / 10);
        stage.addActor(submit);
        final TextButton register = new TextButton("REGISTER", skin);
        register.setPosition(VIRTUAL_WIDTH*10 /16, VIRTUAL_HEIGHT / 10);
        stage.addActor(register);

        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String mail = mailFieldLogin.getText();
                String pass = passFieldLogin.getText();

                if (mail == "" | pass == "") {
                    new Dialog("Error", skin) {
                        {
                            text("Some field is empty");
                            button("ok").getButtonTable().row();
                        }
                    }.show(stage);

                    submit.setChecked(false);
                } else {
                    User user=login(mail,pass);
                    if (user!=null) {
                        userName = user.getNombre();
                        email=user.getMail();
                        game.setScreen(new MenuScreen(game));
                    } else {
                        new Dialog("Error", skin) {
                            {
                                text("Try again");
                                button("ok").getButtonTable().row();
                            }
                        }.show(stage);

                        submit.setChecked(false);
                    }
                }
            }
        });

        register.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = nameFieldRegister.getText();
                String pass = passFieldRegister.getText();
                String email= emailFieldRegister.getText();

                if(name==""|pass==""||email==""){
                    new Dialog("Error",skin){
                        {
                            text("Some field is empty");
                            button("ok").getButtonTable().row();
                        }
                    }.show(stage);

                    register.setChecked(false);
                }else {
                    final String aux=register(name, pass,email);
                    if (aux=="ok") {
                        game.setScreen(new MenuScreen(game));
                    } else {
                        new Dialog("Error", skin) {
                            {
                                text(aux);
                                button("ok").getButtonTable().row();
                            }
                        }.show(stage);

                        register.setChecked(false);
                    }
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

    private User login(String mail, String pass) {
        ResponseWS rws1 = new WebServices().updateScore(mail,100);
        ResponseWS rws2 = new WebServices().getRanking(5);
        User user = new WebServices().login(mail,pass);
        return user;//comprobar si es null fuera

    }

    private String register(String name, String pass, String email){
        ResponseWS rws=new WebServices().createUser(name,pass,email);
        if(rws.getExito()==true){
            //registro correcto
            return "ok";
        }else{
            //registro incorrecto
            return rws.getError();
        }
    }
}
