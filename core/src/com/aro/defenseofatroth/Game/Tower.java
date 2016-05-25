package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.Screens.Hud;
import com.aro.defenseofatroth.Screens.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**
 * Esta clase reprenta a una torre. Las clases hijas deberan especificar que clase de torre es.
 * Created by Sergio on 17/04/2016.
 */
public class Tower extends Actor {
    public static short TORRE_SENSOR_BIT=0x01; //Bit de colision con la torre
    protected Body cuerpo; //cuerpo del
    protected Vector2 posicion; //posicion
    protected ProyectileFactory proyectileFactory;
    protected TextureRegion textura;
    protected Array<Enemy> enemigosEnRango;
    protected UpButton upButton;
    protected int costeMejora;

    public Tower(){
        enemigosEnRango=new Array<Enemy>();
    }

    public void setTextura(TextureRegion textura) {
        this.textura = textura;
    }

    public void setCosteMejora(int costeMejora) {
        this.costeMejora = costeMejora;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
        cuerpo.setTransform(posicion,0);
        super.setPosition(posicion.x - textura.getRegionWidth() * 0.5f, posicion.y - textura.getRegionHeight() * 0.5f); //Cuidado. Hacer cuentas en papel, ya que en el actor esta posicion se refiere a la parte SUPERIOR IZQUIERDA, NO AL CENTRO
    }

    public void setCuerpo(Body cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void setProyectileFactory(ProyectileFactory proyectileFactory) {
        this.proyectileFactory = proyectileFactory;
    }

    /**
     * Llamado cuando el sensor choca contra un enemigo. Se añade a la lista de enemigos dentro
     * del sensor
     * @param enemigo enemigo encontrado
     */
    public void enemyInRange(Enemy enemigo){
        enemigosEnRango.add(enemigo);
    }

    /**
     * Se llama si el sensor deja de tener contacto o bien el
     * enemigo esta muerto.
     */
    public void enemyOutRange(Enemy enemigo){
        enemigosEnRango.removeValue(enemigo, true);
    }
    @Override
    public void draw(Batch bach,float delta){
        bach.draw(textura, posicion.x - textura.getRegionWidth() * 0.5f, posicion.y - textura.getRegionHeight() * 0.5f);
    }
    public void setButtonUpdate(){
        this.setWidth(textura.getRegionWidth());
        this.setDebug(true);
        this.setHeight(textura.getRegionHeight());
        final Tower ñapa=this;
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upButton = new UpButton(ñapa, costeMejora, getX(), getY() + textura.getRegionHeight()+30);
                getStage().addActor(upButton);
            }

        });
    }

    public boolean mejorar(){
        //Las clases hijas lo implementa. Deberia ser abstracto? Si, pero no pienso cambiar toda la estructura ahora
        Message.getInstance().say("Imposible mejorar");
        return false;
    }
    public void ocultar(){
        if (upButton!=null) {
            upButton.remove();
            upButton=null;
        }
    }
}
