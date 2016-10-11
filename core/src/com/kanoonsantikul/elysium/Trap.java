package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Trap extends GameObject{
    public static final float WIDTH = Assets.traps[0].getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.traps[0].getHeight() * Elysium.DEVICE_RATIO;

    private int id;
    private Tile tile;

    public Trap (int id, Tile tile){
        setId(id);
        setTile(tile);
        if(tile != null){
            setCenter(tile.getCenter());
        }
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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
