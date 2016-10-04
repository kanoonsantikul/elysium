package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Elysium extends Game {
	public static int WIDTH;
	public static int HEIGHT;

	public SpriteBatch batcher;

	@Override
	public void create () {
			WIDTH = Gdx.graphics.getWidth();
			HEIGHT = Gdx.graphics.getHeight();

		batcher = new SpriteBatch();

		Assets.load();
		//Settings.load();
		//Assets.load();
		//setScreen(new MainMenuScreen(this));
		setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		batcher.dispose();
	}
}
