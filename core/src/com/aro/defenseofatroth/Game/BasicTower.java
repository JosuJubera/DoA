package com.aro.defenseofatroth.Game;

/**
 * Esta clase representa a una torre en el juego, concretamente a una torre basica.
 * Created by Sergio on 17/04/2016.
 */
public class BasicTower extends Tower{
    protected static float ALCANCE=20f; //Si se modifica e alcance, hay que modificarlo en el sensor tambien (por si hacemos que aumente al subir de lvl)

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
