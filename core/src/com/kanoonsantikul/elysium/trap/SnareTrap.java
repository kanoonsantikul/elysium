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
            setVisible(false);
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
        if(isToggled){
            return;
        }
        super.toggle(actor);
        this.actor = (Player)actor;

        LinkedList<Tile> path = new LinkedList<Tile>();
        int row = Tile.getRowOf(getTile().getNumber());
        int collum = Tile.getCollumOf(getTile().getNumber());
        for(int i=1; i<=MOVE_RANGE; i++){
            if(row + i < World.BOARD_HEIGHT){
                path.add(World.instance()
                        .tiles.get(Tile.getNumberOf(row + i, collum)));
            }
        }

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        World.instance().actionQueue.add(
                new MoveAction((Player)actor, path, World.instance().actionQueue));
        setVisible(false);
    }
}
