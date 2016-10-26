package com.kanoonsantikul.elysium;

import java.util.LinkedList;

public class FuzzyTrap extends Trap{
    private static final int LOCK_TURN = 3;
    private World world = World.instance();

    private Player actor;
    private int turnCount = 0;

    public FuzzyTrap(int id, Tile tile, Player user){
        super(id, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(isToggled && player == actor){
            setVisible(false);
            if(turnCount < LOCK_TURN){
                actor.setIsMoved(true);
                world.actionQueue.add(new MoveAction(
                        player,
                        createPath(),
                        world.actionQueue));
                turnCount++;
            } else{
                user.removeTrap(this);
            }
        }
    }

    @Override
    public void toggle(GameObject actor){
        if(isToggled){
            return;
        }
        super.toggle(actor);
        this.actor = (Player)actor;

        world.actionQueue.add(
                new ShowFullCardAction(new Card(id)));
        setVisible(false);
    }

    public LinkedList<Tile> createPath(){
        LinkedList<Tile> paths= new LinkedList<Tile>();

        int maxRange;
        do{
            maxRange = world.random.nextInt(actor.getMoveRange() + 1);
        }while(maxRange == 0);

        Tile lastTile = actor.getTile();
        Tile tile;
        LinkedList<Tile> neighbors;
        for(int i=0; i<maxRange; i++){
            do{
                neighbors = lastTile.getNeighbors(1, false);
                tile = neighbors.get(world.random.nextInt(neighbors.size()));
            }while(paths.contains(tile));
            lastTile = tile;
            paths.add(tile);
        }

        return paths;
    }
}
