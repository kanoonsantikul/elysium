package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.Color;

public class Renderer{
    private static final float NAME_X = EndTurnButton.X - 5;
    private static final float NAME_Y = EndTurnButton.Y + EndTurnButton.HEIGHT + 20;

    private static final float PLAYER_FONT_Y = -12f;

    World world;
    SpriteBatch batcher;

    public Renderer(World world, SpriteBatch batcher){
        this.world = world;
        this.batcher = batcher;
    }

    public void render(float delta){
        GL20 gl = Gdx.gl;
        gl.glClearColor(91/255f, 222/255f, 162/255f, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();
        renderBoard();
        renderUI(delta);
        renderCard();
        batcher.end();
    }

    private void renderBoard(){
        renderTiles();
        renderTrap();
        renderPathTracker();
        renderTargetTiles();
        renderCharacter(world.player1);
        renderCharacter(world.player2);
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

        if(!world.player.isLock()){
            batcher.draw(Assets.hilightTile,
                    world.player.getTile().getPosition().x,
                    world.player.getTile().getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderTrap(){
        LinkedList<Trap> traps = world.player.getTraps();
        Trap trap;
        for(int i=0; i<traps.size() ;i++){
            trap = traps.get(i);
            if(trap.isToggled()){
                continue;
            }
            batcher.draw(Assets.traps[trap.getId()],
                    trap.getPosition().x,
                    trap.getPosition().y,
                    Trap.WIDTH,
                    Trap.HEIGHT);
        }
    }

    private void renderPathTracker(){
        LinkedList<Tile> pathTracker = world.pathTracker;

        if(pathTracker == null){
            return;
        }

        Tile moveTile;
        for(int i=0; i<pathTracker.size(); i++){
            moveTile = pathTracker.get(i);
            batcher.draw(Assets.moveTile,
                    moveTile.getPosition().x,
                    moveTile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderTargetTiles(){
        LinkedList<Tile> targetTiles = world.targetTiles;

        if(targetTiles == null){
            return;
        }

        Tile targetTile;
        for(int i=0; i<targetTiles.size(); i++){
            targetTile = targetTiles.get(i);
            batcher.draw(Assets.targetTile,
                    targetTile.getPosition().x,
                    targetTile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderCharacter(Player player){
        String text = "" + player.getHealth();
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


    private void renderUI(float delta){
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

        Texture endTurnButton;
        if(world.endTurnButton.isPressed()){
            endTurnButton = Assets.endTurnButtonPressed;
        } else{
            endTurnButton = Assets.endTurnButton;
        }
        batcher.draw(endTurnButton,
                EndTurnButton.X,
                EndTurnButton.Y,
                EndTurnButton.WIDTH,
                EndTurnButton.HEIGHT);

        renderFont();
        renderEffect(delta);
    }

    private void renderFont(){
        String turn;
        if(world.player == world.player1){
            turn = "Player 1";
        } else{
            turn = "Player 2";
        }
        Assets.font.setColor(Color.BLACK);
        Assets.font.draw(batcher,
                turn,
                NAME_X,
                NAME_Y);
        Assets.font.setColor(Color.WHITE);
    }

    private void renderEffect(float delta){
        ParticleEffect effect;
        for(int i=0; i<world.effects.size(); i++){
            effect = world.effects.get(i).getEffect();
            effect.draw(batcher);
            effect.update(delta);

            if(effect.getEmitters().first().getPercentComplete() >= 0.5){
                world.effects.remove(world.effects.get(i));
            } else{
                String damage = world.effects.get(i).getDamage() + "";
                GlyphLayout glyph = Assets.font.draw(batcher, damage, -400, -400);
                Assets.font.setColor(Color.BLACK);
                Assets.font.draw(batcher,
                        damage,
                        effect.getEmitters().first().getX() - glyph.width / 2,
                        effect.getEmitters().first().getY() + glyph.height / 2);
                Assets.font.setColor(Color.WHITE);
            }
        }
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

        int cardId = world.fullCard.getCardId();
        if(cardId != -1){
            batcher.draw(Assets.fullCards[cardId],
                    FullCard.X,
                    FullCard.Y,
                    FullCard.WIDTH,
                    FullCard.HEIGHT);
        }
    }

}
