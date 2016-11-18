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

    }

    @Override
    public void exitState() {

    }

    @Override
    public void onClicked (float x, float y) {

    }

    @Override
    public void onPressed (float x, float y) {

    }

    @Override
    public void onDragStart (float x, float y) {
        this.card = (Card)world.getObjectAt(x, y, Card.class);
        card.isMoving = true;
    }

    @Override
    public void onDragEnd (float x, float y) {
        if (lastFocusTile != null) {
            Trap trap = TrapBuilder.getInstance(card.getId());
            if (trap.getCost() <= world.player.material) {
                trap = TrapBuilder.build(card.getId(), lastFocusTile, world.player);
                world.player.addTrap(trap);
                world.player.removeCard(card);
                MultiplayerUpdater.instance().sendTrapUpdate(trap);

            } else {
                card.isVisible = true;
                card.positioning();
                world.actionQueue.add(new ShowTextAction(
                        ShowTextAction.NOTIFY_NO_MATERIAL_TEXT));
            }
        } else {
            card.isVisible = true;
            card.positioning();
        }

        card.isMoving = false;
        world.targetTiles.clear();
        world.setState(world.handleState);
    }

    @Override
    public void onDragged (float x, float y) {
        GameObject mouseOver = world.getObjectAt(x, y ,Tile.class);
        if (world.isMyTurn && mouseOver instanceof Tile) {
            if (world.targetTiles.contains(mouseOver)) {
                lastFocusTile = (Tile)mouseOver;
                card.isVisible = true;
                TrapBuilder.getInstance(card.getId())
                        .setCenter(lastFocusTile.getCenter());
            }
        } else {
            card.isVisible = true;
            lastFocusTile = null;
        }

        card.setCenter(new Vector2(x, y));
    }

    @Override
    public void update () {

    }
}
