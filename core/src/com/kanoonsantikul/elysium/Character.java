package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Character extends GameObject{
    public static final float WIDTH = Assets.character.getWidth() * World.DEVICE_RATIO;
    public static final float HEIGHT = Assets.character.getHeight() * World.DEVICE_RATIO;

    public Character(float x, float y){
        new Character(new Vector2(x, y));
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
