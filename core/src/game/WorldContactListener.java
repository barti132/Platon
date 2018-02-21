package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import game.Block.Object;
import game.Character.Enemy;

public class WorldContactListener implements ContactListener{



    @Override
    public void beginContact(Contact contact){
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() instanceof Object){
                ((Object) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef){
            case Main.ENEMY_HEAD_BIT | Main.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();

                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;

            case Main.ENEMY_BIT | Main.OBJECT_BIT :
                if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);

                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case Main.ENEMY_BIT | Main.BRICK_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);

                else
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case Main.ENEMY_BIT | Main.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case Main.MARIO_BIT | Main.ENEMY_BIT:
                    System.out.println("MARIO: DIED");
                    break;
        }

    }

    @Override
    public void endContact(Contact contact){

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold){

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse){

    }
}
