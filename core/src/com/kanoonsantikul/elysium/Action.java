package com.kanoonsantikul.elysium;

public class Action{
    protected GameObject actor;

    protected boolean acted;

    public Action(GameObject actor){
        this.actor = actor;
        setActed(false);
    }

    public void act(){
    }

    public void setActed(boolean acted){
        this.acted = acted;
    }

    public boolean isActed(){
        return acted;
    }
}
