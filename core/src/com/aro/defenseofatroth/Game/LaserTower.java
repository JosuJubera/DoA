package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.Screens.Hud;
import com.aro.defenseofatroth.Tools.Constants;

import static com.aro.defenseofatroth.Tools.Constants.*;
import static com.aro.defenseofatroth.Tools.Constants.ALCANCE_LASER;

/**
 * Created by elementary on 26/05/16.
 */
public class LaserTower extends Tower {

    protected static float ALCANCE = ALCANCE_LASER;
    private Enemy objetivo;
    protected int estado; //1 ociosa, 2 atacando
    protected float tiempoEntreAtaques; //Tiemmpo entre ataques
    protected float tiempoSiguienteAtaque; //Tiempo restante para el siguiente ataque
    protected int daño;
    int mejora = 0;

    public float getTiempoEntreAtaques() {
        return tiempoEntreAtaques;
    }

    public void setTiempoEntreAtaques(float tiempoEntreAtaques) {
        this.tiempoEntreAtaques = tiempoEntreAtaques;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public float getTiempoSiguienteAtaque() {
        return tiempoSiguienteAtaque;
    }

    public void setTiempoSiguienteAtaque(float tiempoSiguienteAtaque) {
        this.tiempoSiguienteAtaque = tiempoSiguienteAtaque;
    }

    private void atacar() {
        //TODO hacer. Crear proyectil y enviarlo
        super.proyectileFactory.obtenerMisil(objetivo,posicion, 120);
    }

    @Override
    public void act(float delta) {
        if (estado == 1) { //No ataca
            if (enemigosEnRango.size > 0){ //tiene enemigos al alcance
                cambiarEstado();
                objetivo = enemigosEnRango.first();
            }
        }else if (estado == 2) { //Esta atacando
            tiempoSiguienteAtaque -= delta;
            if (tiempoSiguienteAtaque <= 0) { //hora de atacar!
                tiempoSiguienteAtaque = tiempoEntreAtaques;
                if (objetivo.isViva()) { //Esta viva
                    atacar();
                } else { //Nuevo objetivo
                    if (enemigosEnRango.size > 0) { //hay mas enemigos en rango
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
    public void enemyOutRange(Enemy enemigo) {
        super.enemyOutRange(enemigo);
        if (enemigo == objetivo){
            objetivo = null;
            cambiarEstado();
        }
    }

    private void cambiarEstado() {

        if (estado == 1) {
            estado = 2;
        }else {
            estado = 1;
            tiempoSiguienteAtaque = tiempoEntreAtaques;
            objetivo = null;
        }
    }

    public boolean mejorar() {

        if (Hud.getMoney() >= costeMejora) {
            Hud.addGold(-costeMejora);
            if (mejora > 4) {
                mejora = 4;
            }
            this.tiempoEntreAtaques = tiempoEntreAtaques * velAtaque[mejora];
            this.daño = daño + dañoAtaque[mejora];
            mejora++;
            Message.getInstance().say("Torre mejorada");
            return true;
        }else {
            Message.getInstance().say("No hay suficiente oro");
            return false;
        }
    }
}
