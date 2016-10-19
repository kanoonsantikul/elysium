package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public class ToggleTrapAction extends Action{
    private World world = World.instance();

    private Tile tile;

    public ToggleTrapAction(Player player, Tile tile){
        super(player);

        this.tile = tile;
    }

    @Override
    public void act(){
        for(int i=0; i<2; i++){
            Trap trap = (Trap)world.getObjectAt(tile.getCenter(), Trap.class, true);
            if(trap != null){
                world.actionQueue.add(
                    new ShowFullCardAction(new Card(trap.getId())));
                world.player1.removeTrap(trap);
                world.player2.removeTrap(trap);
                addActions(getAction(trap.getId()));
            }
        }
        setActed(true);
    }

    private Action[] getAction(int trapId){
        Action[] actions = null;

        if(trapId == 1){
            actions = new Action[]{new LockMoveAction(2, (Player)actor)};
        } else if(trapId == 2){
            actions = new Action[]{new BombAction(tile, 200)};
        }

        return actions;
    }

    private void addActions(Action[] actions){
        Action action;
        for(int i=0; i<actions.length; i++){
            action = actions[i];
            if(action instanceof TurnTrackAction){
                world.turnManager.turnTrackActionPool.add(action);
            } else{
                world.actionQueue.add(action);
            }
        }
    }

}
