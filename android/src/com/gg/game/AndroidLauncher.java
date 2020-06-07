package com.gg.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

//стартовый класс для андроида
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		AdapterAndroid adapterAndroid = new AdapterAndroid(this);
		MyGdxGame game = new MyGdxGame();

		game.setNotificationHandler(adapterAndroid);

		initialize(game, config);
	}
}
