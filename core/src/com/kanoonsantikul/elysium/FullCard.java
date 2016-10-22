package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class FullCard extends GameObject{
    public static final float WIDTH = Assets.fullCards[0].getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.fullCards[0].getHeight() * Elysium.DEVICE_RATIO;

    public static final float X = Elysium.WIDTH / 2 - WIDTH /2 ;
    public static final float Y = Elysium.HEIGHT / 2 - HEIGHT /2 + 20;

    private Card card;

    public FullCard(Card card){
        this.card = card;
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }

    public Card getCard(){
        return card;
    }
}
