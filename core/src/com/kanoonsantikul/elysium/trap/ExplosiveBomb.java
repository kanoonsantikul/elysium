package com.kanoonsantikul.elysium;

import java.util.LinkedList;

public class ExplosiveBomb extends Trap{
    public static final int ID = 4;
    public static final int COST = 2;
    private static final int DAMAGE = 100;
    private static final int RANGE = 1;

    public ExplosiveBomb(Tile tile, Player user){
        super(ID, COST, tile, user);
    }

    @Override
    public void toggle(GameObject actor){
        super.toggle(actor);

        LinkedList<Tile> tiles = getTile().getNeighbors(RANGE, true);

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        for(int i=0; i<tiles.size(); i++){
            World.instance().actionQueue.add(
                    new DamageAction(tiles.get(i), DAMAGE));
        }
        user.removeTrap(this);
    }
}
