package game;

import com.badlogic.gdx.Game;
import game.Screen.GameplayScreen;

public class Main extends Game{

	public static final float PPM = 100;

	@Override
	public void create(){
		Game game = this;
		game.setScreen(new GameplayScreen());
	}

}
