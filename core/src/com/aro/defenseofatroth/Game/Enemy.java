package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

/**
 * Esta clase representa a un enemigo en el juego. Las clases hijas deben especificar que clase de
 * enemigo es y sus atributos y habilidades. Los enemigos son generados por el generadordeEnemigos
 * Created by Sergio on 17/04/2016.
 */
public class Enemy extends Actor implements Pool.Poolable {
    private ObjectPool<Enemy> pool;
    @Override
    public void reset() {

    }
}
