package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets{
    public static Texture tile;
    public static Texture hilightTile;
    public static Texture moveTile;
    public static Texture targetTile;
    public static Texture player1;
    public static Texture player2;
    public static Texture endTurnButton;
    public static Texture endTurnButtonPressed;
    public static Texture cardBarBlue;
    public static Texture cardBarRed;
    public static Texture[] cards;
    public static Texture[] fullCards;
    public static Texture[] traps;

    public static BitmapFont font;
    public static BitmapFont fontSmall;

    public static ParticleEffect bombEffect;

    public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

    public static void load(){
        tile = loadTexture("tile.png");
        hilightTile = loadTexture("hilight-tile.png");
        moveTile = loadTexture("move-tile.png");
        targetTile = loadTexture("target-tile.png");
        player1 = loadTexture("player1.png");
        player2 = loadTexture("player2.png");
        endTurnButton = loadTexture("end-turn-button.png");
        endTurnButtonPressed = loadTexture("end-turn-button-pressed.png");
        cardBarBlue = loadTexture("card-bar-1.png");
        cardBarRed = loadTexture("card-bar-2.png");

        cards = new Texture[]{
            loadTexture("card.png"),
            loadTexture("bear-trap-card.png"),
            loadTexture("booby-trap-card.png"),
            loadTexture("ticking-time-bomb-card.png"),
            loadTexture("explosive-bomb-card.png"),
            loadTexture("snare-trap-card.png"),
            loadTexture("fuzzy-bomb-card.png"),
            loadTexture("venom-gas-card.png"),
            loadTexture("spike-trap-card.png")
        };

        fullCards = new Texture[]{
            loadTexture("full-card.png"),
            loadTexture("bear-trap-full-card.png"),
            loadTexture("booby-trap-full-card.png"),
            loadTexture("ticking-time-bomb-full-card.png"),
            loadTexture("explosive-bomb-full-card.png"),
            loadTexture("snare-trap-full-card.png"),
            loadTexture("fuzzy-bomb-full-card.png"),
            loadTexture("venom-gas-full-card.png"),
            loadTexture("spike-trap-full-card.png")
        };

        traps = new Texture[]{
            loadTexture("trap.png"),
            loadTexture("bear-trap.png"),
            loadTexture("booby-trap.png"),
            loadTexture("ticking-time-bomb.png"),
            loadTexture("explosive-bomb.png"),
            loadTexture("snare-trap.png"),
            loadTexture("fuzzy-bomb.png"),
            loadTexture("venom-gas.png"),
            loadTexture("spike-trap.png")
        };

        font = new BitmapFont(
                Gdx.files.internal("font/font.fnt"),
                new TextureRegion(loadTexture("font/font.png")),
                false);
        fontSmall = new BitmapFont(
                Gdx.files.internal("font/font-small.fnt"),
                new TextureRegion(loadTexture("font/font-small.png")),
                false);

        bombEffect = new ParticleEffect();
        bombEffect.load(
                Gdx.files.internal("effect/bomb"),
                Gdx.files.internal(""));
    }

    public static void dispose(){
        tile.dispose();
        hilightTile.dispose();
        moveTile.dispose();
        targetTile.dispose();
        player1.dispose();
        player2.dispose();
        endTurnButton.dispose();
        cardBarBlue.dispose();
        cardBarRed.dispose();

        for(int i=0; i<cards.length; i++){
            cards[i].dispose();
            fullCards[i].dispose();
            traps[i].dispose();
        }

        font.dispose();
        fontSmall.dispose();

        bombEffect.dispose();
    }
}
