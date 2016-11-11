package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class Player extends BoardObject {
    public static final float WIDTH = Assets.player1.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.player1.getHeight() * Elysium.DEVICE_RATIO;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    protected int moveRange = 3;
    protected int trapRange = 2;
    protected int health = 1000;
    protected int material = 0;
    protected LinkedList<Card> cards;
    protected LinkedList<Trap> traps;
    protected boolean isMoved;

    private int number;

    public Player (int number, Tile tile) {
        this.number = number;

        cards = new LinkedList<Card>();
        traps = new LinkedList<Trap>();
        isMoved = false;

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
}
