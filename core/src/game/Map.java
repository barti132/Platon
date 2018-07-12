package game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.Block.Brick;
import game.Block.Coin;
import game.Block.Ground;
import game.Block.Pipe;
import game.Character.Goomba;
import game.Screen.GameplayScreen;

/**
 * Created by barti on 27.12.2017.
 */
public class Map{
    private TiledMap map;
    private Array<Goomba> goombas;

    private GameplayScreen screen;

    public OrthogonalTiledMapRenderer loadMap(String mapName, World world, TextureAtlas atlas, GameplayScreen screen){
        map = new TmxMapLoader().load(mapName);
        goombas = new Array<Goomba>();
        this.screen = screen;
        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / Main.PPM);
        createPhysicalObject(world, atlas);
        return mapRenderer;
    }

    private void createPhysicalObject(World world, TextureAtlas atlas){
        for(int i = 2; i < 7; i++){
            for(MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)){
                switch(i){
                    case 2:
                        new Ground(object, world, this);
                        break;
                    case 3:
                        new Pipe(object, world, this);
                        break;
                    case 4:
                        new Coin(object, world, this, screen);
                        break;
                    case 5:
                        new Brick(object, world, this);
                        break;
                        default:
                    case 6:
                        goombas.add(new Goomba(world, atlas, object));
                        break;
                }
            }
        }
    }

    public TiledMap getTiledMap(){
        return map;
    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }
}
