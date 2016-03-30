package com.aro.defenseofatroth.Tools;

import com.aro.defenseofatroth.Levels.Level3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by elementary on 30/03/16.
 */
public class WorldContactListener implements ContactListener {

    // Colision 2 fixtures, no 2 bodies
    private boolean areCollided(Contact contact, Object userA, Object userB) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if (userDataA == null || userDataB == null) {
            return false;
        }

        return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                (userDataA.equals(userB) && userDataB.equals(userA));
    }



    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

}
