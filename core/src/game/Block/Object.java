package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import game.Character.Player;
import game.Main;
import game.Map;

public abstract class Object {

    private final Map map;
    final MapObject object;
    final Body body;
    final Fixture fixture;

    Object(MapObject object, World world, Map m){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        this.object = object;

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2) / Main.PPM);
        shape.setAsBox((rect.getWidth() / 2) / Main.PPM, (rect.getHeight() / 2) / Main.PPM);
        fdef.friction = .25f;
        fdef.shape = shape;

        body = world.createBody(bdef);
        fixture = body.createFixture(fdef);

        map = m;
    }

    public abstract void onHeadHit();
    public abstract void onHeadHit(Player player);

    void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTiledMap().getLayers().get(0);
        return layer.getCell((int)(body.getPosition().x * Main.PPM / 16), (int)(body.getPosition().y * Main.PPM / 16));
    }

}
