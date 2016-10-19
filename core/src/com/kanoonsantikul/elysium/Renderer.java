package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Renderer{
    private static final float NAME_X = EndTurnButton.X - 20;
    private static final float NAME_Y = EndTurnButton.Y + EndTurnButton.HEIGHT + 20;

    private static final float PLAYER_FONT_Y = -10f;

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
        renderCharacter(world.player1);
        renderCharacter(world.player2);
    }

    private void renderUI(){
        Texture cardBar;
        if(world.player == world.player1){
            cardBar = Assets.cardBarBlue;
        } else{
            cardBar = Assets.cardBarRed;
        }

        batcher.draw(cardBar,
                CardBar.X,
                CardBar.Y,
                CardBar.WIDTH,
                CardBar.HEIGHT);

        batcher.draw(Assets.endTurnButton,
                EndTurnButton.X,
                EndTurnButton.Y,
                EndTurnButton.WIDTH,
                EndTurnButton.HEIGHT);

        renderFont();
    }

    private void renderFont(){
        String turn;
        if(world.player == world.player1){
            turn = "Player 1";
        } else{
            turn = "Player 2";
        }
        Assets.font.draw(batcher,
                turn,
                NAME_X,
                NAME_Y);
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
        if(world.pathTracker == null){
            return;
        }
        LinkedList<Tile> pathTracker = world.pathTracker;
        Tile alphaTile;
        for(int i=0; i<pathTracker.size(); i++){
            alphaTile = pathTracker.get(i);
            batcher.draw(Assets.alphaTile,
                    alphaTile.getPosition().x,
                    alphaTile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderTrap(){
        LinkedList<Trap> traps = world.player.getTraps();
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

    private void renderCharacter(Player player){
        String text = "HP: " + player.getHealth();
        Texture texture;
        if(player == world.player1){
            texture = Assets.player1;
        } else{
            texture = Assets.player2;
        }

        batcher.draw(texture,
                player.getPosition().x,
                player.getPosition().y,
                Player.WIDTH,
                Player.HEIGHT);

        GlyphLayout glyph = Assets.fontSmall.draw(batcher, text, -400, -400);
        Assets.fontSmall.draw(
                batcher,
                text,
                player.getCenter().x - glyph.width / 2f,
                player.getPosition().y - PLAYER_FONT_Y);
    }

    private void renderCard(){
        LinkedList<Card> cards = world.player.getCards();
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
