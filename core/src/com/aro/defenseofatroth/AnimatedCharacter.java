package com.aro.defenseofatroth;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by Sergio on 08/03/2016.
 * Esta clase controla las animaciones de una entidad. Es posible añadir anmaciones, reproducirlas,
 * pararlas etc...
 */
public class AnimatedCharacter {

    private ArrayMap<String, Animation> animations; //Imagenes de la animacion
    private float time; //Tiempo de ejecucion de la animacion
    private Animation currentAnimation; //Gestor  de libgdx de la animacion
    /**
     * Añade el recurso de la animacion (es decir, el archivo XML).
     *
     * @param file XML de iniciacion
     */
    public AnimatedCharacter(FileHandle file){


    }

    /**
     * Actualiza la animacion. Llamar en el renderizado
     * @param deltaTime Tiempo que pasa desde la ultima actualizacion
     */
    public void update(float deltaTime){

    }

    /**
     * Devuelve el frame actual de la animacion
     * @return Frame
     */
    public AtlasRegion getCurrentFrame(){
        return null;
    }

    /**
     * Obtiene el nombre de a animacion que se reproduce
     * @return nombre de la animacion
     */
    public String getAnimation(){
        return null;
    }

    /**
     * Nombre de la animacion a reproducir
     * @param name
     */
    public void setAnimation(String name){

    }

    /**
     * Escala de la animacion
     * @param scale escala en float
     */
    public void setPlaybackScale(float scale){

    }

    /**
     * Para la animacion. Si se llama a update despues, se empezara desde el principio
     */
    public void stop(){

    }

    /**
     * Pausa la animacion. Si despues se llama a update, esta no se movera y sequedara en el
     * ultimo frame
     */
    public void pause(){

    }

    /**
     * Inicia/ Reauna una animacion
     */
    public void play(){

    }
}
