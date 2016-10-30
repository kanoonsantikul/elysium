package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter implements
        World.GameStateChangeListener{
    private Elysium game;
    private World world;
    private MultiplayerUpdater multiplayerUpdater;
    private Renderer renderer;

    public GameScreen(Elysium game, int userNumber){
        this.game = game;

        world = new World(userNumber);
        world.setListener(this);
        game.inputHandler.setListener(world);

        multiplayerUpdater = new MultiplayerUpdater(world);
        ConnectionManager.instance().setListener(multiplayerUpdater);
        world.syncPlayerData();

        renderer = new Renderer(world, game.batcher);
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
