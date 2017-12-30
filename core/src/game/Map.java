package game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

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
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(int i = 2; i < 6; i++){
            for(MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2) / Main.PPM);
                shape.setAsBox((rect.getWidth() / 2) / Main.PPM, (rect.getHeight() / 2) / Main.PPM);
                fdef.shape = shape;

                body = world.createBody(bdef);
                body.createFixture(fdef);
            }
        }
    }
}
