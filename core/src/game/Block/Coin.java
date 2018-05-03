package game.Block;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import game.Hud;
import game.Items.ItemDef;
import game.Items.Mushroom;
import game.Main;
import game.Map;
import game.Screen.GameplayScreen;

public class Coin extends Object {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 27;

    private GameplayScreen screen;

    public Coin(MapObject object, World world, Map map, GameplayScreen screen){
        super(object, world, map);
        this.screen = screen;
        tileSet = map.getTiledMap().getTileSets().getTileSet("tilesheet");
        fixture.setUserData(this);
        setCategoryFilter(Main.COIN_BIT);

    }

    @Override
    public void onHeadHit(){
        if(getCell().getTile().getId() == BLANK_COIN)
            Main.manager.get("audio/bump.wav", Sound.class).play();

        else{
            if(object.getProperties().containsKey("mushroom")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Main.PPM), Mushroom.class));
                Main.manager.get("audio/powerup_spawn.wav", Sound.class).play();
            }
            else
                Main.manager.get("audio/coin.wav", Sound.class).play();

            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(200);
        }

    }

}
