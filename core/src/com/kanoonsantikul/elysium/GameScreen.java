package com.kanoonsantikul.elysium;

import com.kanoonsantikul.elysium.InputHandler.GestureHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;

public class GameScreen extends ScreenAdapter {
    Elysium game;
    World world;
    Renderer renderer;
    InputMultiplexer inputs;

    public GameScreen(Elysium game){
        this.game = game;

        world = new World();
        renderer = new Renderer(world, game.batcher);

        setupInput();
    }

    @Override
	public void render (float delta) {
        world.update();
		renderer.render();
	}

    private void setupInput(){
        inputs = new InputMultiplexer();
        InputHandler inputHandler =
                new InputHandler(new GestureDetector.GestureAdapter());
        inputHandler.setListener(world);
        GestureDetector gestureHandler =
                new GestureDetector(inputHandler.new GestureHandler());
        gestureHandler.setLongPressSeconds(InputHandler.LONG_PRESS_SECONDS);

        inputs.addProcessor(inputHandler);
        inputs.addProcessor(gestureHandler);
        Gdx.input.setInputProcessor(inputs);
    }
}
