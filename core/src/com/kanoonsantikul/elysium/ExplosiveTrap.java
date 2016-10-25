package com.kanoonsantikul.elysium;

import java.util.LinkedList;

public class ExplosiveTrap extends Trap{
    private static final int DAMAGE = 250;
    private static final int RANGE = 1;

    public ExplosiveTrap(int id, Tile tile, Player user){
        super(id, tile, user);
    }

    @Override
    public void toggle(GameObject actor){
        super.toggle(actor);

        LinkedList<Tile> tiles = getTile().getNeighbors(RANGE, true);

        World.instance().actionQueue.add(
                new ShowFullCardAction(new Card(id)));
        for(int i=0; i<tiles.size(); i++){
            World.instance().actionQueue.add(
                    new BombAction(tiles.get(i), DAMAGE));
        }
        user.removeTrap(this);
    }
}
