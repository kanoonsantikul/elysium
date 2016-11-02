package com.kanoonsantikul.elysium;

public class BearTrap extends Trap{
    public static final int ID = 1;
    public static final float WEIGHT = 0.07f;
    public static final int COST = 3;
    private static final int LOCK_TURN = 2;

    private Player actor;
    private int turnCount = 0;

    public BearTrap(Tile tile, Player user){
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(isToggled && player == actor){
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
        setVisible(false);
    }
}
