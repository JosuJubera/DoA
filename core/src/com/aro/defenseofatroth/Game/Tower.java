package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Esta clase reprenta a una torre. Las clases hijas deberan especificar que clase de torre es.
 * Created by Sergio on 17/04/2016.
 */
public class Tower extends Actor {
    public static short TORRE_SENSOR_BIT=0x01; //Bit de colision con la torre
    protected Enemy enemigo;
    protected int estado; //1 ociosa, 2 atacando
    protected Body cuerpo; //cuerpo del
    protected Vector2 posicion; //posicion
    protected ProyectileFactory proyectileFactory;
    protected TextureRegion textura;

    public void setTextura(TextureRegion textura) {
        this.textura = textura;
    }

    public Enemy getEnemigo() {
        return enemigo;
    }


    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
        cuerpo.setTransform(posicion,0);
        this.setPosition(posicion.x,posicion.y);
    }

    public void setCuerpo(Body cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void setProyectileFactory(ProyectileFactory proyectileFactory) {
        this.proyectileFactory = proyectileFactory;
    }

    /**
     * Llamado cuando el sensor choca contra un enemigo. Si esta ociosa, lo marcara para atacar y
     * cambiara su estado. Si no no hace nada
     * @param enemigo enemigo encontrado
     */
    public void establecerObjetivo(Enemy enemigo){
        if (estado!=2) {
            this.enemigo = enemigo;
            estado = 2;
        }
    }

    /**
     * Cambia de estado de atacando a ociosa. Se llama si el sensor deja de tener contacto o bien el
     * enemigo esta muerto.
     */
    public void libre(){
        if (estado!=1){
            this.enemigo=null;
            estado=1;
        }
    }
    @Override
    public void draw(Batch bach,float delta){
        bach.draw(textura,posicion.x,posicion.y);
    }
}
