package com.kanoonsantikul.elysium;

public class ShowFullCardAction extends DelayAction{
    private static final long DELAY_MILLI = 1000;

    private World world = World.instance();

    private Card card;

    public ShowFullCardAction(Card card){
        super(DELAY_MILLI);

        this.card = card;
    }

    public void enter(){
        world.fullCard = new FullCard(card);
    }

    public void exit(){
        world.fullCard = null;
    }
}
