package com.kanoonsantikul.elysium;

import java.util.LinkedList;

public class FuzzyBomb extends Trap implements WaitDataAction.DataListener {
    public static final int ID = 6;
    public static final float WEIGHT = 0.1f;
    public static final int COST = 2;
    private static final int LOCK_TURN = 3;
    private World world = World.instance();

    private Player actor;
    private int turnCount = 0;

    public FuzzyBomb (Tile tile, Player user) {
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void onDataArrive (String data) {
        LinkedList<Tile> paths = new LinkedList<Tile>();
        String[] pathString = data.split(":");
        for (int i = 0; i < pathString.length; i++) {
            int number = Integer.parseInt(pathString[i]);
            paths.add(world.tiles.get(number));
        }

        world.actionQueue.add(new MoveAction(
                actor,
                paths,
                world.actionQueue));
    }

    @Override
    public void onTurnStart (Player player) {
        if (isToggled && player == actor && actor == world.player) {
            if (turnCount < LOCK_TURN) {
                LinkedList<Tile> paths = createPath();
                String data = "";
                for(int i = 0; i < paths.size(); i++){
                    data += paths.get(i).getNumber() + ":";
                }
                MultiplayerUpdater.instance().sendDataUpdate(data);

                actor.setIsMoved(true);
                world.actionQueue.add(new MoveAction(
                        player,
                        paths,
                        world.actionQueue));
                turnCount++;
            } else {
                user.removeTrap(this);
            }
        }
    }

    @Override
    public void onTurnEnd (Player player) {
        if (isToggled && player != actor && actor == world.enemy) {
            if (turnCount < LOCK_TURN) {
                world.actionQueue.add(new WaitDataAction(this));
                turnCount++;
            } else {
                user.removeTrap(this);
            }
        }
    }

    @Override
    public void toggle (GameObject actor) {
        if (isToggled) {
            return;
        }
        super.toggle(actor);
        this.actor = (Player)actor;

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        setVisible(false);
    }

    public LinkedList<Tile> createPath () {
        LinkedList<Tile> paths= new LinkedList<Tile>();

        int maxRange;
        do{
            maxRange = world.random.nextInt(actor.getMoveRange() + 1);
        }while(maxRange == 0);

        Tile lastTile = actor.getTile();
        Tile tile;
        LinkedList<Tile> neighbors;
        for (int i = 0; i < maxRange; i++) {
            do {
                neighbors = lastTile.getNeighbors(1, Tile.PLUS_RANGE);
                tile = neighbors.get(world.random.nextInt(neighbors.size()));
            } while (paths.contains(tile)
                    || world.getObjectAt(tile.getCenter(), Player.class, false) != null);
            lastTile = tile;
            paths.add(tile);
        }

        return paths;
    }
}
