package game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by barti on 27.12.2017.
 */
public class Map{
    private TiledMap map;

    public Map(){
        map = null;
    }

    public OrthogonalTiledMapRenderer loadMap(String mapName){
        map = new TmxMapLoader().load(mapName);
        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(map, 3f);

        //TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get();

        return mapRenderer;
    }
}
