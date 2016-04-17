package com.aro.defenseofatroth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Esta clase representa una barra de Vida.
 * Created by Sergio on 29/03/2016.
 */
public class BarraVida extends Entidad {
    //Optimizacion: Que sea un singleton y de una misma barra segun se pida, para solo cargar 1 vez con la imagen
    private NinePatch barraVidaFondo;
    private NinePatch barraVidaDelante;
    private static float ANCHOBARRA=100; //Tamaño de la barra (longitud mundo)
    private static int PIXELESANCHO=5; //Ancho de la zona no modificada de la barra en PIXELES
    private float valorActual;
    private static float ALTO=10;

    BarraVida(){
        TextureRegion broja;
        TextureRegion bverde;
        this.valorActual=100;
        broja=new TextureRegion(new Texture(Gdx.files.internal("barraRojaBuena.png")));
        bverde=new TextureRegion(new Texture(Gdx.files.internal("barraVerdeBuena.png")));
        barraVidaFondo=new NinePatch(broja,PIXELESANCHO,PIXELESANCHO,0,0);
        barraVidaDelante=new NinePatch(bverde,PIXELESANCHO,PIXELESANCHO,0,0);
    }
    public void setPosition(Vector2 posicion){
        this.posicion=posicion;
    }

    @Override
    public void draw(Batch batch, float delta) {
        barraVidaFondo.draw(batch,posicion.x,posicion.y,ANCHOBARRA,ALTO);
        barraVidaDelante.draw(batch,posicion.x,posicion.y,valorActual,ALTO);
    }



    /**
     * Tamaño de la barra en tanto porciento (%)
     * @param valorActual % de la vida
     */
    public void setValor(float valorActual) {
        this.valorActual= MathUtils.clamp((valorActual*ANCHOBARRA)*0.01f, PIXELESANCHO*2, ANCHOBARRA);
    }

    @Override
    public void dispose() {

    }
}
