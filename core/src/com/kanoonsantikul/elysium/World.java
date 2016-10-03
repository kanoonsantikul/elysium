package com.kanoonsantikul.elysium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class World{
    public static final float DEVICE_RATIO = 0.7f;

    private Tile[] tiles;
    public static final int BOARD_SIZE = 7;
    public static final int BOARD_X_INIT = 9;
    public static final int BOARD_Y_INIT = 150;

    private Character character;

    public World(){
        tiles = new Tile[BOARD_SIZE * BOARD_SIZE];
        createBoard();

        character = new Character(tiles[0].getCenter());
    }

    public Tile[] getTiles(){
        return tiles;
    }

    public Character getCharacter(){
        return character;
    }

    private void createBoard(){
        Texture tile = Assets.tile;
        float x = 0;
        float y = 0;

        for(int i=0; i<(BOARD_SIZE * BOARD_SIZE); i++){
            if(i % BOARD_SIZE == 0){
                x = BOARD_X_INIT;
                y = BOARD_Y_INIT + Tile.HEIGHT * (i / BOARD_SIZE);
            }
            x = BOARD_X_INIT + Tile.WIDTH * (i % BOARD_SIZE);
            tiles[i] = new Tile(x, y);
        }
    }
}
