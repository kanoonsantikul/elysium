package com.kanoonsantikul.elysium;

public abstract class BoardObject extends GameObject{
    private Tile tile;

    public Tile getTile(){
        return tile;
    }

    public void setTile(Tile tile){
        this.tile = tile;
    }
}
