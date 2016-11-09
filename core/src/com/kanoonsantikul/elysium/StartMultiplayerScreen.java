package com.kanoonsantikul.elysium;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;;
import com.badlogic.gdx.ScreenAdapter;

public class StartMultiplayerScreen extends ScreenAdapter implements
        ConnectionManager.ConnectionStateListener {

    private static String username;

    private Elysium game;
    private String text;

    public StartMultiplayerScreen (Elysium game) {
        this.game = game;

        Random random = new Random();
        username = random.nextGaussian() + "";

        ConnectionManager.instance().setListener(this);
        ConnectionManager.instance().connect(username);
        text = "Connecting to Server";
    }

    @Override
    public void render (float delta) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(91/255f, 222/255f, 162/255f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batcher.begin();
        Renderer.textLayout.setText(Assets.font, text);
        Assets.font.draw(game.batcher,
                text,
                Elysium.WIDTH/2 - Renderer.textLayout.width / 2,
                Elysium.HEIGHT/2 + Renderer.textLayout.height / 2);
        game.batcher.end();
    }

    @Override
    public void onGameStarted (int userNumber) {
        game.setScreen(new GameScreen(game, userNumber));
    }

    @Override
    public void onWaitingStarted () {
        text = "Waiting for other player";
    }

    @Override
    public void onGameUpdateReceived(String message) {

    }
}
