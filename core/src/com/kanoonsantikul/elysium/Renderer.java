package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.Color;

public class Renderer {
    private static final float FONT_SMALL_SPACING = 12f;
    private static final float FONT_SPACING = 17f;

    private static final float PLAYER_DATA_X = 257;
    private static final float PLAYER_DATA_Y = Elysium.HEIGHT - Assets.player1Pic.getHeight() - 20;
    private static final float ENEMY_DATA_X = PLAYER_DATA_X + Assets.player1Pic.getWidth();
    private static final float ENEMY_DATA_Y = PLAYER_DATA_Y;

    public static GlyphLayout textLayout = new GlyphLayout();

    private World world;
    private SpriteBatch batcher;

    public Renderer (World world, SpriteBatch batcher) {
        this.world = world;
        this.batcher = batcher;
    }

    public void render (float delta) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(127/255f, 174/255f, 108/255f, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();
        renderBoard();
        renderUI(delta);
        renderCard();
        batcher.end();
    }

    private void renderBoard () {
        renderTiles();
        renderTrap();
        renderPathTracker();
        renderTargetTiles();
        renderCharacter(world.player);
        renderCharacter(world.enemy);
    }

    private void renderTiles () {
        for (Tile tile : world.tiles) {
            if (!tile.isVisible) {
                continue;
            }
            batcher.draw(Assets.tile,
                    tile.getPosition().x,
                    tile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }

        if (world.isMyTurn && !world.player.isLock) {
            batcher.draw(Assets.hilightTile,
                    world.player.getTile().getPosition().x,
                    world.player.getTile().getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderTrap () {
        for (Trap trap : world.player.traps) {
            if (trap.isToggled()) {
                continue;
            }
            batcher.draw(Assets.traps[trap.getId()],
                    trap.getPosition().x,
                    trap.getPosition().y,
                    Trap.WIDTH,
                    Trap.HEIGHT);
        }
    }

    private void renderPathTracker () {
        LinkedList<Tile> pathTracker = world.pathTracker;

        if (pathTracker == null) {
            return;
        }

        for (Tile moveTile : pathTracker) {
            batcher.draw(Assets.moveTile,
                    moveTile.getPosition().x,
                    moveTile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderTargetTiles () {
        LinkedList<Tile> targetTiles = world.targetTiles;

        if (targetTiles == null) {
            return;
        }

        for (Tile targetTile : targetTiles) {
            batcher.draw(Assets.targetTile,
                    targetTile.getPosition().x,
                    targetTile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderCharacter (Player player) {
        String health = "" + player.health;
        Texture texture;
        if (player.getNumber() == Player.PLAYER1) {
            texture = Assets.player1;
        } else {
            texture = Assets.player2;
        }

        batcher.draw(texture,
                player.getPosition().x,
                player.getPosition().y,
                Player.WIDTH,
                Player.HEIGHT);

        textLayout.setText(Assets.fontSmall, health);
        Assets.fontSmall.draw(
                batcher,
                health,
                player.getCenter().x - textLayout.width / 2f,
                player.getPosition().y + FONT_SMALL_SPACING);
    }


    private void renderUI (float delta) {
        batcher.draw(Assets.cardBar,
                0,
                0,
                Assets.cardBar.getWidth(),
                Assets.cardBar.getHeight());

        renderPlayerData(world.player);
        renderPlayerData(world.enemy);
        renderEndTurnButton();
        renderEffect(delta);
        renderNotifyText();
    }

    private void renderPlayerData (Player player) {
        float x, y;
        Texture texture;
        String name;

        if(player == world.player){
            x = PLAYER_DATA_X;
            y = PLAYER_DATA_Y;
            name = "YOU";

            if(player.getNumber() == Player.PLAYER1){
                texture = Assets.player1Pic;
            } else {
                texture = Assets.player2Pic;
            }
        } else{
            x = ENEMY_DATA_X;
            y = ENEMY_DATA_Y;
            name = "ENEMY";

            if(player.getNumber() == Player.PLAYER1){
                texture = Assets.player1PicSmall;
            } else {
                texture = Assets.player2PicSmall;
            }
        }

        batcher.draw(texture,
                x,
                y,
                texture.getWidth(),
                texture.getHeight());
        Assets.font.setColor(Color.WHITE);
        Assets.font.draw(batcher,
                name,
                x + 10,
                y + 20);
        Assets.font.setColor(Color.BLACK);

        batcher.draw(Assets.material,
                x,
                y - Assets.material.getHeight() - 3,
                Assets.material.getWidth(),
                Assets.material.getHeight());
        Assets.font.draw(batcher,
                "x " + player.material,
                x + Assets.material.getWidth(),
                y - Assets.material.getHeight() * 2 / 3 + Assets.font.getXHeight());
    }

    private void renderEndTurnButton () {
        Texture endTurnButton;
        if (world.endTurnButton.isPressed) {
            endTurnButton = Assets.endTurnButtonPressed;
        } else {
            endTurnButton = Assets.endTurnButton;
        }
        batcher.draw(endTurnButton,
                EndTurnButton.X,
                EndTurnButton.Y,
                EndTurnButton.WIDTH,
                EndTurnButton.HEIGHT);
    }

    private void renderEffect (float delta) {
        LinkedList<Effect> effectPool = new LinkedList<Effect>(world.effectPool);
        for (Effect effect : effectPool) {
            if (!effect.draw(batcher, delta)) {
                world.effectPool.remove(effect);
            } else {
                textLayout.setText(Assets.font, effect.getMessage());
                Assets.font.draw(batcher,
                        effect.getMessage(),
                        effect.getCenter().x - textLayout.width / 2,
                        effect.getCenter().y + textLayout.height / 2);
            }
        }
    }

    private void renderNotifyText () {
        if (world.notifyText != 0) {
            Texture textBox = Assets.notifyText[world.notifyText];
            batcher.draw(textBox,
                    Elysium.WIDTH / 2 - textBox.getWidth() / 2,
                    Elysium.HEIGHT / 2 + textBox.getHeight() / 2,
                    textBox.getWidth(),
                    textBox.getHeight());
        }
    }

    private void renderCard () {
        for (Card card : world.player.cards) {
            if (!card.isVisible) {
                Trap trap = TrapBuilder.getInstance(card.getId());
                batcher.setColor(1, 1, 1, Card.CARD_FADE_ALPHA);
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

            if (!card.isMoving) {
                renderCardMaterial(card);
            }
        }

        renderFullCard();
    }

    public void renderCardMaterial (Card card) {
        batcher.draw(Assets.materialSmall,
                card.getPosition().x + Card.WIDTH / 5,
                card.getPosition().y + Card.HEIGHT + FONT_SMALL_SPACING / 3,
                Assets.materialSmall.getWidth(),
                Assets.materialSmall.getHeight());
        Assets.font.draw(batcher,
                TrapBuilder.getInstance(card.getId()).getCost() + "",
                card.getPosition().x + Card.WIDTH / 5 + Assets.materialSmall.getWidth() + 5,
                card.getPosition().y + Card.HEIGHT + FONT_SMALL_SPACING / 3
                        + Assets.materialSmall.getHeight() / 3 + Assets.font.getXHeight());
    }

    public void renderFullCard () {
        int cardId = world.fullCard.getCardId();
        if (cardId != FullCard.NULL_CARD) {
            batcher.draw(Assets.fullCards[cardId],
                    world.fullCard.getPosition().x,
                    world.fullCard.getPosition().y,
                    FullCard.WIDTH,
                    FullCard.HEIGHT);
        }
    }
}
