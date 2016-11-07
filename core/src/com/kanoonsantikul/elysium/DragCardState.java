package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class DragCardState implements WorldState {
    private World world;
    private Card card;
    private Tile lastFocusTile;

    @Override
    public void enterState (World world) {
        this.world = world;
        this.card = (Card)world.mouseFocus;
        card.setIsMoving(true);
    }

    @Override
    public void handleInput (float x, float y) {
        if (world.isMyTurn) {
            getTargetTile();
        }

        GameObject mouseOver = world.getObjectAt(x, y ,Tile.class);
        if(world.isMyTurn && mouseOver instanceof Tile){
            if(world.targetTiles.contains(mouseOver)){
                lastFocusTile = (Tile)mouseOver;
                card.setVisible(false);
                TrapBuilder.getInstance(card.getId())
                        .setCenter(lastFocusTile.getCenter());
            }
        } else {
            card.setVisible(true);
            lastFocusTile = null;
        }

        card.setCenter(new Vector2(x, y));
    }

    @Override
    public void exitState() {
        if (lastFocusTile != null) {
            Trap trap = TrapBuilder.getInstance(card.getId());
            if (trap.getCost() <= world.player.getMaterial()) {
                trap = TrapBuilder.build(card.getId(), lastFocusTile, world.player);
                world.player.addTrap(trap);
                world.player.removeCard(card);
                MultiplayerUpdater.instance().sendTrapUpdate(trap);
            } else {
                card.setVisible(true);
                card.positioning();
                world.actionQueue.add(
                        new ShowTextAction(ShowTextAction.NOTIFY_NO_MATERIAL_TEXT));
            }
        } else {
            card.setVisible(true);
            card.positioning();
        }
        
        card.setIsMoving(false);
        world.targetTiles.clear();
    }

    private void getTargetTile () {
        if(world.targetTiles.size() == 0 && !world.player.isLock()){
            LinkedList<Tile> neighborTiles = world.player.getTile()
                    .getNeighbors(world.player.getTrapRange(), Tile.CIRCLE_RANGE);

            Tile tile;
            for (int i = 0; i < neighborTiles.size(); i++) {
                tile = neighborTiles.get(i);
                if(world.getObjectAt(tile.getCenter(), Trap.class, false) == null){
                    world.targetTiles.add(tile);
                }
            }

        }
    }

}
