package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public class TickingTimeBomb extends Trap{
    private static final int LOCK_TURN = 2;
    private static final int DAMAGE = 200;

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
