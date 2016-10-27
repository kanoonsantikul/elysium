package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter implements
        World.GameStateChangeListener{
    private static final String USERNAME = "THEHUNTER";

    private Elysium game;
    private World world;
    private Renderer renderer;

    public GameScreen(Elysium game){
        this.game = game;

        world = new World();
        world.setListener(this);
        game.inputHandler.setListener(world);
        renderer = new Renderer(world, game.batcher);
        ConnectionManager.instance().connect(USERNAME);
    }

    @Override
	public void render (float delta) {
        world.update();
		renderer.render(delta);
    }

    @Override
    public void onGameOver(GameOverScreen.Winner winner){
        game.setScreen(new GameOverScreen(game, winner));
    }
}
