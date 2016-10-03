package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Elysium extends Game {
	// used by all screens
	public SpriteBatch batcher;

	@Override
	public void create () {
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
