package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import game.Main;
import game.Map;

public class Coin extends InteractiveObject{

    public Coin(MapObject object, World world, Map map){
        super(object, world, map);
        fixture.setUserData(this);
        setCategoryFilter(Main.COIN_BIT);
    }

    @Override
    public void onHeadHit(){
    }

}
