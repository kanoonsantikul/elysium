package com.kanoonsantikul.elysium;

public class TickingTimeBomb extends Trap{
    public static final int ID = 3;
    public static final float WEIGHT = 0.4f;
    public static final int COST = 1;
    private static final int LOCK_TURN = 0;
    private static final int DAMAGE = 250;

    private int turnCount = 0;

    public TickingTimeBomb(Tile tile, Player user){
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(player == user){
            if(turnCount < LOCK_TURN){
                turnCount++;
            } else{
                World.instance().actionQueue.add(
                        new ShowFullCardAction(id));
                World.instance().actionQueue.add(
                        new DamageAction(getTile(), DAMAGE));
                user.removeTrap(this);
            }
        }
    }

    @Override
    public void toggle(GameObject actor){
    }
}
