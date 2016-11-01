package com.kanoonsantikul.elysium;

public class SpikeTrap extends Trap{
    public static final int ID = 8;
    public static final float WEIGHT = 0.1f;
    public static final int COST = 2;
    private static final int LOCK_TURN = 1;
    private static final int DAMAGE = 300;

    private Player actor;
    private int turnCount = 0;

    public SpikeTrap(Tile tile, Player user){
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(isToggled && player == actor){
            setVisible(false);
            if(turnCount < LOCK_TURN){
                actor.setIsMoved(true);
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

        World.instance().actionQueue.add(
                new ShowFullCardAction(id));
        World.instance().actionQueue.add(
                new DamageAction(getTile(), DAMAGE));
        setVisible(false);
    }
}
