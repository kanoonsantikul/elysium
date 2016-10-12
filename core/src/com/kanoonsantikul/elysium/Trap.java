package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Trap extends BoardObject{
    public static final float WIDTH = Assets.traps[0].getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.traps[0].getHeight() * Elysium.DEVICE_RATIO;

    private int id;
    private Tile tile;

    public Trap (int id, Tile tile){
        setId(id);
        if(tile != null){
            setTile(tile);
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
}
