package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Character extends GameObject{
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
        setTile(tile);
        setCenter(position);
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }

    public Tile getTile(){
        return tile;
    }

    public void setTile(Tile tile){
        this.tile = tile;
    }
}
