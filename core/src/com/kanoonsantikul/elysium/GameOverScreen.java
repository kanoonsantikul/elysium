package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.ScreenAdapter;

public class GameOverScreen extends ScreenAdapter
        implements InputHandler.InputListener{
    public enum WinState{
        WIN, LOSE, DRAW
    };

    private Elysium game;
    private WinState winState;
    private String text;

    public GameOverScreen(Elysium game, WinState winState){
        this.game = game;
        this.winState = winState;

        game.inputHandler.setListener(this);
        ConnectionManager.instance().disconnect();

        if(winState == WinState.WIN){
            text = "YOU WIN";
        } else if(winState == WinState.LOSE){
            text = "YOU LOSE";
        } else{
            text = "DRAW";
        }
    }

    @Override
	public void render (float delta){
        GL20 gl = Gdx.gl;
        gl.glClearColor(91/255f, 222/255f, 162/255f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batcher.begin();
        Assets.font.draw(game.batcher,
                text,
                Elysium.WIDTH/2,
                Elysium.HEIGHT/2);
        Assets.font.draw(game.batcher,
                "Click anywhere to restart",
                Elysium.WIDTH/2,
                Elysium.HEIGHT/2 - 20);
        game.batcher.end();
    }

    @Override
    public void onClicked(float x, float y){
        game.setScreen(new StartMultiplayerScreen(game));
    }

    @Override
    public void onPressed(float x, float y){

    }

    @Override
    public void onDragStart(float x, float y){

    }

    @Override
    public void onDragEnd(float x, float y){

    }

    @Override
    public void onDragged(float x, float y){

    }
}
