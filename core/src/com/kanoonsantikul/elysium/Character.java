package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Character extends GameObject{
    public static final float WIDTH = Assets.player1.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.player1.getHeight() * Elysium.DEVICE_RATIO;

    public Character(float x, float y){
        this(new Vector2(x, y));
    }

    public Character(Vector2 position){
        setCenter(position);
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }
}
