package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class CardBar extends GameObject {
    public static final float WIDTH = Assets.cardBar.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.cardBar.getHeight() * Elysium.DEVICE_RATIO;

    public static final float X = 0;
    public static final float Y = 0;

    public float getWidth () {
        return WIDTH;
    }

    public float getHeight () {
        return HEIGHT;
    }
}
