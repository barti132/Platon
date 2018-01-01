package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends InteractiveObject{
    public Ground(MapObject object, World world){
        super(object, world);
    }
}
