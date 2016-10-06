package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class EndTurnButton extends GameObject{
    public static final float WIDTH = Assets.endTurnButton.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.endTurnButton.getHeight() * Elysium.DEVICE_RATIO;

    private static final float X = Elysium.WIDTH - WIDTH / 2 - World.BOARD_X_INIT;
    private static final float Y = HEIGHT / 2 + 12;

    private boolean isPlayer1Turn;

    public EndTurnButton(boolean isPlayer1Turn){
        setCenter(new Vector2(X, Y));
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
