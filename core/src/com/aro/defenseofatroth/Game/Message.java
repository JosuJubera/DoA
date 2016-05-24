package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Esta clase sirve para mostrar mensajes in-game (como falta oro y demas)
 * Created by Sergio on 24/05/2016.
 */
public class Message {
    private static Message ourInstance = new Message();

    public static Message getInstance() {
        return ourInstance;
    }
    Stage stage;
    Label mensaje;


    private Message() {
    }
    public void setStage(Stage stage) {
        this.stage = stage;
        BitmapFont font = new BitmapFont(Gdx.files.internal("data/default.fnt"));
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        skin.add("default", font);
        mensaje=new Label("",skin);
        mensaje.setColor(Color.RED);
        mensaje.setPosition(0.4f * stage.getWidth(), 0.8f * stage.getHeight());
        mensaje.setFontScale(5f);
        stage.addActor(mensaje);
    }
    public boolean say(String message){
        if (mensaje!=null) {
            mensaje.setText(message);
            mensaje.addAction(Actions.show());
            mensaje.addAction(Actions.sequence(Actions.delay(2f), Actions.hide()));
           // stage.addActor(mensaje);
            return true;
        }
        return false;
    }


}
