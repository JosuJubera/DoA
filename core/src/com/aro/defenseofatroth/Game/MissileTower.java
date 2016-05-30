package com.aro.defenseofatroth.Game;

/**
 * Created by Sergio on 24/05/2016.
 */
public class MissileTower extends Tower {
    protected static float ALCANCE=600f; //Si se modifica e alcance, hay que modificarlo en el sensor tambien (por si hacemos que aumente al subir de lvl)
    protected Enemy objetivo;
    protected int estado; //1 ociosa, 2 atacando
    protected float tiempoEntreAtaques; //Tiemmpo entre ataques
    protected float tiempoSiguienteAtaque; //Tiempo restante para el siguiente ataque

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

    private void atacar(){
        //TODO hacer. Crear proyectil y enviarlo
        super.proyectileFactory.obtenerMisil(objetivo,posicion,120);
    }

    @Override
    public void act(float delta){
        if (estado==1) { //No ataca
            if (enemigosEnRango.size>0){ //tiene enemigos al alcance
                cambiarEstado();
                objetivo=enemigosEnRango.first();
            }
        }else if (estado==2) { //Esta atacando
            tiempoSiguienteAtaque -= delta;
            if (tiempoSiguienteAtaque <= 0) { //hora de atacar!
                tiempoSiguienteAtaque = tiempoEntreAtaques;
                if (objetivo.isViva()) { //Esta viva
                    atacar();
                } else { //Nuevo objetivo
                    if (enemigosEnRango.size>0) { //hay mas enemigos en rango
                        objetivo = enemigosEnRango.first();
                    }else{ //no hay nadie, cambio
                        cambiarEstado();
                    }
                }
            }
        }
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    public void enemyOutRange(Enemy enemigo){
        super.enemyOutRange(enemigo);
        if (enemigo==objetivo){
            objetivo=null;
            cambiarEstado();
        }
    }

    private void cambiarEstado(){
        if (estado==1){
            estado=2;
        }else {
            estado=1;
            tiempoSiguienteAtaque=tiempoEntreAtaques;
            objetivo=null;
        }
    }

}
