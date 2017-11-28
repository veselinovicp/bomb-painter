package com.monoton.horizont.bomb.painter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.monoton.horizont.bomb.painter.BombPainter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1500;
		config.height=1000;
		config.foregroundFPS = 60;
		new LwjglApplication(new BombPainter(), config);
	}
}
