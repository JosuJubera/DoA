package com.aro.defenseofatroth.Game;

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
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //Comprobamos que choca, si es un sensor o un proyectil
        //comprobamos si son enemigo y sensor de torre
        if (isInRange(fixtureA,fixtureB)){
            //Obtenemos la torre y le decimos que ataque.
            if  (fixtureA.getUserData() instanceof Tower){
                //La torre ataca
                Tower torre=(Tower) fixtureA.getUserData();
                Enemy enemigo=(Enemy) fixtureB.getUserData();
                torre.establecerObjetivo(enemigo);
            }
            if  (fixtureB.getUserData() instanceof Tower){
                //La torre ataca
                Tower torre=(Tower) fixtureB.getUserData();
                Enemy enemigo=(Enemy) fixtureA.getUserData();
                torre.establecerObjetivo(enemigo);
            }
        }
        //Comprobamos si lo que choca es un proyectil con una unidad


    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    //Devuelve si la colision es de una torre y un enemmigo
    private boolean isInRange(Fixture objectA,Fixture objectB){
       return (((objectA.getUserData() instanceof Enemy)&&(objectB.getUserData() instanceof Tower))||((objectB.getUserData() instanceof Enemy)&&(objectA.getUserData() instanceof Tower)));
    }
}
