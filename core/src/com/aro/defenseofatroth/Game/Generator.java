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
    float delay; //Delay entre enemigos



    /**
     * Crea una oleada segun los parametros internos
     */
    public void generate(){
        Timer.Task next=new Timer.Task() {
            @Override
            public void run() {
                Enemy tank=enemyFactory.obtenerTankeBasico();
            }
        };
        Timer.schedule(next,0,delay,size);
    }

    /**
     * Para debugear, genera enemigos con la configuracion por defecto
     */
    public void setDefault(){
        entropy=2;
        delay=0.5f;
        size=15;
        ruta=new Array<Vector2>();
        ruta.add(new Vector2(0, 0));
        ruta.add(new Vector2(100, 100));
        ruta.add(new Vector2(-500, 500));
        ruta.add(new Vector2(100, -150));
        ruta.add(new Vector2(50, 600));
        ruta.add(new Vector2(-250, -150));
        enemyFactory.setRuta(ruta);
    }


    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
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
