package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import game.Main;
import game.Map;

public class Pipe extends Object {
    public Pipe(MapObject object, World world, Map m){
        super(object, world, m);
        setCategoryFilter(Main.OBJECT_BIT);
    }

    @Override
    public void onHeadHit() {

    }
}
