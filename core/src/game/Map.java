package game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import game.Block.Brick;
import game.Block.Coin;
import game.Block.Ground;
import game.Block.Pipe;

/**
 * Created by barti on 27.12.2017.
 */
public class Map{
    private TiledMap map;

    public Map(){
        map = null;
    }

    public OrthogonalTiledMapRenderer loadMap(String mapName, World world){
        map = new TmxMapLoader().load(mapName);
        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / Main.PPM);
        createPhysicalObject(world);
        return mapRenderer;
    }

    private void createPhysicalObject(World world){
        for(int i = 2; i < 6; i++){
            for(MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)){
                switch(i){
                    case 2:
                        new Ground(object, world);
                        break;
                    case 3:
                        new Pipe(object, world);
                        break;
                    case 4:
                        new Coin(object, world);
                        break;
                    case 5:
                        new Brick(object, world);
                        break;
                        default:
                }
            }
        }
    }
}
