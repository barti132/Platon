package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends InteractiveObject{
    public Brick(MapObject object, World world){
        super(object, world);
    }
}
