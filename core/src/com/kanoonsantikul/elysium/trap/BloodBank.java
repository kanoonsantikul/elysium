package com.kanoonsantikul.elysium;

public class BloodBank extends Trap {
    public static final int ID = 10;
    public static final float WEIGHT = 0.07f;
    public static final int COST = 5;
    private static final int HEALTH = 200;

    public BloodBank (Tile tile, Player user) {
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void toggle (Player actor) {
        super.toggle(actor);

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        World.instance().actionQueue.add(
                new HealthAction(actor, HEALTH));
        user.removeTrap(this);
    }
}
