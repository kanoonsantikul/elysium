package com.kanoonsantikul.elysium;

public class Tile{
    public static final float WIDTH = Assets.tile.getWidth() * World.DEVICE_RATIO;
    public static final float HEIGHT = Assets.tile.getHeight() * World.DEVICE_RATIO;

    private float x;
    private float y;

    public Tile(float x, float y){
        setX(x);
        setY(y);
    }

    public void setX(float x){
        this.x = x;
    }

    public float getX(){
        return x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getY(){
        return y;
    }

    public float getCenterX(){
        return x += WIDTH/2;
    }

    public float getCenterY(){
        return y += HEIGHT/2;
    }
}
