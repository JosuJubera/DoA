package com.aro.defenseofatroth;

import com.aro.defenseofatroth.Levels.Level1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

/**
 * Esta clase representa un proyectil de una torre. Si hay clases hijas, habra que reimplimentar el Pool
 * Created by Sergio on 09/04/2016.
 */
public class Proyectil extends Entidad implements Pool.Poolable {
    private Unidad objetivo;
    private int danio;

    //Esto deberia de hacerlo las clases inferiors, pero de prueba lo dejo aki
    public Proyectil(){
        super();
        texture = new Sprite(new Texture(Gdx.files.internal("barraRoja.png")));
        texture.scale(0.3f);
        velocidadM=200f;
    }
    @Override
    public void draw(Batch batch, float delta) {
        actuar(delta);
        texture.setPosition(posicion.x,posicion.y);//Esto es un setButtonUpdate
        texture.draw(batch);
    }

    @Override
    public void dispose() {

    }

    public void setObjetivo(Unidad objetivo){
        this.objetivo=objetivo;
    }
    public void setDanio(int danio){
        this.danio=danio;
    }
    public void actuar(float delta){
        setDestino(objetivo.posicion);//en cada frame hay que actualizar el destino, ya que se mueve!
        posicion.x+=velocidad.x*delta;
        posicion.y+=velocidad.y*delta;

        if ((posicion.x==objetivo.getPosicion().x) && (posicion.y==objetivo.getPosicion().y)){
            objetivo.danar(danio);
            Level1.entidades.removeValue(this, true);//Quitamos el proyectil (igual a this.remove() si es un actor)
            Level1.proyectiles.free(this); //Lo marcamos como obtenible
        }
    }

    @Override
    public void reset() {
        this.danio=0;
        this.posicion=null;
        this.objetivo=null;
    }
}
