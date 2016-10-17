package com.kanoonsantikul.elysium;

import java.util.LinkedList;

public class ToggleTrapAction extends Action{
    public ToggleTrapAction(Action[] trapActions, LinkedList<Action> actionQueue){
        super(null);

        addAction(trapActions, actionQueue);
        this.setActed(true);
    }

    private void addAction(Action[] trapActions, LinkedList<Action> actionQueue){
        for(int i=0; i<trapActions.length; i++){
            actionQueue.add(trapActions[i]);
        }
    }
}
