package com.kanoonsantikul.elysium;

public interface WorldState{
    public void enterState(World world);
    public void handleInput(float x, float y);
    public void exitState();
}
