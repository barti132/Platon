package game;

import com.badlogic.gdx.Game;
import game.Screen.GameplayScreen;

public class Main extends Game{

	public static final float PPM = 100f;

	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;

	@Override
	public void create(){
		Game game = this;
		game.setScreen(new GameplayScreen());
	}

}
