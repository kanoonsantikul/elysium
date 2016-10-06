package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class MoveAction extends Action{
    private LinkedList<Tile> paths;
    private Vector2 currentTargetPosition;

    private final float SPEED = 3;
    private float speedX;
    private float speedY;

    public MoveAction(GameObject actor, LinkedList<Tile> paths){
        super(actor);

        this.paths = paths;
    }

    @Override
    public void act(){
        if(paths.size() > 0){
            if(currentTargetPosition == null){
                prepareNextMove();
            } else{
                if(!move()){
                    currentTargetPosition = null;
                    paths.poll();
                }
            }
        } else{
            setActed(true);
        }
    }

    private void prepareNextMove(){
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
        boolean reachedX = !((speedX > 0 && actor.getCenter().x < currentTargetPosition.x) ||
                (speedX < 0 && actor.getCenter().x > currentTargetPosition.x));
        boolean reachedY = !((speedY > 0 && actor.getCenter().y < currentTargetPosition.y) ||
                (speedY < 0 && actor.getCenter().y > currentTargetPosition.y));

        if(!reachedX){
            actor.setCenter(new Vector2(actor.getCenter().x + speedX, actor.getCenter().y));
        } else{
            actor.setCenter(new Vector2(currentTargetPosition.x, actor.getCenter().y));
        }
        if(!reachedY){
            actor.setCenter(new Vector2(actor.getCenter().x, actor.getCenter().y + speedY));
        } else{
            actor.setCenter(new Vector2(actor.getCenter().x, currentTargetPosition.y));
        }

        return !reachedX || !reachedY;
    }
}
