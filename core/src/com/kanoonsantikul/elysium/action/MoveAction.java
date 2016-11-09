package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class MoveAction extends Action {
    private final float SPEED = 3;
    private float speedX;
    private float speedY;

    private Vector2 initial;
    private Vector2 target;

    public MoveAction (GameObject actor,
            Vector2 initial,
            Vector2 target) {
        super(actor);
        this.initial = initial;
        this. target = target;
    }

    @Override
    public void act () {
        if (!actor.isLock()) {
            if (!validatePosition()) {
                return;
            } else {
                obtainDirection();
            }
        }

        if (!move()) {
            actor.setLock(false);

            if (actor instanceof BoardObject) {
                Tile tile = (Tile)World.instance().getObjectAt(actor.getCenter(), Tile.class, false);
                ((BoardObject)actor).setTile(tile);
            }
            if (actor instanceof Player) {
                ((Player)actor).setIsMoved(true);
                ((Player)actor).addMaterial(1);
            }

            setActed(true);
        }
    }

    private boolean validatePosition () {
        actor.setLock(true);
        if (!actor.getCenter().equals(initial) || actor == null) {
            setActed(true);
            return false;
        } else {
            return true;
        }
    }

    private void obtainDirection () {
        speedX = SPEED;
        speedY = SPEED;
        if (initial.x > target.x) {
            speedX *= -1;
        }
        if (initial.y > target.y) {
            speedY *= -1;
        }
    }

    private boolean move () {
        boolean reachedX = !(
                (speedX > 0 && actor.getCenter().x < target.x) ||
                (speedX < 0 && actor.getCenter().x > target.x));
        boolean reachedY = !(
                (speedY > 0 && actor.getCenter().y < target.y) ||
                (speedY < 0 && actor.getCenter().y > target.y));

        if (!reachedX) {
            actor.setCenter(new Vector2(
                    actor.getCenter().x + speedX,
                    actor.getCenter().y));
        } else {
            actor.setCenter(new Vector2(
                    target.x,
                    actor.getCenter().y));
        }

        if (!reachedY) {
            actor.setCenter(new Vector2(
                    actor.getCenter().x,
                    actor.getCenter().y + speedY));
        } else {
            actor.setCenter(new Vector2(
                    actor.getCenter().x,
                    target.y));
        }

        return !reachedX || !reachedY;
    }
}
