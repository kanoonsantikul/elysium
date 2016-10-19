package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public abstract class TurnTrackAction extends Action{
    public abstract void update();

    private int turnLimit;
    private int turnCount;

    public TurnTrackAction(int turnLimit, GameObject object){
        super(object);

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
            setActed(true);
        }
    }
}
