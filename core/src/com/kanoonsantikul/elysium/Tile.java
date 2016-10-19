package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class Tile extends GameObject{
    public static final float WIDTH = Assets.tile.getWidth() * Elysium.DEVICE_RATIO;
    public static final float HEIGHT = Assets.tile.getHeight() * Elysium.DEVICE_RATIO;

    private static float INIT_X = 9f;
    private static float INIT_Y = 150f;

    private int number;

    public Tile(int number){
        float y = INIT_Y + HEIGHT * (number / World.BOARD_SIZE);
        float x = INIT_X + WIDTH * (number % World.BOARD_SIZE);

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

    public LinkedList<Tile> getNeighbors(boolean neighbor8){
        World world = World.instance();
        LinkedList<Tile> neighbors = new LinkedList<Tile>();

        int row = number / World.BOARD_SIZE;
        int collum = number % World.BOARD_SIZE;

        if(row - 1 >= 0){
            neighbors.add(world.getTile(row - 1, collum));
        }
        if(row + 1 < World.BOARD_SIZE){
            neighbors.add(world.getTile(row + 1, collum));
        }
        if(collum - 1 >= 0){
            neighbors.add(world.getTile(row, collum - 1));
        }
        if(collum + 1 < World.BOARD_SIZE){
            neighbors.add(world.getTile(row, collum + 1));
        }

        if(neighbor8){
            if(row - 1 >= 0 && collum - 1 >= 0){
                neighbors.add(world.getTile(row - 1, collum - 1));
            }
            if(row - 1 >= 0 && collum + 1 < World.BOARD_SIZE){
                neighbors.add(world.getTile(row - 1, collum + 1));
            }
            if(row + 1 < World.BOARD_SIZE && collum -1 >= 0){
                neighbors.add(world.getTile(row + 1, collum - 1));
            }
            if(row + 1 < World.BOARD_SIZE && collum + 1 < World.BOARD_SIZE){
                neighbors.add(world.getTile(row + 1, collum + 1));
            }
        }

        return neighbors;
    }

}
