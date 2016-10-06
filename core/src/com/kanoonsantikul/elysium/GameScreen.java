package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;

public class GameScreen extends ScreenAdapter {
    Elysium game;
    World world;
    Renderer renderer;

    public GameScreen(Elysium game){
        this.game = game;

        world = new World();
        renderer = new Renderer(world, game.batcher);

        InputHandler inputHandler = new InputHandler(new GestureAdapter());
        inputHandler.setListener(world);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
	public void render (float delta) {
        world.act();
		renderer.render();
	}
}
