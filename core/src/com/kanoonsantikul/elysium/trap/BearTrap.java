package com.kanoonsantikul.elysium;

public class BearTrap extends Trap {
    public static final int ID = 1;
    public static final float WEIGHT = 0.085f;
    public static final int COST = 3;
    private static final int LOCK_TURN = 2;

    private Player actor;
    private int turnCount = 0;

    public BearTrap (Tile tile, Player user) {
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void onTurnStart (Player player) {
        if (isToggled && player == actor) {
            if (turnCount < LOCK_TURN) {
                actor.isMoved = true;
                turnCount++;
            } else {
                user.removeTrap(this);
            }
        }
    }

    @Override
    public void toggle (Player actor) {
        if (isToggled) {
            return;
        }
        super.toggle(actor);
        this.actor = actor;

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        isVisible = false;
        if(actor == null){
            user.removeTrap(this);
        }
    }
}
