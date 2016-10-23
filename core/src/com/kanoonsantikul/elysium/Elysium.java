package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Application.ApplicationType;

public class Elysium extends Game {
	public static float DEVICE_RATIO = 1f;
	public static int WIDTH;
	public static int HEIGHT;

	public SpriteBatch batcher;

	private GameScreen gameScreen;

	@Override
	public void create () {
		//DEVICE_RATIO = getDeviceRatio();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		Assets.load();
		batcher = new SpriteBatch();
		gameScreen = new GameScreen(this);

		setScreen(gameScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		batcher.dispose();
		gameScreen.dispose();
		Assets.dispose();
	}

	// public float getDeviceRatio(){
	// 	ApplicationType type = Gdx.app.getType();
	// 	if(type == ApplicationType.Android){
	// 		return 1f;
	// 	} else if(type == ApplicationType.Desktop){
	// 		return 0.35f;
	// 	}
	// 	return 0f;
	// }

}
