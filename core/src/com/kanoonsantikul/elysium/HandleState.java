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
        world.fullCard.setCardId(FullCard.NULL_CARD, FullCard.AUTO_SHOW_TYPE);

        GameObject object = world.getObjectAt(x, y, null);
        if (object instanceof EndTurnButton && world.isMyTurn) {
            world.turnManager.endTurn();
        }
    }

    @Override
    public void onPressed (float x, float y) {
        GameObject object = world.getObjectAt(x, y, null);
        if (object instanceof Card) {
            world.fullCard.setCardId(((Card)object).getId(), FullCard.PRESSED_SHOW_TYPE);

        } else if ((object = world.getObjectAt(x, y, Trap.class)) != null) {
            world.fullCard.setCardId(((Trap)object).getId(), FullCard.PRESSED_SHOW_TYPE);

        }
    }

    @Override
    public void onDragStart (float x, float y) {
        world.fullCard.setCardId(FullCard.NULL_CARD, FullCard.AUTO_SHOW_TYPE);

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

    @Override
    public void update () {
        
    }
}
