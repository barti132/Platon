package game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

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
        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / 6f);
        createPhysicalObject("object", world);
        createPhysicalObject("ground", world);
        return mapRenderer;
    }

    private void createPhysicalObject(String layerName, World world){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);


        for(int x = 0; x < layer.getWidth(); x++){
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);

                if(cell == null || cell.getTile() == null)
                    continue;

                BodyDef bdef = new BodyDef();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((x + .5f) * 16 / (100/16), (y + .5f) * 16 / (100/16));

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(16 / (100/16) / 1.5f, 16 / (100/16) / 1.5f);

                FixtureDef fdef = new FixtureDef();
                fdef.friction = 2f;
                fdef.shape = shape;


                world.createBody(bdef).createFixture(fdef);
            }
        }
    }
}
