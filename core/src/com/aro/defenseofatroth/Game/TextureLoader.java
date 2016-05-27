package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private TextureAtlas texturas; //las torres del juego
    private BitmapFont font;
    private Animation sprinter;
    private Animation camion;
    private Animation infanteria;
   //Ahora es un singleton
    private static TextureLoader textureLoader=new TextureLoader();

    public static TextureLoader getInstance(){
        return textureLoader;
    }

    public void cargar(){
        texturas=MainClass.getManager().get("atlas/texturas.pack", TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> auxspr=MainClass.getManager().get("atlas/spr.pack", TextureAtlas.class).getRegions();
        sprinter=new Animation(3.5f,auxspr, Animation.PlayMode.NORMAL);
        Array<TextureAtlas.AtlasRegion> auxcam=MainClass.getManager().get("atlas/Camion.pack", TextureAtlas.class).getRegions();
        camion=new Animation(3.5f,auxcam, Animation.PlayMode.NORMAL);
        Array<TextureAtlas.AtlasRegion> auxinf=MainClass.getManager().get("atlas/Inf.pack", TextureAtlas.class).getRegions();
        infanteria=new Animation(3.5f,auxinf, Animation.PlayMode.NORMAL);
        font=new BitmapFont(Gdx.files.internal("data/default.fnt"),Gdx.files.internal("data/default.png"), false);
    }

    public TextureRegion obtenerBarraRoja(){
        return texturas.findRegion("barraRoja");
    }
    public TextureRegion obtenerBarraVerde(){
        return texturas.findRegion("barraVerde");
    }
    public TextureRegion obtenerMejorar(){
        return texturas.findRegion("mejorar");
    }
    public TextureRegion obtenerTarget(){
        return texturas.findRegion("target");
    }
    public TextureRegion obtenerBasicTower(){
        return texturas.findRegion("Basiktower");
    }
    public TextureRegion obtenerProyectilBasicTower(){
        return texturas.findRegion("Basiktower");//TODO hacer
    }
    public TextureRegion obtenerMissile(){
        return texturas.findRegion("misil");
    }
    public TextureRegion obtenerCamion(){
        return texturas.findRegion("camion");
    }
    public TextureRegion obtenerInfanteria(){
        return texturas.findRegion("infanteria");
    }
    public TextureRegion obtenerLaserTower_I(){
        return texturas.findRegion("LaserTower_I");
    }
    public TextureRegion obtenerLaserTower_D(){
        return texturas.findRegion("LaseTower_D");
    }
    public TextureRegion obtenerMisileTower_D(){
        return texturas.findRegion("MisileTower_D");
    }
    public TextureRegion obtenerMisileTower_I(){
        return texturas.findRegion("MisileTower_I");
    }
    public TextureRegion obtenerSprinter(){
        return texturas.findRegion("sprinter");
    }
    public Animation obtenerAnimaSprinter(){
        return sprinter;
    }
    public Animation obtenerAnimaInfanteria(){
        return infanteria;
    }
    public Animation obtenerAnimaCamion(){
        return camion;
    }
    public BitmapFont obtenerFont(){
        return font;
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
        if (texturas !=null) {
            texturas.dispose();
            texturas =null;
        }
        if (font!=null){
            font.dispose();
            font=null;
        }
    }
}
