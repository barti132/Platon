package game.Block;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.physics.box2d.World;
import game.Hud;
import game.Main;
import game.Map;

public class Coin extends InteractiveObject{

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 27;

    public Coin(MapObject object, World world, Map map){
        super(object, world, map);
        tileSet = map.getTiledMap().getTileSets().getTileSet("tilesheet");
        fixture.setUserData(this);
        setCategoryFilter(Main.COIN_BIT);
    }

    @Override
    public void onHeadHit(){
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(200);
    }

}
