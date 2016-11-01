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
        if(world.player.getNumber() == Player.PLAYER1){
            getTrap(world.player);
            getTrap(world.enemy);
        } else{
            getTrap(world.enemy);
            getTrap(world.player);
        }
        setActed(true);
    }

    private void getTrap(Player player){
        for(int i=0; i<player.getTraps().size(); i++){
            if(player.getTraps().get(i).getTile() == tile){
                player.getTraps().get(i).toggle(actor);
                break;
            }
        }
    }
}
