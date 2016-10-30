package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
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
        if(world.isMyTurn){
            getTargetTile();
        }

        GameObject mouseOver = world.getObjectAt(x, y ,Tile.class);
        if(world.isMyTurn && mouseOver instanceof Tile){
            if(world.targetTiles != null
                    && world.targetTiles.contains(mouseOver)){
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
    public void exitState(){
        if(lastFocusTile != null){
            Trap trap = TrapBuilder.build(card.getId(), lastFocusTile, world.player);
            world.player.addTrap(trap);
            world.player.removeCard(card);

            MultiplayerUpdater.instance().sendTrapUpdate(trap);

        } else{
            card.setVisible(true);
            card.positioning();
        }

        trapInstance.setId(0);
        world.targetTiles = null;
    }

    private void getTargetTile(){
        if(world.targetTiles == null && !world.player.isLock()){
            world.targetTiles = new LinkedList<Tile>();
            Vector2 position = world.player.getCenter();
            Tile tile = (Tile)world.getObjectAt(position, Tile.class, false);
            LinkedList<Tile> neighborTiles =
                    tile.getNeighbors(world.player.getTrapRange(), true);

            for(int i=0; i<neighborTiles.size(); i++){
                tile = neighborTiles.get(i);
                if(world.getObjectAt(tile.getCenter(), Trap.class, false) == null){
                    world.targetTiles.add(tile);
                }
            }
        }
    }

}
