package com.kanoonsantikul.elysium;

import java.util.List;
import java.util.LinkedList;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener{
    public static final float DEVICE_RATIO = 0.7f;

    public static final int BOARD_SIZE = 7;
    public static final int BOARD_X_INIT = 9;
    public static final int BOARD_Y_INIT = 150;
    private Tile[] tiles;

    private Character character;

    private Array<GameObject> gameObjects;

    private Queue<Action> actionQueue;
    private LinkedList<Tile> pathTracker;
    private Character activeCharacter;

    public World(){
        tiles = new Tile[BOARD_SIZE * BOARD_SIZE];
        createBoard();

        character = new Character(tiles[0].getCenter());

        gameObjects = new Array<GameObject>();
        gameObjects.add(character);
        gameObjects.addAll(tiles);

        actionQueue = new Queue<Action>();
        pathTracker = new LinkedList<Tile>();
    }

    @Override
    public void onClicked(float x, float y){
    }

    @Override
    public void onDragStart(float x, float y){
        GameObject object = getObjectAt(x, y);
        if(object != null){
            if(object instanceof Character){
                activeCharacter = (Character)object;
            }
        }
    }

    @Override
    public void onDragEnd(float x, float y){
        if(activeCharacter != null){
            //move
        }

        activeCharacter = null;
        pathTracker.clear();
    }

    @Override
    public void onDragged(float x, float y){
        GameObject object = getObjectAt(x, y);
        if(object != null){
            if(activeCharacter != null && object instanceof Tile){
                updatePath((Tile)object);
            }
        }
    }

    public GameObject getObjectAt(float x, float y){
        for(int i=0; i<gameObjects.size; i++){
            if(gameObjects.get(i).isInBound(x, y)){
                return gameObjects.get(i);
            }
        }

        return null;
    }

    public Tile[] getTiles(){
        return tiles;
    }

    public Tile getTile(int row, int collum){
        return getTile(row * BOARD_SIZE + collum);
    }

    public Tile getTile(int number){
        return tiles[number];
    }

    public Character getCharacter(){
        return character;
    }

    public LinkedList<Tile> getPathTracker(){
        return pathTracker;
    }

    private void updatePath(Tile tile){
        if(!pathTracker.contains(tile)){
            if(pathTracker.size() == 0 ||
                    pathTracker.getLast().getNeighbors(this, false).contains(tile))
            pathTracker.add(tile);
        } else{
            int listPosition = pathTracker.indexOf(tile);
            List subPath = pathTracker.subList(0, listPosition + 1);
            pathTracker = new LinkedList(subPath);
        }

        // String log ="";
        // for(int i=0;i<pathTracker.size();i++)
        //     log += pathTracker.get(i).hashCode() + " ";
        // Gdx.app.log("",log);
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
            tiles[i] = new Tile(x, y, i);
        }
    }
}
