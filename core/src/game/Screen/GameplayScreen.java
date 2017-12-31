package game.Screen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import game.Player;
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
    private TextureAtlas atlas;

    public GameplayScreen(){
        world = new World(new Vector2(0, -10f), true);
        renderDebug = new Box2DDebugRenderer();
        atlas = new TextureAtlas("mario_and_enemies.atlas");
        player = new Player(world, atlas);
        map = new Map();
        render = map.loadMap("1-1test.tmx", world);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        update(delta);

        player.move();

        render.render();
        renderDebug.render(world, camera.combined);

        batch.begin();
        player.draw(batch);
        batch.end();
    }

    private void update(float delta){
        world.step(1 / 120f, 8, 3);
        player.update(delta);
        camera.position.x = player.getBody().getPosition().x;
        camera.update();
        render.setView(camera);
        batch.setProjectionMatrix(camera.combined);
    }

}
