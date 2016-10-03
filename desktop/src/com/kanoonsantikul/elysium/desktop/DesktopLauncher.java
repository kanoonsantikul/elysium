package com.kanoonsantikul.elysium.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kanoonsantikul.elysium.Elysium;

public class DesktopLauncher {
	private static final int DESKTOP_WIDTH = 448;  ////640*0.7
	private static final int DESKTOP_HEIGHT = 672; ////960*0.7

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = DESKTOP_WIDTH;
		config.height = DESKTOP_HEIGHT;
		config.resizable = false;
		new LwjglApplication(new Elysium(), config);
	}
}
