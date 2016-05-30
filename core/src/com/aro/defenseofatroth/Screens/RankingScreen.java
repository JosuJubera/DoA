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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * Created by Javier on 25/05/2016.
 */
public class RankingScreen extends BaseScreen{

        private static int VIRTUAL_WIDTH = 800;
        private static int VIRTUAL_HEIGHT = 600;
        protected MainClass game;

        private Viewport viewport;
        private Stage stage;

        private BitmapFont font;
        private Skin skin;


        public RankingScreen(MainClass game) {
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

            TextButton back = new TextButton("Volver", skin); // Use the initialized skin
            back.setPosition(VIRTUAL_WIDTH - VIRTUAL_WIDTH * 1 / 10, VIRTUAL_HEIGHT * 6 / 7); // desde bottomleft
            stage.addActor(back);

            // Back button listener
            back.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game));
                }
            });

            Label titulo = new Label("TOP SCORES",new Label.LabelStyle(font,Color.WHITE));
            titulo.setPosition(VIRTUAL_WIDTH * 7 / 16, VIRTUAL_HEIGHT *12/ 14);
            titulo.setFontScale(5);
            stage.addActor(titulo);
            ResponseWS rws2 = new WebServices().getRanking(5);

            if(rws2.getExito()){
                ArrayList data= (ArrayList)rws2.getData();
                int size = data.size();
                Label[] labelarray = new Label[5];
                for(int i=0;i<size;i++){
                    String cadena =((String)((LinkedTreeMap)data.get(i)).get("nombre"))+"       <-->       "+((String)((LinkedTreeMap)data.get(i)).get("puntuacion")); ;
                    labelarray[i]=new Label(cadena,new Label.LabelStyle(font,Color.WHITE));
                    labelarray[i].setPosition(VIRTUAL_WIDTH * 7 / 16, VIRTUAL_HEIGHT * (10-(i*2) )/ 14);
                    labelarray[i].setFontScale(3);
                    stage.addActor(labelarray[i]);
                }


            }else{
                //error
                new Dialog("Error", skin) {
                    {
                        text("Something went wrong, please try again later");
                        button("ok").getButtonTable().row();
                    }
                }.show(stage);
            }



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

}
