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

    private static final float PLAYER1_DATA_X = 265;
    private static final float PLAYER1_DATA_Y = 135;
    private static final float PLAYER2_DATA_X = 355;
    private static final float PLAYER2_DATA_Y = 135;

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
            if(!tile.isVisible()){
                continue;
            }
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

        renderPlayerData(world.player);
        renderPlayerData(world.enemy);
        renderEndTurnButton();
        renderEffect(delta);
        renderNotifyText();
    }

    private void renderPlayerData(Player player){
        float x, y;
        if(player == world.player){
            x = PLAYER1_DATA_X;
            y = PLAYER1_DATA_Y;
        } else{
            x = PLAYER2_DATA_X;
            y = PLAYER2_DATA_Y;
        }

        batcher.draw(Assets.material,
                x,
                y,
                Assets.material.getWidth(),
                Assets.material.getHeight());
        Assets.font.draw(batcher,
                "x " + player.getMaterial(),
                x + Assets.material.getWidth(),
                y + Assets.material.getHeight() / 2 + Assets.font.getXHeight());

        Texture texture;
        if(player.getNumber() == Player.PLAYER1){
            texture = Assets.player1;
        } else{
            texture = Assets.player2;
        }
        batcher.draw(texture,
                x,
                y + Assets.material.getHeight() + 3,
                texture.getWidth(),
                texture.getHeight());
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
            Texture textBox = Assets.notifyText[world.notifyText];
            batcher.draw(textBox,
                    Elysium.WIDTH / 2 - textBox.getWidth() / 2,
                    Elysium.HEIGHT / 2 + textBox.getHeight() / 2,
                    textBox.getWidth(),
                    textBox.getHeight());
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

            batcher.draw(Assets.materialSmall,
                    card.getPosition().x + Card.WIDTH / 5,
                    card.getPosition().y + Card.HEIGHT + FONT_SMALL_SPACING / 3,
                    Assets.materialSmall.getWidth(),
                    Assets.materialSmall.getHeight());
            Assets.font.draw(batcher,
                    TrapBuilder.getInstance(card.getId()).getCost() + "",
                    card.getPosition().x + Card.WIDTH / 5 + Assets.materialSmall.getWidth() + 5,
                    card.getPosition().y + Card.HEIGHT + FONT_SMALL_SPACING / 3
                            + Assets.materialSmall.getHeight() / 2 + Assets.font.getXHeight());
        }

        int cardId = world.fullCard.getCardId();
        if(cardId != -1){
            batcher.draw(Assets.fullCards[cardId],
                    world.fullCard.getPosition().x,
                    world.fullCard.getPosition().y,
                    FullCard.WIDTH,
                    FullCard.HEIGHT);
        }
    }

}
