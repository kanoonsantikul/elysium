package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets{
    public static Texture tile;
    public static Texture alphaTile;
    public static Texture player1;
    public static Texture player2;
    public static Texture endTurnButton;
    public static Texture cardBarBlue;
    public static Texture cardBarRed;
    public static Texture card;

    public static BitmapFont font;

    public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

    public static void load(){
        tile = loadTexture("tile.png");
        alphaTile = loadTexture("alpha-tile.png");
        player1 = loadTexture("player1.png");
        player2 = loadTexture("player2.png");
        endTurnButton = loadTexture("end-turn-button.png");
        cardBarBlue = loadTexture("card-bar-blue.png");
        cardBarRed = loadTexture("card-bar-red.png");
        card = loadTexture("card.png");

        font = new BitmapFont(
                Gdx.files.internal("font/font.fnt"),
                Gdx.files.internal("font/font.png"),
                false);
    }
}
