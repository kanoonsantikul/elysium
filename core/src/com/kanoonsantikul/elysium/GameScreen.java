package com.kanoonsantikul.elysium;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter {
    Elysium game;

    World world;
    Renderer renderer;

    public GameScreen(Elysium game){
        this.game = game;

        world = new World();
        renderer = new Renderer(world, game.batcher);
    }

    @Override
	public void render (float delta) {
		renderer.render();
	}
}
