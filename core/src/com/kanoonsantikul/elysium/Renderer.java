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
    private static final float FONT_SMALL_SPACING = 12f;
    private static final float FONT_SPACING = 17f;

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
        renderCharacter(world.player);
        renderCharacter(world.enemy);
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

        if(world.isMyTurn && !world.player.isLock()){
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
        if(player.getNumber() == Player.PLAYER1){
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
                player.getPosition().y + FONT_SMALL_SPACING);
    }


    private void renderUI(float delta){
        batcher.draw(Assets.cardBar,
                CardBar.X,
                CardBar.Y,
                CardBar.WIDTH,
                CardBar.HEIGHT);

        renderPlayerMaterial();
        renderEndTurnButton();
        renderEffect(delta);
        renderNotifyText();
    }

    private void renderPlayerMaterial(){
        Assets.font.draw(batcher,
                "MATERIAL: " + world.player.getMaterial(),
                300,
                150);
    }

    private void renderEndTurnButton(){
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
                Assets.font.draw(batcher,
                        damage,
                        effect.getEmitters().first().getX() - glyph.width / 2,
                        effect.getEmitters().first().getY() + glyph.height / 2);
            }
        }
    }

    private void renderNotifyText(){
        if(world.notifyText != 0){
            
        }
    }

    private void renderCard(){
        LinkedList<Card> cards = world.player.getCards();
        Card card;
        for(int i=0; i<cards.size(); i++){
            card = cards.get(i);
            if(!card.isVisible()){
                Trap trap = TrapBuilder.getInstance(card.getId());
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

            Assets.font.draw(batcher,
                    TrapBuilder.getInstance(card.getId()).getCost() + "",
                    card.getPosition().x + Card.WIDTH / 2,
                    card.getPosition().y + Card.HEIGHT + FONT_SPACING);
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
