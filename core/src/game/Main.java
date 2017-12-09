package game;

import com.badlogic.gdx.Game;
import game.Screen.GameplayScreen;

public class Main extends Game{

	@Override
	public void create(){
		Game game = this;
		game.setScreen(new GameplayScreen());
	}

}
