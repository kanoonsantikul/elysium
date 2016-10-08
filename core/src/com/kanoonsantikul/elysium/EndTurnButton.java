package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class EndTurnButton extends GameObject{
    public static final float WIDTH = Assets.endTurnButton.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.endTurnButton.getHeight() * Elysium.DEVICE_RATIO;

    private static final float X = Elysium.WIDTH - WIDTH / 2 - 9f;
    private static final float Y = HEIGHT / 2 + 12;

    public EndTurnButton(){
        setCenter(new Vector2(X, Y));
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }
}
