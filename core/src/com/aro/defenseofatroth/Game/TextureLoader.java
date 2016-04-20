package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Esta clase se encarga de cargar las texturas en memoria, de tal forma que no se cargen varias
 * veces la misma textura en la GPU por diferentes unidades. Tambien es la encargada de cargar lo
 * necesario en memoria antes de que empiece el nivel.
 * Created by Sergio on 17/04/2016.
 */
public class TextureLoader implements Disposable{
    World mundo; //lo tengo para darselo a las Factorias
    Stage escenario; //lo tengo para darselo a las Factorias

    private TextureAtlas barrasVida; //las barras de vida (pueden ir en otro atlas , solo son 2)
    private TextureAtlas torres; //las torres del juego
    private TextureAtlas proyectiles; //los proyectiles del juego

    //Animaciones

    public Animation getBasicTankHoriz() {
        return basicTankHoriz;
    }

    private Animation basicTankHoriz;



    //Se deberia de usar un asert manager para mostrar una barra de carga
    public void cargar(){
        //TODO hacer. Aqui se cargarian los atlas
        barrasVida=new TextureAtlas(Gdx.files.internal("barrasVida.atlas"));
        TextureAtlas atextura = new TextureAtlas(Gdx.files.internal("caveman.atlas"));
        Array<TextureAtlas.AtlasRegion> anima = new Array<TextureAtlas.AtlasRegion>(atextura.getRegions());
        basicTankHoriz=new Animation(0.05f, anima, Animation.PlayMode.LOOP);
    }

    public TextureRegion obtenerBarraRoja(){
        return barrasVida.findRegion("barraRoja");
    }
    public TextureRegion obtenerBarraVerde(){
        return barrasVida.findRegion("barraVerde");
    }


    public Stage getEscenario() {
        return escenario;
    }

    public void setEscenario(Stage escenario) {
        this.escenario = escenario;
    }

    public World getMundo() {
        return mundo;
    }

    public void setMundo(World mundo) {
        this.mundo = mundo;
    }
    @Override
    public void dispose() {
        barrasVida.dispose();
    }
}
