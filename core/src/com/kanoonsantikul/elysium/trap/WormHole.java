package com.kanoonsantikul.elysium;

public class WormHole extends Trap {
    public static final int ID = 9;
    public static final float WEIGHT = 0.08f;
    public static final int COST = 5;

    public WormHole (Tile tile, Player user) {
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void toggle (Player actor) {
        super.toggle(actor);

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        World.instance().actionQueue.add(
                new AddMaterialAction(actor, -actor.getMaterial()));
        user.removeTrap(this);
    }
}
