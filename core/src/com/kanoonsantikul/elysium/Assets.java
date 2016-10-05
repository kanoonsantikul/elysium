package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets{
    public static Texture tile;
    public static Texture alphaTile;
    public static Texture character;

    public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

    public static void load(){
        tile = loadTexture("tile.png");
        alphaTile = loadTexture("alpha-tile.png");
        character = loadTexture("character.png");
    }
}
