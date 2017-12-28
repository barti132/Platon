package game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import game.Block.Player;
import game.Map;

/**
 * Created by barti on 06.12.2017.
 */
public class GameplayScreen extends AbstractScreen{

    private World world;
    private OrthogonalTiledMapRenderer render;
    private Box2DDebugRenderer renderDebug;
    private Player player;
    private Map map;

    public GameplayScreen(){
        world = new World(new Vector2(0, -10f), true);
        renderDebug = new Box2DDebugRenderer();
        player = new Player(world);
        map = new Map();
        render = map.loadMap("1-1test.tmx", world);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        update(delta);

        player.move();
        contact();

        world.step(1 / 60f, 8, 3);
        render.render();
        renderDebug.render(world, camera.combined);

        batch.begin();
        player.draw(batch);
        batch.end();
    }

    private void update(float delta){
        camera.position.x = player.getBody().getPosition().x;
        camera.update();
        render.setView(camera);
        batch.setProjectionMatrix(camera.combined);
    }

    private void addContactListener(){
        world.setContactListener(new ContactListener(){
            @Override
            public void beginContact(Contact contact){
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
            }

            @Override
            public void endContact(Contact contact){
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold){

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse){

            }
        });
    }

    private void contact(){
        int numContacts = world.getContactCount();
        if(numContacts > 0){
            for(Contact contact : world.getContactList()){
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(player.getPlayerFoot().toString().equals(fixtureA.toString()) || player.getPlayerFoot().toString().equals(fixtureB.toString()))
                    player.setContact(true);
            }
        }

        else if(player.isContact())
            player.setContact(false);
    }

}
