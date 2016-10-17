package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class Player extends BoardObject{
    public static final float WIDTH = Assets.player1.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.player1.getHeight() * Elysium.DEVICE_RATIO;

    public int moveRange = 3;
    public int trapRange = 4;

    private int health = 2000;
    private LinkedList<Card> cards;
    private LinkedList<Trap> traps;

    private Tile tile;
    private boolean isMoved;

    public Player(Tile tile){
        this(tile.getCenter(), tile);
    }

    public Player(Vector2 position, Tile tile){
        cards = new LinkedList<Card>();
        traps = new LinkedList<Trap>();

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

    public LinkedList<Card> getCards(){
        return cards;
    }

    public LinkedList<Trap> getTraps(){
        return traps;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return health;
    }

    public void updateCards(){
        Card card;
        for(int i=0; i<cards.size(); i++){
            card = cards.get(i);
            card.setNumber(i);
        }
    }
}
