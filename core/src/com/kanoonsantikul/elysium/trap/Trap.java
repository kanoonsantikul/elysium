package com.kanoonsantikul.elysium;

import com.kanoonsantikul.elysium.TurnManager.TurnStateChangeListener;

import com.badlogic.gdx.math.Vector2;

public class Trap extends BoardObject implements TurnStateChangeListener{
    public static final float WIDTH = Assets.traps[0].getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.traps[0].getHeight() * Elysium.DEVICE_RATIO;

    protected int id;
    protected Player user;
    protected boolean isToggled = false;

    public Trap (int id, Tile tile, Player user){
        setId(id);
        if(tile != null){
            setTile(tile);
            setCenter(tile.getCenter());
        }
        if(user != null){
            this.user = user;
        }
    }

    @Override
    public void onTurnStart(Player player){

    }

    @Override
    public void onTurnEnd(Player player){

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

    public boolean isToggled(){
        return isToggled;
    }

    public void toggle(GameObject actor){
        isToggled = true;
    }
}
