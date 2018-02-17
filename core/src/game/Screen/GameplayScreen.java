package game.Screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import game.*;
import game.Character.Goomba;
import game.Character.Player;

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
    private Hud hud;
    private Music music;
    private Goomba goomba;

    public GameplayScreen(){
        world = new World(new Vector2(0, -10f), true);
        renderDebug = new Box2DDebugRenderer();
        atlas = new TextureAtlas("mario_and_enemies.atlas");
        player = new Player(world, atlas);
        map = new Map();
        render = map.loadMap("1-1test.tmx", world);
        hud = new Hud(batch);
        music = Main.manager.get("audio/mario_music.ogg", Music.class);
        goomba = new Goomba(world, atlas, .32f, .32f);

        music.setLooping(true);
        music.play();
        world.setContactListener(new WorldContactListener());
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
        goomba.draw(batch);
        batch.end();

        hud.getStage().draw();
    }

    private void update(float delta){
        world.step(1 / 120f, 8, 3);
        player.update(delta);
        goomba.update(delta);
        camera.position.x = player.getBody().getPosition().x;
        camera.update();
        render.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        hud.update(delta);
        Main.manager.update();
    }

    @Override
    public void dispose(){
        batch.dispose();
        hud.dispose();
    }

}
