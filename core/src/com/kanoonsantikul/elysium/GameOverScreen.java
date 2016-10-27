package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.ScreenAdapter;

public class GameOverScreen extends ScreenAdapter
        implements InputHandler.InputListener{
    public enum Winner{
        PLAYER1, PLAYER2, NONE
    };

    private Elysium game;
    private Winner winner;
    private String winnerString;

    public GameOverScreen(Elysium game, Winner winner){
        this.game = game;
        this.winner = winner;

        game.inputHandler.setListener(this);

        if(winner == Winner.PLAYER1){
            winnerString = "player 1";
        } else if(winner == Winner.PLAYER2){
            winnerString = "player 2";
        } else{
            winnerString = "no winner";
        }
    }

    @Override
	public void render (float delta){
        GL20 gl = Gdx.gl;
        gl.glClearColor(91/255f, 222/255f, 162/255f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batcher.begin();
        Assets.font.setColor(Color.BLACK);
        Assets.font.draw(game.batcher,
                "Winner is " + winnerString,
                Elysium.WIDTH/2,
                Elysium.HEIGHT/2);
        Assets.font.draw(game.batcher,
                "Click to restart",
                Elysium.WIDTH/2,
                Elysium.HEIGHT/2 - 20);
        Assets.font.setColor(Color.WHITE);
        game.batcher.end();
    }

    @Override
    public void onClicked(float x, float y){
        game.setScreen(new GameScreen(game));
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
