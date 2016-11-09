package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class DragCardState implements WorldState {
    private World world;

    private Card card;
    private Tile lastFocusTile;

    public DragCardState (World world) {
        this.world = world;
    }

    @Override
    public void enterState () {
        this.card = (Card)world.mouseFocus;
        card.setIsMoving(true);
    }

    @Override
    public void handleInput (float x, float y) {
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
                world.actionQueue.add(new ShowTextAction(
                        ShowTextAction.NOTIFY_NO_MATERIAL_TEXT));
            }
        } else {
            card.setVisible(true);
            card.positioning();
        }

        card.setIsMoving(false);
        world.targetTiles.clear();
    }
}
