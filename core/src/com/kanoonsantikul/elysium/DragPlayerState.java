package com.kanoonsantikul.elysium;

import java.util.List;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class DragPlayerState implements WorldState {
    private World world;
    private Player player;

    private LinkedList<Tile> pathTracker;

    public DragPlayerState (World world) {
        this.world = world;
        player = world.player;
    }

    @Override
    public void enterState () {

    }

    @Override
    public void exitState () {

    }

    @Override
    public void onClicked (float x, float y) {

    }

    @Override
    public void onPressed (float x, float y) {

    }

    @Override
    public void onDragStart (float x, float y) {
        pathTracker = new LinkedList<Tile>();
        world.pathTracker = pathTracker;

        if (!world.isMyTurn || player.isLock || player.isMoved) {
            world.setState(world.handleState);
        }
    }

    @Override
    public void onDragEnd (float x, float y) {
        if (pathTracker.size() > 0) {
            world.actionQueue.add(new MoveBoardObjectAction(player, pathTracker));
        }

        world.pathTracker = null;
        world.setState(world.handleState);
    }

    @Override
    public void onDragged (float x, float y) {
        GameObject mouseOver = world.getObjectAt(x, y, Tile.class);
        if (mouseOver != null) {
            mouseOver = world.getObjectAt(mouseOver.getCenter(), null, false);

            if (mouseOver instanceof Tile) {
                updatePath((Tile)mouseOver);
            } else if (mouseOver == player) {
                pathTracker.clear();
            }

        }
    }

    private void updatePath (Tile tile) {
        if (tile == null) {
            return;
        }

        if (!pathTracker.contains(tile)) {
            Tile lastTile;
            if (pathTracker.size() == 0) {
                lastTile = player.getTile();
            } else {
                lastTile = pathTracker.getLast();
            }

            if (lastTile.getPlusNeighbors(1).contains(tile)
                    && pathTracker.size() < player.moveRange) {
                pathTracker.add(tile);
            }
        } else {
            int listPosition = pathTracker.indexOf(tile);
            List subPath = pathTracker.subList(0, listPosition + 1);
            pathTracker = new LinkedList(subPath);
            world.pathTracker = pathTracker;
        }
    }

}
