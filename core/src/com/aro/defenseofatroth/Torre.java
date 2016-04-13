package com.aro.defenseofatroth;

import com.aro.defenseofatroth.Levels.Level1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * Created by Sergio on 12/04/2016.
 */
public class Torre extends Entidad {
    private static float TIEMPO_ENTRE_ATAQUES=1500; //en milisegundos. Esto cambiara segun la torre
    private static float ALCANCE=15; //en unidades del mundo
    private static int DANIO=25;
    private float tiempoSiguienteAtaque;
    private Unidad enemigo;
    private int estado; //1 ociosa, 2 atacando


    public Torre(){
        super();
        tiempoSiguienteAtaque=0;
        enemigo=null;
        estado=1;
        texture=new Sprite(new Texture(Gdx.files.internal("barraVerde.png")));
        texture.scale(2f);
    }
    @Override
    public void draw(Batch batch, float delta) {
        actuar(delta); //esto deberia de ir fuera, pero es una prueba
        texture.draw(batch);
    }

    public void actuar(float delta){
        if (estado==1){
            //Buscamos si el enemigo esta al alcance
            if (Level1.niapa.posicion.dst(this.posicion)<ALCANCE){
                estado=2;
                this.enemigo=(Unidad) Level1.niapa;
            }
        }else if (estado==2){
            if ((!enemigo.isAlive()) || (enemigo.posicion.dst(this.posicion)>ALCANCE)){ //ha muerto o se a ido
                estado=1;
                enemigo=null;
                return;
            }
            if (tiempoSiguienteAtaque<=0){
                atacar();
                tiempoSiguienteAtaque=TIEMPO_ENTRE_ATAQUES;
            }else {
                tiempoSiguienteAtaque-=delta;
            }
        }
    }
    @Override
    public void dispose() {

    }
    private void atacar(){
        Proyectil proyectil=new Proyectil();
        proyectil.setPosicion(this.posicion);
        proyectil.setObjetivo(enemigo);
        proyectil.setDanio(DANIO);
        Level1.entidades.add(proyectil);

    }
}
