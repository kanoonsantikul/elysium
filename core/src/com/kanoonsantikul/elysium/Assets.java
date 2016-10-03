package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets{
    public static Texture tile;

    public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

    public static void load(){
        tile = loadTexture("tile.png");
    }
}
