package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.Screens.Hud;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Esta clase representa a una torre en el juego, concretamente a una torre basica.
 * Created by Sergio on 17/04/2016.
 */
public class BasicTower extends Tower{
    protected static float ALCANCE=400f; //Si se modifica e alcance, hay que modificarlo en el sensor tambien (por si hacemos que aumente al subir de lvl)
    private BitmapFont font;
    protected Enemy objetivo;
    protected int estado; //1 ociosa, 2 atacando
    protected float tiempoEntreAtaques; //Tiemmpo entre ataques
    protected float tiempoSiguienteAtaque; //Tiempo restante para el siguiente ataque
    protected int daño;

    public void setFont(BitmapFont font) {
        this.font = font;
    }

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

    private void atacar(){
        //TODO hacer. Crear proyectil y enviarlo
        super.proyectileFactory.obtenerProyectilTorreBasica(objetivo,posicion,daño);
    }

    public void draw(Batch bach,float delta){
        super.draw(bach, delta);
        font.setColor(Color.WHITE);
        //font.draw(bach, "Estado: " + estado + "\n Tiempo sigueinte Ataque " + tiempoSiguienteAtaque, posicion.x - textura.getRegionWidth() * 0.5f, posicion.y - textura.getRegionWidth() * 0.5f - 10);//Debug, borrrar
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

    public boolean mejorar(){
        if (Hud.getMoney()>=costeMejora){
            Hud.addGold(-costeMejora);
            this.tiempoEntreAtaques=tiempoEntreAtaques*0.7f;
            this.daño=daño+5;
            Message.getInstance().say("Torre mejorada");
            return true;
        }else {
            Message.getInstance().say("No hay suficiente oro");
            return false;
        }
    }
}
