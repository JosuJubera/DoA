package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.utils.Disposable;

/**
 * Esta clase se encarga de cargar las texturas en memoria, de tal forma que no se cargen varias
 * veces la misma textura en la GPU por diferentes unidades. Tambien es la encargada de cargar lo
 * necesario en memoria antes de que empiece el nivel.
 * Created by Sergio on 17/04/2016.
 */
public class TextureLoader implements Disposable{
    @Override
    public void dispose() {

    }
}
