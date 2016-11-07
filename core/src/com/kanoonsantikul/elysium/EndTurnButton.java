package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class EndTurnButton extends GameObject {
    public static final float WIDTH = Assets.endTurnButton.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.endTurnButton.getHeight() * Elysium.DEVICE_RATIO;

    public static final float X = Elysium.WIDTH - WIDTH - 9f;
    public static final float Y = 12f;

    private boolean isPressed;

    public EndTurnButton () {
        setPosition(new Vector2(X, Y));
    }

    public float getWidth () {
        return WIDTH;
    }

    public float getHeight () {
        return HEIGHT;
    }

    public boolean isPressed () {
        return isPressed;
    }

    public void setPressed (boolean isPressed) {
        this.isPressed = isPressed;
    }
}
