package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;

public class Card extends GameObject{
    public static final float WIDTH = Assets.card.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.card.getHeight() * Elysium.DEVICE_RATIO;

    private static float INIT_X = 9f;
    private static float INIT_Y = 13f;

    private int number;

    public Card(int number){
        float y = INIT_Y;
        float x = INIT_X + (WIDTH + 5)* number;

        setPosition(new Vector2(x, y));
        this.number = number;
    }

    public float getWidth(){
        return WIDTH;
    }

    public float getHeight(){
        return HEIGHT;
    }

    public int getNumber(){
        return number;
    }

}
