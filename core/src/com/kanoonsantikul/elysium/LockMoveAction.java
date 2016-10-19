package com.kanoonsantikul.elysium;

public class LockMoveAction extends TurnTrackAction{
    public LockMoveAction(int turnLimit, Player player){
        super(turnLimit, player);
    }

    @Override
    public void update(){
        ((Player)actor).setIsMoved(true);
    }
}
