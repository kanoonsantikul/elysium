package com.kanoonsantikul.elysium;

import com.kanoonsantikul.elysium.TurnManager.TurnStateChangeListener;

import com.badlogic.gdx.math.Vector2;

public class Trap extends BoardObject implements TurnStateChangeListener, Cloneable{
    public static final float WIDTH = Assets.traps[0].getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.traps[0].getHeight() * Elysium.DEVICE_RATIO;

    protected int id;
    protected int cost;
    protected Player user;
    protected boolean isToggled = false;

    public Trap (int id, int cost, Tile tile, Player user){
        this.id = id;
        this.cost = cost;
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

    public int getCost(){
        return cost;
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

    public Trap createCopy(Tile tile, Player user){
        Trap trap = null;
        try{
            trap = (Trap)this.clone();
            trap.setTile(tile);
            trap.setCenter(tile.getCenter());
            trap.user = user;
        } catch(Exception e){

        }
        return trap;
    }
}
