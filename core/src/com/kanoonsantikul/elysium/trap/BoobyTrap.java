package com.kanoonsantikul.elysium;

public class BoobyTrap extends Trap {
    public static final int ID = 2;
    public static final float WEIGHT = 0.2f;
    public static final int COST = 2;
    private static final int DAMAGE = 200;

    public BoobyTrap (Tile tile, Player user) {
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void toggle (GameObject actor) {
        super.toggle(actor);

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        World.instance().actionQueue.add(
                new DamageAction(getTile(), DAMAGE));
        user.removeTrap(this);
    }
}
