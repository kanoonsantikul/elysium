package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class Player extends BoardObject {
    public static final float WIDTH = Assets.player1.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.player1.getHeight() * Elysium.DEVICE_RATIO;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    private int number;

    private int moveRange = 3;
    private int trapRange = 2;
    private int health = 1000;
    private int material = 0;
    private LinkedList<Card> cards;
    private LinkedList<Trap> traps;
    private boolean isMoved;

    public Player (int number, Tile tile) {
        this.number = number;

        cards = new LinkedList<Card>();
        traps = new LinkedList<Trap>();

        setIsMoved(false);
        setTile(tile);
        setCenter(tile.getCenter());
    }

    public int getNumber () {
        return this.number;
    }

    public float getWidth () {
        return WIDTH;
    }

    public float getHeight () {
        return HEIGHT;
    }

    public int getMoveRange () {
        return moveRange;
    }

    public void setMoveRange (int moveRange) {
        this.moveRange = moveRange;
    }

    public int getTrapRange () {
        return trapRange;
    }

    public void setTrapRange (int trapRange) {
        this.trapRange = trapRange;
    }

    public int getHealth () {
        return health;
    }

    public void setHealth (int health) {
        this.health = health;
    }

    public int getMaterial () {
        return this.material;
    }

    public void addMaterial (int diff) {
        this.material += diff;
    }

    public LinkedList<Card> getCards () {
        return cards;
    }

    public void setCards (LinkedList<Card> cards) {
        this.cards = cards;
    }

    public void addCard (Card card) {
        cards.add(card);
        World.gameObjects.add(card);
        updateCards();
    }

    public void removeCard (Card card) {
        cards.remove(card);
        World.gameObjects.remove(card);
        updateCards();
    }

    public void updateCards () {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setNumber(i);
        }

        MultiplayerUpdater.instance().sendCardUpdate(cards);
    }

    public LinkedList<Trap> getTraps () {
        return traps;
    }

    public void addTrap (Trap trap) {
        traps.add(trap);
        World world = World.instance();
        world.gameObjects.add(trap);
        world.turnManager.addListener(trap);
    }

    public void removeTrap (Trap trap) {
        traps.remove(trap);
        World world = World.instance();
        world.gameObjects.remove(trap);
        world.turnManager.removeListener(trap);
    }

    public boolean getIsMoved () {
        return isMoved;
    }

    public void setIsMoved (boolean isMoved) {
        this.isMoved = isMoved;
    }
}
