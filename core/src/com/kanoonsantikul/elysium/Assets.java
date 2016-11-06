package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.Color;

public class Assets {
    public static Texture tile;
    public static Texture hilightTile;
    public static Texture moveTile;
    public static Texture targetTile;
    public static Texture player1;
    public static Texture player1Pic;
    public static Texture player1PicSmall;
    public static Texture player2;
    public static Texture player2Pic;
    public static Texture player2PicSmall;
    public static Texture endTurnButton;
    public static Texture endTurnButtonPressed;
    public static Texture cardBar;
    public static Texture materialSmall;
    public static Texture material;
    public static Texture[] cards;
    public static Texture[] fullCards;
    public static Texture[] traps;
    public static Texture[] notifyText;

    public static BitmapFont font;
    public static BitmapFont fontSmall;

    public static ParticleEffect bombEffect;

    public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

    public static void load () {
        tile = loadTexture("tile.png");
        hilightTile = loadTexture("hilight-tile.png");
        moveTile = loadTexture("move-tile.png");
        targetTile = loadTexture("target-tile.png");
        player1 = loadTexture("player1.png");
        player1Pic = loadTexture("player1-pic.png");
        player1PicSmall = loadTexture("player1-pic-small.png");
        player2 = loadTexture("player2.png");
        player2Pic = loadTexture("player2-pic.png");
        player2PicSmall = loadTexture("player2-pic-small.png");
        endTurnButton = loadTexture("end-turn-button.png");
        endTurnButtonPressed = loadTexture("end-turn-button-pressed.png");
        materialSmall = loadTexture("material-small.png");
        material = loadTexture("material.png");
        cardBar = loadTexture("card-bar.png");

        cards = new Texture[] {
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

        fullCards = new Texture[] {
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

        traps = new Texture[] {
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

        notifyText = new Texture[] {
            loadTexture("text-box.png"),
            loadTexture("no-material-text.png")
        };

        font = new BitmapFont(
                Gdx.files.internal("font/font.fnt"),
                new TextureRegion(loadTexture("font/font.png")),
                false);
        font.setColor(Color.BLACK);
        fontSmall = new BitmapFont(
                Gdx.files.internal("font/font-small.fnt"),
                new TextureRegion(loadTexture("font/font-small.png")),
                false);

        bombEffect = new ParticleEffect();
        bombEffect.load(
                Gdx.files.internal("effect/bomb"),
                Gdx.files.internal(""));
    }

    public static void dispose () {
        tile.dispose();
        hilightTile.dispose();
        moveTile.dispose();
        targetTile.dispose();
        player1.dispose();
        player1Pic.dispose();
        player1PicSmall.dispose();
        player2.dispose();
        player2Pic.dispose();
        player2PicSmall.dispose();
        endTurnButton.dispose();
        endTurnButtonPressed.dispose();
        materialSmall.dispose();
        material.dispose();
        cardBar.dispose();

        for (int i = 0; i < cards.length; i++) {
            cards[i].dispose();
            fullCards[i].dispose();
            traps[i].dispose();
        }

        for (int i = 0; i < notifyText.length; i++) {
            notifyText[i].dispose();
        }

        font.dispose();
        fontSmall.dispose();

        bombEffect.dispose();
    }
}
