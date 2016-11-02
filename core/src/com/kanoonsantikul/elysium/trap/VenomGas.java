package com.kanoonsantikul.elysium;

public class VenomGas extends Trap{
    public static final int ID = 7;
    public static final float WEIGHT = 0.2f;
    public static final int COST = 2;
    private static final int LOCK_TURN = 4;
    private static final int DAMAGE = 100;

    private Player actor;
    private int turnCount = 0;

    public VenomGas(Tile tile, Player user){
        super(ID, WEIGHT, COST, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(isToggled && player == actor){
            if(turnCount < LOCK_TURN){
                World.instance().actionQueue.add(
                        new DamageAction(actor.getTile(), DAMAGE));
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
