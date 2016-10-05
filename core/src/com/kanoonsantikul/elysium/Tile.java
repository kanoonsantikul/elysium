package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class Tile extends GameObject{
    public static final float WIDTH = Assets.tile.getWidth() * World.DEVICE_RATIO;
    public static final float HEIGHT = Assets.tile.getHeight() * World.DEVICE_RATIO;

    private int number;

    public Tile(float x, float y, int number){
        this(new Vector2(x, y), number);
    }

    public Tile(Vector2 position, int number){
        setPosition(position);
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

    public LinkedList<Tile> getNeighbors(World world, boolean neighbor8){
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
