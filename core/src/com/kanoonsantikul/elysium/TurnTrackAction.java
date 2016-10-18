package com.kanoonsantikul.elysium;

public abstract class TurnTrackAction extends Action{
    public abstract void update();

    private int turnLimit;
    private int turnCount;

    public TurnTrackAction(int turnLimit){
        super(null);

        this.turnLimit = turnLimit;
        turnCount = 0;
    }

    @Override
    public void act(){
        update();
        checkTurnLimit();
    }

    private void checkTurnLimit(){
        turnCount++;
        if(turnCount == turnLimit){
            this.setActed(true);
        }
    }
}
