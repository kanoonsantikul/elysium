package com.kanoonsantikul.elysium;

import java.util.List;
import java.util.LinkedList;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener{
    public static final int BOARD_SIZE = 7;
    public static final int FULL_HAND = 4;

    public Tile[] tiles;
    public Card[] cards;
    public Character player1;

    public EndTurnButton endTurnButton;
    public CardBar cardBar;

    public boolean isPlayer1Turn;

    private Array<GameObject> gameObjects;

    public LinkedList<Tile> pathTracker;
    private Character activeCharacter;
    private Queue<Action> actionQueue;

    public World(){
        tiles = new Tile[BOARD_SIZE * BOARD_SIZE];
        initBoard();

        cards = new Card[FULL_HAND];
        initCard();

        player1 = new Character(tiles[0].getCenter());

        isPlayer1Turn = true;
        endTurnButton = new EndTurnButton();
        cardBar = new CardBar();

        gameObjects = new Array<GameObject>();
        gameObjects.add(player1);
        gameObjects.addAll(tiles);
        gameObjects.add(endTurnButton);

        actionQueue = new Queue<Action>();
        pathTracker = new LinkedList<Tile>();
    }

    @Override
    public void onClicked(float x, float y){
        GameObject object = getObjectAt(x, y);
        if(object instanceof EndTurnButton){
            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    @Override
    public void onDragStart(float x, float y){
        pathTracker.clear();

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
            actionQueue.addLast(
                    new MoveAction(activeCharacter, pathTracker));
        }

        activeCharacter = null;
    }

    @Override
    public void onDragged(float x, float y){
        GameObject object = getObjectAt(x, y);
        if(object != null){
            if(activeCharacter != null){
                if(object instanceof Tile){
                    updatePath((Tile)object);
                } else if(object instanceof Character){
                    pathTracker.clear();
                }
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

    public Tile getTileOf(GameObject object){
        for(int i=0; i<tiles.length ; i++){
            if(tiles[i].getCenter().x == object.getCenter().x &&
                    tiles[i].getCenter().y == object.getCenter().y){
                return tiles[i];
            }
        }

        return null;
    }

    public void update(){
        updateActionQueue();
    }

    public Tile getTile(int row, int collum){
        return tiles[row * BOARD_SIZE + collum];
    }

    private void updateActionQueue(){
        if(actionQueue.size > 0){
            Action action = actionQueue.first();
            if(action.isActed()){
                actionQueue.removeFirst();
            } else{
                action.act();
            }
        }
    }

    private void updatePath(Tile tile){
        if(tile == null){
            return;
        }
        if(!pathTracker.contains(tile)){
            if(pathTracker.size() == 0 ||
                    pathTracker.getLast().getNeighbors(this, false).contains(tile))
            pathTracker.add(tile);
        } else{
            int listPosition = pathTracker.indexOf(tile);
            List subPath = pathTracker.subList(0, listPosition + 1);
            pathTracker = new LinkedList(subPath);
        }
    }

    private void initBoard(){
        for(int i=0; i<tiles.length; i++){
            tiles[i] = new Tile(i);
        }
    }

    private void initCard(){
        for(int i=0; i<cards.length; i++){
            cards[i] = new Card(i);
        }
    }
}
