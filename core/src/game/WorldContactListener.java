package game;

import com.badlogic.gdx.physics.box2d.*;
import game.Block.Object;
import game.Character.Enemy;
import game.Character.Player;
import game.Items.Item;

public class WorldContactListener implements ContactListener{



    @Override
    public void beginContact(Contact contact){
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Main.BRICK_BIT | Main.PLAYER__HEAD_BIT:
            case Main.COIN_BIT | Main.PLAYER__HEAD_BIT:
                if(fixA.getFilterData().categoryBits == Main.PLAYER__HEAD_BIT)
                    ((Object)(fixB.getUserData())).onHeadHit((Player) fixA.getUserData());
                else
                    ((Object)(fixA.getUserData())).onHeadHit((Player) fixB.getUserData());
                break;

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

            case Main.ITEM_BIT | Main.OBJECT_BIT :
                if(fixA.getFilterData().categoryBits == Main.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);

                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case Main.ITEM_BIT | Main.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == Main.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Player) fixB.getUserData());

                else
                    ((Item)fixB.getUserData()).use((Player) fixA.getUserData());
                break;

            case Main.MARIO_BIT | Main.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Main.MARIO_BIT)
                    ((Player) fixA.getUserData()).hit();
                else
                    ((Player) fixB.getUserData()).hit();
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
