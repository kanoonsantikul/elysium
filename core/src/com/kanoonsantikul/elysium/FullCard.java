package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class FullCard extends GameObject{
    public static final float WIDTH = Assets.fullCards[0].getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.fullCards[0].getHeight() * Elysium.DEVICE_RATIO;

    public static final float AUTO_SHOW_X = Elysium.WIDTH - WIDTH - 10;
    public static final float AUTO_SHOW_Y = Elysium.HEIGHT - HEIGHT - 20;

    public static final float PRESSED_SHOW_X = Elysium.WIDTH / 2 - WIDTH / 2;
    public static final float PRESSED_SHOW_Y = Elysium.HEIGHT / 2 - HEIGHT / 2 + 60;

    public static final int NULL_CARD = -1;

    private int cardId;

    public FullCard(){
        setCardId(NULL_CARD);
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }

    public int getCardId(){
        return cardId;
    }

    public void setCardId(int cardId){
        this.cardId = cardId;
    }
}
