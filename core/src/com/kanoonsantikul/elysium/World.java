package com.kanoonsantikul.elysium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class World{
    public static final float DEVICE_RATIO = 0.7f;

    public static final int BOARD_SIZE = 7;
    public static final int BOARD_X_INIT = 9;
    public static final int BOARD_Y_INIT = 150;

    private Tile[][] tiles;

    public World(){
        tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        createBoard();
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    private void createBoard(){
        Texture tile = Assets.tile;
        float x = 0;
        float y = 0;

        for(int i=0; i<(BOARD_SIZE * BOARD_SIZE); i++){
            if(i % BOARD_SIZE == 0){
                x = BOARD_X_INIT;
                y = BOARD_Y_INIT + Tile.HEIGHT * (i / BOARD_SIZE);
                Gdx.app.log("y", "" + y);
            }
            x = BOARD_X_INIT + Tile.WIDTH * (i % BOARD_SIZE);
            tiles[i / BOARD_SIZE][i % BOARD_SIZE] = new Tile(x, y);
        }
    }
}
