package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class MoveBoardObjectAction extends Action {
    private LinkedList<Tile> paths;

    public MoveBoardObjectAction (BoardObject object, LinkedList<Tile> paths) {
        super(object);
        this.paths = paths;

        MultiplayerUpdater.instance().sendLocationUpdate(object, paths);
    }

    @Override
    public void act () {
        Vector2 initial = actor.getCenter();
        Vector2 target;

        for (int i = 0; i < paths.size(); i++) {
            if (i > 0) {
                initial = paths.get(i - 1).getCenter();
            }
            target = paths.get(i).getCenter();
            World.instance().actionQueue.add(new MoveAction(
                    actor,
                    initial,
                    target));
        }

        if (actor instanceof Player) {
            World.instance().actionQueue.add(new ToggleTrapAction(
                    (Player)actor,
                    paths.get(paths.size() - 1)));
        }

        setActed(true);
    }

}
