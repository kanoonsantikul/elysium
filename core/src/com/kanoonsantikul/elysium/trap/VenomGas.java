package com.kanoonsantikul.elysium;

public class VenomGas extends Trap{
    private static final int LOCK_TURN = 4;
    private static final int DAMAGE = 100;

    private Player actor;
    private int turnCount = 0;

    public VenomGas(int id, Tile tile, Player user){
        super(id, tile, user);
    }

    @Override
    public void onTurnStart(Player player){
        if(isToggled && player == actor){
            setVisible(false);
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
