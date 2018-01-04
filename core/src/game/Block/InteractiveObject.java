package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveObject extends Object {

    public InteractiveObject(MapObject object, World world){
        super(object, world);
    }

    public abstract void onHeadHit();

}
