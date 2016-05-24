package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

/**
 * Clase que genera los enemigos en un mapa.
 * Created by Sergio on 19/05/2016.
 */
public class Generator{
    EnemyFactory enemyFactory; //Factoria de los enemigos
    Array<Vector2> ruta; //Ruta que seguiran los enemigos
    int size; //Numero de enemigos a generar
    float entropy; //Entropia en la ruta. Solo afecta a la altura. Es para que sean paralelos
    int creados=0;
    float frecuency; //Tiempo entre cada enemigo
    float tiempo; //Tiempo para el siguiente


    public void actualizar(float delta){
        tiempo-=delta;
        if ((tiempo<0) && (creados<size)){
            tiempo=frecuency;
            enemyFactory.obtenerTankeBasico();
            creados++;
        }
    }

    public void reset(){
        creados=0;
    }

    public int getCreados() {
        return creados;
    }
    /**
     * Para debugear, genera enemigos con la configuracion por defecto
     */
    public void setDefault(){
        creados=0;
        entropy=2;
        size=40;
        frecuency=1.5f;
        tiempo=1.5f;
        ruta=new Array<Vector2>();
        ruta.add(new Vector2(0, 0));
        ruta.add(new Vector2(300, 100));
        ruta.add(new Vector2(400, 400));
        ruta.add(new Vector2(500, 200));
        ruta.add(new Vector2(600, 600));
        ruta.add(new Vector2(1000, 0));
        ruta.add(new Vector2(100000, 0));
        enemyFactory.setRuta(ruta);
    }

    public EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }

    public void setEnemyFactory(EnemyFactory enemyFactory) {
        this.enemyFactory = enemyFactory;
    }

    public Array<Vector2> getRuta() {
        return ruta;
    }

    public void setRuta(Array<Vector2> ruta) {
        this.ruta = ruta;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
