package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Tile extends GameObject{
    public static final float WIDTH = Assets.tile.getWidth() * World.DEVICE_RATIO;
    public static final float HEIGHT = Assets.tile.getHeight() * World.DEVICE_RATIO;

    public Tile(float x, float y){
        setPosition(new Vector2(x, y));
    }

    public Tile(Vector2 position){
        setPosition(position);
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }
}
