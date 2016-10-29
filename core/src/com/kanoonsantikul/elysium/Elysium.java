package com.kanoonsantikul.elysium;

import com.kanoonsantikul.elysium.InputHandler.GestureHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;

public class Elysium extends Game {
	public static float DEVICE_RATIO = 1f;
	public static int WIDTH;
	public static int HEIGHT;

	public SpriteBatch batcher;
	public InputHandler inputHandler;

	@Override
	public void create () {
		//DEVICE_RATIO = getDeviceRatio();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		Assets.load();
		batcher = new SpriteBatch();
		setupInput();

		setScreen(new StartMultiplayerScreen(this));
		//setScreen(new GameScreen(this));
		//setScreen(new GameOverScreen(this, GameOverScreen.Winner.NONE));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		batcher.dispose();
		//gameScreen.dispose();
		Assets.dispose();
	}

    private void setupInput(){
        InputMultiplexer inputs = new InputMultiplexer();
        inputHandler =
                new InputHandler(new GestureDetector.GestureAdapter());
        GestureDetector gestureHandler =
                new GestureDetector(inputHandler.new GestureHandler());
        gestureHandler.setLongPressSeconds(InputHandler.LONG_PRESS_SECONDS);

        inputs.addProcessor(inputHandler);
        inputs.addProcessor(gestureHandler);
        Gdx.input.setInputProcessor(inputs);
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
