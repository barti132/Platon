package game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
        world = new World(new Vector2(0, -9.81f), true);
        renderDebug = new Box2DDebugRenderer();
        player = new Player(world);
        map = new Map();
        render = map.loadMap("1-1test.tmx", world);
    }

    @Override
    public void render(float delta){
        super.render(delta);

        keyboard();
        camera.update();

        world.step(1 / 60f, 8, 3);
        render.setView(camera);
        render.render();
        renderDebug.render(world, camera.combined);
    }

    private void keyboard(){
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.translate(2, 0, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.translate(-2, 0, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.translate(0, 1, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.translate(0, -1, 0);
    }
}
