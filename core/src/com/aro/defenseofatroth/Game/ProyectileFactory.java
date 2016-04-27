package com.aro.defenseofatroth.Game;

/**
 * Esta clase genera los proyectiles de las diferentes torres. Posee diferentes Pools para mejorar
 * el rendimiento. Los proyectiles son generados por las torres.
 * Created by Sergio on 17/04/2016.
 */
public class ProyectileFactory implements ObjectPool<Proyectile> {
    @Override
    public void remove(Proyectile freeObject) {

    }
}
