package com.kanoonsantikul.elysium;

public class NeedleTrap extends Trap{
    private static final int LOCK_TURN = 1;
    private static final int DAMAGE = 400;

    private Player actor;
    private int turnCount = 0;

    public NeedleTrap(int id, Tile tile, Player user){
        super(id, tile, user);
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
                new ShowFullCardAction(new Card(id)));
        World.instance().actionQueue.add(
                new DamageAction(getTile(), DAMAGE));
        setVisible(false);
    }
}
