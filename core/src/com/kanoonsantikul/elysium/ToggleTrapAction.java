package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public class ToggleTrapAction extends Action{
    public ToggleTrapAction(Player player, Tile tile){
        super(player);

        Trap trap = (Trap)World.getObjectAt(tile.getCenter(), Trap.class);
        if(trap != null){
            World.player1.removeTrap(trap);
            World.player2.removeTrap(trap);
            addActions(getAction(trap.getId()));
        }
        setActed(true);
    }

    private Action[] getAction(int trapId){
        Action[] actions = null;
        switch(trapId){
            case 1:
                actions = new Action[]{new LockMoveAction(2, (Player)actor)};
                break;
        }
        return actions;
    }

    private void addActions(Action[] actions){
        Action action;
        for(int i=0; i<actions.length; i++){
            action = actions[i];
            if(action instanceof TurnTrackAction){
                World.turnManager.turnTrackActionPool.add(action);
            } else{
                World.actionQueue.add(action);
            }
        }
    }

}
