package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.Game.TextureLoader;
import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_HEIGHT;
import static com.aro.defenseofatroth.Tools.Constants.VIRTUAL_WIDTH;

/**
 * Si, tiene delito hacer una clase que solo tiene un constructor...
 * Created by elementary on 7/04/16.
 */
public class Selector {

    public Stage stage;
    private Viewport viewport;
    private Array<Image> imagenes;

    public Selector(SpriteBatch spriteBatch) {

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        // Shader pa hacer gris guardo el codigo por si sirve
//        String vertexShader = Gdx.files.internal("vertex.glsl").readString();
//        String fragmentShader = Gdx.files.internal("fragment.glsl").readString();
//        ShaderProgram shaderProgram = new ShaderProgram(vertexShader, fragmentShader);

        Table table = new Table();
        table.defaults().width(100).height(100);
        table.bottom();
        table.setFillParent(true);

        Image torre = new Image(TextureLoader.getInstance().obtenerBasicTower());
        torre.setUserObject(new Integer(1)); //Ids de las torres, para despues saber cual es cual
        table.add(torre);

        Image torre2 = new Image(TextureLoader.getInstance().obtenerMisileTower_I());
        torre2.setUserObject(new Integer(2));
        table.add(torre2);

        Image torre3 = new Image(TextureLoader.getInstance().obtenerLaserTower_I());
        torre3.setUserObject(new Integer(3));
        table.add(torre3);

        //Se añaden las torres (actores) al array que sera usado en el selector
        imagenes=new Array<Image>();
        imagenes.add(torre);
        imagenes.add(torre2);
        imagenes.add(torre3);

        stage.addActor(table);
    }

    public Array<Image> getImagenes() {
        return imagenes;
    }
}
