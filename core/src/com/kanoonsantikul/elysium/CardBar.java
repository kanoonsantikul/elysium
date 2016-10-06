package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class CardBar extends GameObject{
    public static final float WIDTH = Assets.cardBar.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.cardBar.getHeight() * Elysium.DEVICE_RATIO;

    private static final float X = 0;
    private static final float Y = 0;

    private boolean isPlayer1Turn;

    public CardBar(boolean isPlayer1Turn){
        setPosition(new Vector2(X, Y));
        toggle(true);
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }

    public void toggle(boolean isPlayer1Turn){
        this.isPlayer1Turn = isPlayer1Turn;
    }
}
