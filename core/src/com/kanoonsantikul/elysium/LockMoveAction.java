package com.kanoonsantikul.elysium;

public abstract class LockMoveAction extends TurnTrackAction{
    private Player player;

    public LockMoveAction(int turnLimit, Player player){
        super(turnLimit);
        this.player = player;
    }

    @Override
    public void update(){
        player.setIsMoved(true);
    }
}
