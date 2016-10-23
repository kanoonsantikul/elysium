package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public class ToggleTrapAction extends Action{
    private World world = World.instance();

    private Tile tile;
    private Trap trap;

    public ToggleTrapAction(Player actor, Tile tile){
        super(actor);

        this.tile = tile;
    }

    @Override
    public void act(){
        getTrap(world.player1);
        getTrap(world.player2);
        setActed(true);
    }

    private void getTrap(Player player){
        trap = null;
        for(int i=0; i<player.getTraps().size(); i++){
            trap = player.getTraps().get(i);
            if(!trap.getCenter().equals(tile.getCenter())){
                trap = null;
            }
        }
        if(trap != null){
            Gdx.app.log("","toggle");
            trap.toggle(actor);
        }
    }
}
