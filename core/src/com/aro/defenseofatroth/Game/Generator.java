package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.math.MathUtils;
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
            int rand= MathUtils.random(0,3);
            switch (rand) {
                case 0: enemyFactory.obtenerTankeBasico();
                    break;
                case 1: enemyFactory.obtenerTankeMotor();
                    break;
                case 2: enemyFactory.obtenerTankePesado();
                    break;
                default: enemyFactory.obtenerTankeBasico();
            }
            creados++;
        }
    }

    public void reset(){
        creados=0;
        setSize(size+3);
    }

    public int getCreados() {
        return creados;
    }
    /**
     * Para debugear, genera enemigos con la configuracion por defecto
     */
    public void setDefaultLevel2(){
        creados=0;
        entropy=2;
        size=1;
        frecuency=1.5f;
        tiempo=1.5f;
        ruta=new Array<Vector2>();
        ruta.add(new Vector2(1250,2300));
        ruta.add(new Vector2(825, 1225));
        ruta.add(new Vector2(690, 300));
        ruta.add(new Vector2(1160, 200));
        ruta.add(new Vector2(1750, 425));
        ruta.add(new Vector2(1800, 1000));
        ruta.add(new Vector2(2000, 1600));
        ruta.add(new Vector2(2500, 1825));
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
