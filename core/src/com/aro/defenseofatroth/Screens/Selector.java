package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_HEIGHT;
import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_WIDTH;

/**
 * Created by elementary on 7/04/16.
 */
public class Selector {

    public Stage stage;
    private Viewport viewport;

    public Selector(SpriteBatch spriteBatch) {

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.defaults().width(100).height(100);
        table.bottom();
        table.setFillParent(true);

        Image torre = new Image(MainClass.getManager().get("torre.png", Texture.class));
        table.add(torre);

        Image torre2 = new Image(MainClass.getManager().get("torre.png", Texture.class));
        table.add(torre2);

        Image torre3 = new Image(MainClass.getManager().get("torre.png", Texture.class));
        table.add(torre3);

        stage.addActor(table);
    }


}
