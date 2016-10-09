package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderer{
    World world;
    SpriteBatch batcher;

    public Renderer(World world, SpriteBatch batcher){
        this.world = world;
        this.batcher = batcher;
    }

    public void render(){
        GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();
        renderBoard();
        renderUI();
        renderCard();
        batcher.end();
    }

    private void renderBoard(){
        renderTiles();
        renderPathTracker();
        renderCharacter();
    }

    private void renderUI(){
        Texture cardBar;
        if(world.isPlayer1Turn){
            cardBar = Assets.cardBarBlue;
        } else{
            cardBar = Assets.cardBarRed;
        }

        batcher.draw(cardBar,
                world.cardBar.getPosition().x,
                world.cardBar.getPosition().y,
                CardBar.WIDTH,
                CardBar.HEIGHT);

        batcher.draw(Assets.endTurnButton,
                world.endTurnButton.getPosition().x,
                world.endTurnButton.getPosition().y,
                EndTurnButton.WIDTH,
                EndTurnButton.HEIGHT);

        renderFont();
    }

    private void renderFont(){
        String turn;
        if(world.isPlayer1Turn){
            turn = "Player 1";
        } else{
            turn = "Player 2";
        }
        Assets.font.draw(batcher,
                turn,
                world.endTurnButton.getPosition().x - 70,
                world.endTurnButton.getPosition().y + world.endTurnButton.HEIGHT + 20);
    }

    private void renderTiles(){
        Tile[] tiles = world.tiles;
        Tile tile;
        for(int i=0; i<tiles.length; i++){
            tile = tiles[i];
            batcher.draw(Assets.tile,
                    tile.getPosition().x,
                    tile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderPathTracker(){
        LinkedList<Tile> pathTracker = world.pathTracker;
        Tile alphaTile;
        for(int i=0 ;i<pathTracker.size(); i++){
            alphaTile = pathTracker.get(i);
            batcher.draw(Assets.alphaTile,
                    alphaTile.getPosition().x,
                    alphaTile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderCharacter(){
        Character player1 = world.player1;
        batcher.draw(Assets.player1,
                player1.getPosition().x,
                player1.getPosition().y,
                Character.WIDTH,
                Character.HEIGHT);
    }

    private void renderCard(){
        Card[] cards = world.cards;
        for(int i=0; i<cards.length; i++){
            batcher.setColor(1, 1, 1, cards[i].getAlpha());
            batcher.draw(Assets.card,
                    cards[i].getPosition().x,
                    cards[i].getPosition().y,
                    Card.WIDTH,
                    Card.HEIGHT);
            batcher.setColor(1,1,1,1);
        }

        if(world.fullCard != null){
            batcher.draw(Assets.card,
                    FullCard.X,
                    FullCard.Y,
                    FullCard.WIDTH,
                    FullCard.HEIGHT);
        }
    }
}
