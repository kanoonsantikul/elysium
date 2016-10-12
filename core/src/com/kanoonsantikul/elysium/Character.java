package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Character extends BoardObject{
    public static final float WIDTH = Assets.player1.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.player1.getHeight() * Elysium.DEVICE_RATIO;

    public static final int moveRange = 3;
    public static final int trapRange = 4;

    private Tile tile;
    private boolean isMoved;

    public Character(Tile tile){
        this(tile.getCenter(), tile);
    }

    public Character(Vector2 position, Tile tile){
        setIsMoved(false);
        setTile(tile);
        setCenter(position);
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }

    public boolean getIsMoved(){
        return isMoved;
    }

    public void setIsMoved(boolean isMoved){
        this.isMoved = isMoved;
    }
}
