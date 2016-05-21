package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Esta clase controla las diferentes colisiones que ocurren en el juego. Se pueden obtener los
 * objetos que colisionan en el userData de la fixture.
 * Created by Sergio on 17/04/2016.
 */
public class CollisionControl implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        //Cojemos la fixture de los implicados
        Body cuerpoA = contact.getFixtureA().getBody();
        Body cuerpoB = contact.getFixtureB().getBody();
        //Comprobamos que choca, si es un sensor o un proyectil
        //comprobamos si son enemigo y sensor de torre
        if (isInRange(cuerpoA,cuerpoB)){
            //Obtenemos la torre y le decimos que ataque.
            if  (cuerpoA.getUserData() instanceof Tower){
                //La torre ataca
                Gdx.app.log("COLLISIONCONTROL", "La torre atacara!");
                Tower torre=(Tower) cuerpoA.getUserData();
                Enemy enemigo=(Enemy) cuerpoB.getUserData();
                torre.enemyInRange(enemigo);
            }
            if  (cuerpoB.getUserData() instanceof Tower){
                //La torre ataca
                Gdx.app.log("COLLISIONCONTROL", "La torre atacara!");
                Tower torre=(Tower) cuerpoB.getUserData();
                Enemy enemigo=(Enemy) cuerpoA.getUserData();
                torre.enemyInRange(enemigo);
            }
        }
        /* //Por ahora hacemos que se calcule segun la distancia con el objetivo. Asi no se choca antes
        //Comprobamos si lo que choca es un proyectil con una unidad
        if (proyectilcolision(cuerpoA,cuerpoB)){
            if  (cuerpoA.getUserData() instanceof Proyectile){
                //La torre ataca
                Proyectile proyectil=(Proyectile) cuerpoA.getUserData();
                Enemy enemigo=(Enemy) cuerpoB.getUserData();
                enemigo.dainar(proyectil.getDaino());
                proyectil.liberar(); //liveramos el proyectil TODO particulas de explosion

            }
            if  (cuerpoB.getUserData() instanceof Proyectile){
                //La torre ataca
                Proyectile proyectil=(Proyectile) cuerpoB.getUserData();
                Enemy enemigo=(Enemy) cuerpoA.getUserData();
                enemigo.dainar(proyectil.getDaino());
                proyectil.liberar(); //liberamos el proyectil TODO particulas de explosion
            }
        }*/

    }

    @Override
    public void endContact(Contact contact) {
        Body cuerpoA = contact.getFixtureA().getBody();
        Body cuerpoB = contact.getFixtureB().getBody();
        //Salen del rango, la torre deja de atacar
        if  (cuerpoA.getUserData() instanceof Tower){
            Gdx.app.log("COLLISIONCONTROL", "La torre deja de atacar!");
            Tower torre=(Tower) cuerpoA.getUserData();
            torre.enemyOutRange((Enemy) cuerpoB.getUserData());
        }
        if  (cuerpoB.getUserData() instanceof Tower){
            Gdx.app.log("COLLISIONCONTROL", "La torre deja de atacar!");
            Tower torre=(Tower) cuerpoB.getUserData();
            torre.enemyOutRange((Enemy) cuerpoA.getUserData());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    //Devuelve si la colision es de una torre y un enemmigo
    private boolean isInRange(Body objectA,Body objectB){
       return (((objectA.getUserData() instanceof Enemy)&&(objectB.getUserData() instanceof Tower))||((objectB.getUserData() instanceof Enemy)&&(objectA.getUserData() instanceof Tower)));
    }
    private boolean proyectilcolision(Body objectA,Body objectB){
        return (((objectA.getUserData() instanceof Enemy)&&(objectB.getUserData() instanceof Proyectile))||((objectB.getUserData() instanceof Enemy)&&(objectA.getUserData() instanceof Proyectile)));
    }
}
