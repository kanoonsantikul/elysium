package com.kanoonsantikul.elysium;

public class ShowFullCardAction extends DelayAction {
    private static final long DELAY_MILLI = 2200;

    private World world = World.instance();
    private int cardId;

    public ShowFullCardAction (int cardId) {
        super(DELAY_MILLI);
        this.cardId = cardId;
    }

    public void enter () {
        world.fullCard.setCardId(cardId, FullCard.AUTO_SHOW_TYPE);
    }

    public void exit () {
        world.fullCard.setCardId(FullCard.NULL_CARD, FullCard.AUTO_SHOW_TYPE);
    }
}
