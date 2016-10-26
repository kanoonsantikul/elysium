package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class MoveAction extends Action{

    private LinkedList<Tile> paths;
    private LinkedList<Action> actionQueue;
    private Vector2 currentTargetPosition;

    private final float SPEED = 3;
    private float speedX;
    private float speedY;

    private Tile lastTile;

    public MoveAction(BoardObject actor,
            LinkedList<Tile> paths,
            LinkedList<Action> actionQueue){
        super(actor);

        this.paths = paths;
        this.actionQueue = actionQueue;
        lastTile = actor.getTile();
    }

    @Override
    public void act(){
        if(!actor.isOnAction()){
            preAction();
        }

        if(paths.size() > 0){
            if(currentTargetPosition == null){
                changeDirection();
            } else if(!move()){
                currentTargetPosition = null;
                lastTile = paths.poll();
            }
        } else{
            actor.setOnAction(false);
            ((BoardObject)actor).setTile(lastTile);
            if(actor instanceof Player){
                ((Player)actor).setIsMoved(true);
                if(actionQueue != null){
                    actionQueue.add(
                            new ToggleTrapAction((Player)actor, lastTile));
                }
            }
            setActed(true);
        }
    }

    private void preAction(){
        actor.setOnAction(true);
        if(((BoardObject)actor).getTile() != lastTile){
            paths.clear();
            lastTile = ((BoardObject)actor).getTile();
            actionQueue = null;
        }
    }

    private void changeDirection(){
        currentTargetPosition = paths.peek().getCenter();
        speedX = SPEED;
        speedY = SPEED;
        if(actor.getPosition().x > currentTargetPosition.x){
            speedX *= -1;
        }
        if(actor.getPosition().y > currentTargetPosition.y){
            speedY *= -1;
        }
    }

    private boolean move(){
        boolean reachedX = !(
                (speedX > 0 && actor.getCenter().x < currentTargetPosition.x) ||
                (speedX < 0 && actor.getCenter().x > currentTargetPosition.x));
        boolean reachedY = !(
                (speedY > 0 && actor.getCenter().y < currentTargetPosition.y) ||
                (speedY < 0 && actor.getCenter().y > currentTargetPosition.y));

        if(!reachedX){
            actor.setCenter(new Vector2(
                    actor.getCenter().x + speedX,
                    actor.getCenter().y));
        } else{
            actor.setCenter(new Vector2(
                    currentTargetPosition.x,
                    actor.getCenter().y));
        }
        if(!reachedY){
            actor.setCenter(new Vector2(
                    actor.getCenter().x,
                    actor.getCenter().y + speedY));
        } else{
            actor.setCenter(new Vector2(
                    actor.getCenter().x,
                    currentTargetPosition.y));
        }

        return !reachedX || !reachedY;
    }
}
