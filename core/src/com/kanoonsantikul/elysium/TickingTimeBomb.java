package com.kanoonsantikul.elysium;

public class TickingTimeBomb extends Trap{
    private static final int LOCK_TURN = 2;
    private static final int DAMAGE = 750;

    private int turnCount = 0;

    public TickingTimeBomb(int id, Tile tile, Player user){
        super(id, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(player == user){
            if(turnCount < LOCK_TURN){
                turnCount++;
            } else{
                World.instance().actionQueue.add(
                        new ShowFullCardAction(new Card(id)));
                World.instance().actionQueue.add(
                        new BombAction(getTile(), DAMAGE));
                user.removeTrap(this);
            }
        }
    }

    @Override
    public void toggle(GameObject actor){
        super.toggle(actor);
    }
}
