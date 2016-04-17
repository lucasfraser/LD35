package au.com.ionprogramming.ld35;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Sam on 18/04/2016.
 */
public class DropBall implements ContactListener{
    @Override
    public void beginContact(Contact contact){
        if(contact.getFixtureA().getFilterData().categoryBits == 0x0002){
            if(contact.getFixtureB().getFilterData().categoryBits == 0x0001){
                Game.lose();
            }
        }
        else if(contact.getFixtureB().getFilterData().categoryBits == 0x0002){
            if(contact.getFixtureA().getFilterData().categoryBits == 0x0001){
                Game.lose();
            }
        }
    }

    @Override
    public void endContact(Contact contact){

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold){

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse){

    }
}
