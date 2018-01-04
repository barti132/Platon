package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import game.Main;
import game.Map;

public abstract class InteractiveObject extends Object {

    private Map map;
    protected World world;

    public InteractiveObject(MapObject object, World w, Map m){
        super(object, w);
        map = m;
        world = w;
    }

    public abstract void onHeadHit();
    protected void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    protected TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTiledMap().getLayers().get(0);
        return layer.getCell((int)(body.getPosition().x * Main.PPM / 16), (int)(body.getPosition().y * Main.PPM / 16));
    }

}
