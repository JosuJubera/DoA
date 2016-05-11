package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
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
        Gdx.app.log("COLLISIONCONTROL", "Se ha establecido un contacto");
        //Comprobamos que choca, si es un sensor o un proyectil
        //comprobamos si son enemigo y sensor de torre
        if (isInRange(cuerpoA,cuerpoB)){
            //Obtenemos la torre y le decimos que ataque.
            if  (cuerpoA.getUserData() instanceof Tower){
                //La torre ataca
                Gdx.app.log("COLLISIONCONTROL", "La torre atacara!");
                Tower torre=(Tower) cuerpoA.getUserData();
                Enemy enemigo=(Enemy) cuerpoB.getUserData();
                torre.establecerObjetivo(enemigo);
            }
            if  (cuerpoB.getUserData() instanceof Tower){
                //La torre ataca
                Gdx.app.log("COLLISIONCONTROL", "La torre atacara!");
                Tower torre=(Tower) cuerpoB.getUserData();
                Enemy enemigo=(Enemy) cuerpoA.getUserData();
                torre.establecerObjetivo(enemigo);
            }
        }
        //Comprobamos si lo que choca es un proyectil con una unidad
        if (proyectilcolision(cuerpoA,cuerpoB)){
            if  (cuerpoA.getUserData() instanceof Proyectile){
                //La torre ataca
                Proyectile proyectil=(Proyectile) cuerpoA.getUserData();
                Enemy enemigo=(Enemy) cuerpoB.getUserData();
                enemigo.daniar(proyectil.getDanio());
                proyectil.liberar(); //liveramos el proyectil TODO particulas de explosion

            }
            if  (cuerpoB.getUserData() instanceof Proyectile){
                //La torre ataca
                Proyectile proyectil=(Proyectile) cuerpoB.getUserData();
                Enemy enemigo=(Enemy) cuerpoA.getUserData();
                enemigo.daniar(proyectil.getDanio());
                proyectil.liberar(); //liberamos el proyectil TODO particulas de explosion
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Gdx.app.log("COLLISIONCONTROL", "Se ha perdido un contacto");
        //Salen del rango, la torre deja de atacar
        if  (fixtureA.getUserData() instanceof Tower){
            Tower torre=(Tower) fixtureA.getUserData();
            torre.libre();
        }
        if  (fixtureB.getUserData() instanceof Tower){
            Tower torre=(Tower) fixtureB.getUserData();
            torre.libre();
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
