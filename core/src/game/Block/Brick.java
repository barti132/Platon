package game.Block;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import game.Hud;
import game.Main;
import game.Map;

public class Brick extends Object {
    public Brick(MapObject object, World world, Map map){
        super(object, world, map);
        fixture.setUserData(this);
        setCategoryFilter(Main.BRICK_BIT);
    }

    @Override
    public void onHeadHit(){
        setCategoryFilter(Main.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(100);
        Main.manager.get("audio/breakblock.wav", Sound.class).play();
    }
}
