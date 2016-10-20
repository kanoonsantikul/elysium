package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class Tile extends GameObject{
    public static final float WIDTH = Assets.tile.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.tile.getHeight() * Elysium.DEVICE_RATIO;

    private static float INIT_X = 9f;
    private static float INIT_Y = 150f;

    private static final int[][] directions = new int[][]{
        {-1, -1}, {-1, 0}, {-1, 1},
        {0 ,-1}, {0, 0}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };

    private int number;

    public Tile(int number){
        float y = INIT_Y + HEIGHT * getRowOf(number);
        float x = INIT_X + WIDTH * getCollumOf(number);

        setPosition(new Vector2(x, y));
        this.number = number;
    }

    public static int getNumberOf(int row, int collum){
        return row * World.BOARD_SIZE + collum;
    }

    public static int getRowOf(int number){
        return number / World.BOARD_SIZE;
    }

    public static int getCollumOf(int number){
        return number % World.BOARD_SIZE;
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


    public LinkedList<Tile> getNeighbors(boolean neighbor8){
        LinkedList<Tile> tiles = World.instance().tiles;
        LinkedList<Tile> neighbors = new LinkedList<Tile>();
        Tile tile;
        int row, collum;

        for(int i=0; i<9; i++){
            row = directions[i][0] + getRowOf(number);
            collum = directions[i][1] + getCollumOf(number);

            if((row >= 0 && row < World.BOARD_SIZE)
                    && (collum >=0 && collum < World.BOARD_SIZE)
                    && i != 4){
                if(!neighbor8){
                    if(i % 2 == 1){
                        tile = tiles.get(getNumberOf(row, collum));
                        neighbors.add(tile);
                    }
                } else{
                    tile = tiles.get(getNumberOf(row, collum));
                    neighbors.add(tile);
                }
            }
            tile = null;
        }
        return neighbors;
    }

}
