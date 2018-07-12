package game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.Main;

class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Platon";
		config.height = 720;
		config.width = 1280;
		config.vSyncEnabled = false;
		config.foregroundFPS = 120;
		config.backgroundFPS = 120;
		new LwjglApplication(new Main(), config);
	}
}
