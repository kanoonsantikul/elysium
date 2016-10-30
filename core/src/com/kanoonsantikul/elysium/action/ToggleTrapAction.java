package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public class ToggleTrapAction extends Action{
    private World world = World.instance();

    private Tile tile;

    public ToggleTrapAction(Player actor, Tile tile){
        super(actor);

        this.tile = tile;
    }

    @Override
    public void act(){
        getTrap(world.player);
        getTrap(world.enemy);
        setActed(true);
    }

    private void getTrap(Player player){
        Trap trap = null;
        for(int i=0; i<player.getTraps().size(); i++){
            trap = player.getTraps().get(i);
            if(trap.getCenter().equals(tile.getCenter())){
                break;
            }
            trap = null;
        }
        if(trap != null){
            trap.toggle(actor);
        }
    }
}
