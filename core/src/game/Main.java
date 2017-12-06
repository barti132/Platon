package game;

import com.badlogic.gdx.Game;

public class Main extends Game{

	@Override
	public void create(){
		Game game = this;
		game.setScreen(new GameplayScreen());
	}

}
