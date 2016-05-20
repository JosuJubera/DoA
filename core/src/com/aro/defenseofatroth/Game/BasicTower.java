package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Esta clase representa a una torre en el juego, concretamente a una torre basica.
 * Created by Sergio on 17/04/2016.
 */
public class BasicTower extends Tower{
    protected static float ALCANCE=400f; //Si se modifica e alcance, hay que modificarlo en el sensor tambien (por si hacemos que aumente al subir de lvl)
    private BitmapFont font= new BitmapFont(Gdx.files.internal("data/default.fnt"),Gdx.files.internal("data/default.png"), false); //para debugear

    public float getTiempoEntreAtaques() {
        return tiempoEntreAtaques;
    }

    public void setTiempoEntreAtaques(float tiempoEntreAtaques) {
        this.tiempoEntreAtaques = tiempoEntreAtaques;
    }

    public float getTiempoSiguienteAtaque() {
        return tiempoSiguienteAtaque;
    }

    public void setTiempoSiguienteAtaque(float tiempoSiguienteAtaque) {
        this.tiempoSiguienteAtaque = tiempoSiguienteAtaque;
    }

    protected float tiempoEntreAtaques; //Tiemmpo entre ataques
    protected float tiempoSiguienteAtaque; //Tiempo restante para el siguiente ataque


    private void atacar(){
        //TODO hacer. Crear proyectil y enviarlo
        Proyectile aux=super.proyectileFactory.obtenerProyectilTorreBasica(enemigo,posicion);
        aux.setDaino(50); //Se puede subir el lvl de la torre y que meta mas?
    }

    public void draw(Batch bach,float delta){
        super.draw(bach, delta);
        font.setColor(Color.WHITE);
        font.draw(bach, "Estado: "+estado+"\n Tiempo sigueinte Ataque "+tiempoSiguienteAtaque,posicion.x-textura.getRegionWidth()*0.5f,posicion.y-textura.getRegionWidth()*0.5f-10);//Debug, borrrar
    }

    @Override
    public void act(float delta){
        if (estado==2) {
            tiempoSiguienteAtaque -= delta;
            if (tiempoSiguienteAtaque <= 0) { //hora de atacar!
                tiempoSiguienteAtaque = tiempoEntreAtaques;
                if (enemigo.isViva()) {
                    atacar();
                } else {
                    this.libre();
                }
            }
        }
    }





}
