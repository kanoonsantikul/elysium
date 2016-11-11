package com.kanoonsantikul.elysium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Card extends GameObject {
    public static final float WIDTH = Assets.cards[0].getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.cards[0].getHeight() * Elysium.DEVICE_RATIO;

    public static final float CARD_FULL_ALPHA = 1f;
    public static final float CARD_FADE_ALPHA = 0.55f;

    private static float INIT_X = 9f;
    private static float INIT_Y = 13f;

    private int id;
    private int number;

    protected float alpha = 1;
    protected boolean isMoving;

    public Card (int id) {
        this.id = id;
        positioning();
    }

    public float getWidth () {
        return WIDTH;
    }

    public float getHeight () {
        return HEIGHT;
    }

    public int getId () {
        return id;
    }

    public int getNumber () {
        return number;
    }

    public void setNumber (int number) {
        this.number = number;
        positioning();
    }

    public void positioning () {
        float y = INIT_Y;
        float x = INIT_X + (WIDTH + 5)* number;
        setPosition(new Vector2(x, y));
    }
}
