package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class Tile extends GameObject{
    public static final float WIDTH = Assets.tile.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.tile.getHeight() * Elysium.DEVICE_RATIO;

    public static final int RECTANGLE_RANGE = 1;
    public static final int PLUS_RANGE = 2;
    public static final int CIRCLE_RANGE = 3;

    private static float INIT_X = 7f;
    private static float INIT_Y = 140f;

    private int number;

    public Tile(int number){
        float y = INIT_Y + HEIGHT * getRowOf(number);
        float x = INIT_X + WIDTH * getCollumOf(number);

        setPosition(new Vector2(x, y));
        this.number = number;
    }

    public static int getNumberOf(int row, int collum){
        return row * World.BOARD_WIDTH + collum;
    }

    public static int getRowOf(int number){
        return number / World.BOARD_WIDTH;
    }

    public static int getCollumOf(int number){
        return number % World.BOARD_WIDTH;
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

    public LinkedList<Tile> getNeighbors(int range, int rangeType){
        LinkedList<Tile> tiles = World.instance().tiles;
        LinkedList<Tile> neighbors = new LinkedList<Tile>();
        Tile tile;
        int row, collum;

        if(rangeType == RECTANGLE_RANGE){
            for(int i=-range; i<range + 1; i++){
                for(int j=-range; j<range + 1; j++){
                    row = i + getRowOf(number);
                    collum = j + getCollumOf(number);

                    if(isHaveTile(row, collum)){
                        tile = tiles.get(getNumberOf(row, collum));
                        if(tile.isVisible()){
                            neighbors.add(tile);
                        }
                    }
                }
            }
        } else if(rangeType == PLUS_RANGE){
            for(int i=-range; i<=range; i++){
                row = i + getRowOf(number);
                collum = i + getCollumOf(number);

                if(isHaveTile(row, getCollumOf(number))){
                    tile = tiles.get(getNumberOf(row, getCollumOf(number)));
                    if(tile.isVisible()){
                        neighbors.add(tile);
                    }
                }
                if(isHaveTile(getRowOf(number), collum)){
                    tile = tiles.get(getNumberOf(getRowOf(number), collum));
                    if(tile.isVisible()){
                        neighbors.add(tile);
                    }
                }
            }
        } else if(rangeType == CIRCLE_RANGE){
            for(int i=-range; i<=0; i++){
                for(int j=-range-i; j<=range+i; j++){
                    row = i + getRowOf(number);
                    collum = j + getCollumOf(number);
                    
                    if(isHaveTile(row, collum)){
                        tile = tiles.get(getNumberOf(row, collum));
                        if(tile.isVisible()){
                            neighbors.add(tile);
                        }
                    }
                }
                Gdx.app.log("","");
            }
            for(int i=1; i<=range; i++){
                for(int j=-range+i; j<=range-i; j++){
                    row = i + getRowOf(number);
                    collum = j + getCollumOf(number);

                    if(isHaveTile(row, collum)){
                        tile = tiles.get(getNumberOf(row, collum));
                        if(tile.isVisible()){
                            neighbors.add(tile);
                        }
                    }
                }
            }
        }

        return neighbors;
    }

    private boolean isHaveTile(int row, int collum){
        return (row >= 0 && row < World.BOARD_HEIGHT)
                && (collum >=0 && collum < World.BOARD_WIDTH)
                && getNumberOf(row, collum) != number;
    }
}
