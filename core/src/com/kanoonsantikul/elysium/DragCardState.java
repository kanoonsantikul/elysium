package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class DragCardState implements WorldState{
    private World world;
    private Card card;
    private Trap trapInstance;
    private Tile lastFocusTile;

    @Override
    public void enterState(World world){
        this.world = world;
        this.card = (Card)world.mouseFocus;
        this.trapInstance = world.trapInstance;
    }

    @Override
    public void handleInput(float x, float y){
        getTargetTile();

        GameObject mouseOver = world.getObjectAt(x, y ,Tile.class);
        if(mouseOver instanceof Tile){
            if(world.targetTiles != null
                    && world.targetTiles.contains(mouseOver)
                    && world.getObjectAt(mouseOver.getCenter(), Trap.class, false) == null){
                lastFocusTile = (Tile)mouseOver;
                card.setVisible(false);
                trapInstance.setId(card.getId());
                trapInstance.setCenter(mouseOver.getCenter());
            }
        } else{
            card.setVisible(true);
            trapInstance.setId(0);
            lastFocusTile = null;
        }

        card.setCenter(new Vector2(x, y));
    }

    @Override
    public void update(){

    }

    @Override
    public void exitState(){
        if(lastFocusTile != null){
            Trap trap = TrapBuilder.build(card.getId(), lastFocusTile, world.player);
            world.player.addTrap(trap);
            world.player.removeCard(card);

        } else{
            card.setVisible(true);
            card.positioning();
        }

        trapInstance.setId(0);
        world.targetTiles = null;
    }

    private void getTargetTile(){
        if(world.targetTiles == null && !world.player.isOnAction()){
            Vector2 position = world.player.getCenter();
            Tile tile = (Tile)world.getObjectAt(position, Tile.class, false);
            world.targetTiles = tile.getNeighbors(world.player.getTrapRange(), true);

            for(int i=0; i<world.targetTiles.size(); i++){
                tile = world.targetTiles.get(i);
                if(world.getObjectAt(tile.getCenter(), Trap.class, false) != null){
                    world.targetTiles.remove(i);
                }
            }
        }
    }

}
