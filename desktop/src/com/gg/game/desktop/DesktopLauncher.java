package com.gg.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gg.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true; // Setting to false disables vertical sync
		config.foregroundFPS = 60; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 60;
		config.width = 1067;
		config.height =480;
        MyGdxGame game = new MyGdxGame();
        AdapterDesktop adapter = new AdapterDesktop();

        game.setNotificationHandler(adapter);
        new LwjglApplication(game, config);
	}
}
