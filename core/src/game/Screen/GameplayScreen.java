package game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import game.Character.Enemy;
import game.Character.Player;
import game.Hud;
import game.Items.Item;
import game.Items.ItemDef;
import game.Items.Mushroom;
import game.Main;
import game.Map;
import game.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by barti on 06.12.2017.
 */
public class GameplayScreen extends AbstractScreen{

    private final World world;
    private final OrthogonalTiledMapRenderer render;
    private final Box2DDebugRenderer renderDebug;
    private final Player player;
    private final Map map;
    private final TextureAtlas atlas;
    private final Hud hud;
    private final Array<Item> items;
    private final LinkedBlockingQueue<ItemDef> itemsToSpawn;
    private final Game game;

    public GameplayScreen(Game game){
        this.game = game;
        world = new World(new Vector2(0, -10f), true);
        renderDebug = new Box2DDebugRenderer();
        atlas = new TextureAtlas("mario_and_enemies.atlas");
        player = new Player(world, atlas);
        map = new Map();
        render = map.loadMap("1-1test.tmx", world, atlas, this);
        hud = new Hud(batch);
        Music music = Main.manager.get("audio/mario_music.ogg", Music.class);
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

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

        for(Enemy enemy : map.getEnemies())
            enemy.draw(batch);

        for(Item item : items)
            item.draw(batch);

        batch.end();

        hud.getStage().draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    private void update(float delta){
        handleSpawningItems();

        world.step(1 / 120f, 8, 3);
        player.update(delta);

        for(Enemy enemy : map.getEnemies()) {
            enemy.update(delta);

            if(enemy.getX() < player.getX() + 240)
                enemy.body.setActive(true);
        }

        for(Item item : items)
            item.update(delta);

        if(player.currentState != Player.State.DEAD)
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

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    private void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class);{
                items.add(new Mushroom(world, atlas, idef.position.x, idef.position.y));
            }
        }
    }

    public boolean gameOver(){
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 3)
            return true;
        return false;
    }

}
