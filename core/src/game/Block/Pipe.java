package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

public class Pipe extends InteractiveObject{
    public Pipe(MapObject object, World world){
        super(object, world);
    }
}
