package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import game.Screen.GameplayScreen;

public class Main extends Game{

	public static final float PPM = 100f;

	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short PLAYER__HEAD_BIT = 512;

	public static AssetManager manager;

	@Override
	public void create(){
		Game game = this;
		manager = new AssetManager();
		manager.load("audio/mario_music.ogg", Music.class);
		manager.load("audio/coin.wav", Sound.class);
		manager.load("audio/bump.wav", Sound.class);
		manager.load("audio/breakblock.wav", Sound.class);
		manager.load("audio/powerup_spawn.wav", Sound.class);
		manager.load("audio/powerup.wav", Sound.class);
		manager.finishLoading();

		game.setScreen(new GameplayScreen());
	}

}
