package com.kanoonsantikul.elysium;

import java.util.List;
import java.util.LinkedList;

public class SnareTrap extends Trap{
    private static final int LOCK_TURN = 1;
    private static final int MOVE_RANGE = 2;

    private Player actor;
    private int turnCount = 0;

    public SnareTrap(int id, Tile tile, Player user){
        super(id, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(isToggled && player == actor){
            if(turnCount < LOCK_TURN){
                actor.setIsMoved(true);
                turnCount++;
            } else{
                user.removeTrap(this);
            }
        }
    }

    @Override
    public void toggle(GameObject actor){
        super.toggle(actor);
        this.actor = (Player)actor;

        LinkedList<Tile> path = getTile().getNeighbors(MOVE_RANGE, false);
        List subPath = path.subList(0, MOVE_RANGE);
        path = new LinkedList(subPath);

        World.instance().actionQueue.add(
                new ShowFullCardAction(new Card(id)));
        World.instance().actionQueue.add(
                new MoveAction((Player)actor, path, World.instance().actionQueue));
        setVisible(false);
    }
}
