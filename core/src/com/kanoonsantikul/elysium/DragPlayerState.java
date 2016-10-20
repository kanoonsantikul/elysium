package com.kanoonsantikul.elysium;

import java.util.List;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class DragPlayerState implements WorldState{
    private World world;
    private Player player;
    private LinkedList<Tile> pathTracker;

    @Override
    public void enterState(World world){
        this.world = world;
        player = world.player;
        pathTracker = new LinkedList<Tile>();
        world.pathTracker = pathTracker;

        if(player.isOnAction() || player.getIsMoved()){
            world.state = null;
        }
    }

    @Override
    public void handleInput(float x, float y){
        GameObject object = world.getObjectAt(x, y, null);
        if(object instanceof Tile){
            updatePath((Tile)object);
        } else if(object == player){
            pathTracker.clear();
        }
    }

    @Override
    public void update(){

    }

    @Override
    public void exitState(){
        world.actionQueue.addLast(new MoveAction(
                player,
                pathTracker,
                world.actionQueue));
    }

    private void updatePath(Tile tile){
        if(tile == null){
            return;
        }

        if(!pathTracker.contains(tile)){
            Tile lastTile;
            if(pathTracker.size() == 0){
                lastTile = player.getTile();
            } else{
                lastTile = pathTracker.getLast();
            }

            if(lastTile.getNeighbors(1, false).contains(tile)
                    && pathTracker.size() < player.getMoveRange()){
                pathTracker.add(tile);
            }
        } else{
            int listPosition = pathTracker.indexOf(tile);
            List subPath = pathTracker.subList(0, listPosition + 1);
            pathTracker = new LinkedList(subPath);
            world.pathTracker = pathTracker;
        }
    }

}
