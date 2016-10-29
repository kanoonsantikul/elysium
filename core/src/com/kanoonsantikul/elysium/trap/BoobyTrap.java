package com.kanoonsantikul.elysium;

public class BoobyTrap extends Trap{
    private static final int DAMAGE = 200;

    public BoobyTrap(int id, Tile tile, Player user){
        super(id, tile, user);
    }

    @Override
    public void toggle(GameObject actor){
        super.toggle(actor);

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        World.instance().actionQueue.add(
                new DamageAction(getTile(), DAMAGE));
        user.removeTrap(this);
    }
}