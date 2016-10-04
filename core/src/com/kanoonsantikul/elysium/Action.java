package com.kanoonsantikul.elysium;

public class Action{
    private GameObject actor;
    private GameObject target;

    private boolean acted;

    public Action(GameObject actor, GameObject target){
        this.actor = actor;
        this.target = target;
    }

    public void act(){
    }

    public void setActed(boolean acted){
        this.acted = acted;
    }
}
