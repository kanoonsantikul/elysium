package com.kanoonsantikul.elysium;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener{
    public static final float DEVICE_RATIO = 0.7f;

    private Tile[] tiles;
    public static final int BOARD_SIZE = 7;
    public static final int BOARD_X_INIT = 9;
    public static final int BOARD_Y_INIT = 150;

    private Character character;

    private Array<GameObject> gameObjects;

    private Queue<Action> actionQueue;

    public World(){
        tiles = new Tile[BOARD_SIZE * BOARD_SIZE];
        createBoard();

        character = new Character(tiles[0].getCenter());

        gameObjects = new Array<GameObject>();
        gameObjects.add(character);
        gameObjects.addAll(tiles);

        actionQueue = new Queue();
    }

    @Override
    public void onClicked(float x, float y){
        GameObject object = getObjectAt(x, y);
        if(object != null){
            Gdx.app.log("log", object.toString());
        }
    }

    public Tile[] getTiles(){
        return tiles;
    }

    public Character getCharacter(){
        return character;
    }

    public GameObject getObjectAt(float x, float y){
        for(int i=0; i<gameObjects.size; i++){
            if(gameObjects.get(i).isInBound(x, y)){
                return gameObjects.get(i);
            }
        }

        return null;
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
