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
        renderTrap();
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
        LinkedList<Tile> tiles = world.tiles;
        Tile tile;
        for(int i=0; i<tiles.size(); i++){
            tile = tiles.get(i);
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

    private void renderTrap(){
        LinkedList<Trap> traps;
        if(world.isPlayer1Turn){
            traps = world.player1Traps;
        } else{
            traps = world.player2Traps;
        }

        Trap trap;
        for(int i=0; i<traps.size() ;i++){
            trap = traps.get(i);
            batcher.draw(Assets.traps[trap.getId()],
                    trap.getPosition().x,
                    trap.getPosition().y,
                    Trap.WIDTH,
                    Trap.HEIGHT);
        }
    }

    private void renderCharacter(){
        Character player;
        player = world.player1;
        batcher.draw(Assets.player1,
                player.getPosition().x,
                player.getPosition().y,
                Character.WIDTH,
                Character.HEIGHT);


        player = world.player2;
        batcher.draw(Assets.player2,
                player.getPosition().x,
                player.getPosition().y,
                Character.WIDTH,
                Character.HEIGHT);
    }

    private void renderCard(){
        LinkedList<Card> cards;
        if(world.isPlayer1Turn){
            cards = world.player1Cards;
        } else{
            cards = world.player2Cards;
        }

        Card card;
        for(int i=0; i<cards.size(); i++){
            card = cards.get(i);
            if(!card.isVisible()){
                Trap trap = world.trapInstance;
                batcher.setColor(1, 1, 1, World.ALPHA);
                batcher.draw(Assets.traps[trap.getId()],
                        trap.getPosition().x,
                        trap.getPosition().y,
                        Trap.WIDTH,
                        Trap.HEIGHT );
                batcher.setColor(1,1,1,1);
                continue;
            }
            batcher.draw(Assets.cards[card.getId()],
                    card.getPosition().x,
                    card.getPosition().y,
                    Card.WIDTH,
                    Card.HEIGHT);
        }

        if(world.fullCard != null){
            card = world.fullCard.getCard();
            batcher.draw(Assets.cards[card.getId()],
                    FullCard.X,
                    FullCard.Y,
                    FullCard.WIDTH,
                    FullCard.HEIGHT);
        }
    }
}
