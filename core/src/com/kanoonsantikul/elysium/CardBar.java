package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class CardBar extends GameObject{
    public static final float WIDTH = Assets.cardBarBlue.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.cardBarBlue.getHeight() * Elysium.DEVICE_RATIO;

    private static final float X = 0;
    private static final float Y = 0;

    public CardBar(){
        setPosition(new Vector2(X, Y));
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }
}
