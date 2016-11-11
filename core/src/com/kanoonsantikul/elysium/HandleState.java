package com.kanoonsantikul.elysium;

public class HandleState implements WorldState {
    World world;

    public HandleState (World world) {
        this.world = world;
    }

    @Override
    public void enterState () {

    }

    @Override
    public void exitState () {

    }

    @Override
    public void onClicked (float x, float y) {
        GameObject object = world.getObjectAt(x, y, null);
        if (object instanceof EndTurnButton && world.isMyTurn) {
            world.turnManager.endTurn();
        }
    }

    @Override
    public void onPressed (float x, float y) {

    }

    @Override
    public void onDragStart (float x, float y) {
        GameObject object = world.getObjectAt(x, y, null);

        if (object == world.player) {
            world.setState(world.dragPlayerState);
            world.dragPlayerState.onDragStart(x, y);
        } else if (object instanceof Card) {
            world.setState(world.dragCardState);
            world.dragCardState.onDragStart(x, y);
        }
    }

    @Override
    public void onDragEnd (float x, float y) {

    }

    @Override
    public void onDragged (float x, float y) {

    }

}
