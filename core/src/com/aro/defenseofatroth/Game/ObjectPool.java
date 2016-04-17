package com.aro.defenseofatroth.Game;

/**
 * Esta interfaz la implementan las factorias para que un objeto creado pueda liberarse por si mismo.
 * Created by Sergio on 17/04/2016.
 */
public interface ObjectPool<X> {
    void remove(X freeObject);
}
