package com.kanoonsantikul.elysium;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener{
    public static final int BOARD_SIZE = 7;
    public static final int FULL_HAND = 4;

    public Tile[] tiles;
    public Card[] player1Cards;
    public Card[] player2Cards;
    public Character player1;
    public Character player2;

    public EndTurnButton endTurnButton;
    public CardBar cardBar;
    public FullCard fullCard;

    public boolean isPlayer1Turn;

    private Array<GameObject> gameObjects;

    private GameObject mouseFocus;
    private Queue<Action> actionQueue;
    public LinkedList<Tile> pathTracker;

    public World(){
        tiles = new Tile[BOARD_SIZE * BOARD_SIZE];
        initBoard();

        player1Cards = new Card[FULL_HAND];
        player2Cards = new Card[FULL_HAND];
        initCard();

        player1 = new Character(tiles[0]);
        player2 = new Character(tiles[tiles.length - 1]);

        isPlayer1Turn = true;
        endTurnButton = new EndTurnButton();
        cardBar = new CardBar();

        gameObjects = new Array<GameObject>();
        gameObjects.addAll(player1Cards);
        gameObjects.addAll(player2Cards);
        gameObjects.add(player1);
        gameObjects.add(player2);
        gameObjects.addAll(tiles);
        gameObjects.add(endTurnButton);

        actionQueue = new Queue<Action>();
        pathTracker = new LinkedList<Tile>();
    }

    @Override
    public void onClicked(float x, float y){
        GameObject object = getObjectAt(x, y);
        if(object instanceof EndTurnButton){
            endTurn();
        } else if(object instanceof Card){
            if(fullCard != null){
                fullCard = null;
            }
        }
    }

    @Override
    public void onPressed(float x, float y){
        if(mouseFocus == null &&
                getObjectAt(x, y) instanceof Card){
            fullCard = new FullCard((Card)getObjectAt(x, y));
        }
    }

    @Override
    public void onDragStart(float x, float y){
        fullCard = null;

        GameObject object = getObjectAt(x, y);
        if(object instanceof Character && !object.isOnAction()){
            if(!(object == player1 ^ isPlayer1Turn)){
                mouseFocus = object;
            }

        } else if(object instanceof Card){
            mouseFocus = object;
            ((Card)mouseFocus).setAlpha(0.75f);
        }
    }

    @Override
    public void onDragEnd(float x, float y){
        if(mouseFocus instanceof Character){
            actionQueue.addLast(new MoveAction(mouseFocus, pathTracker));

        } else if(mouseFocus instanceof Card){
            ((Card)mouseFocus).positioning();
            ((Card)mouseFocus).setAlpha(1f);
        }

        mouseFocus = null;
    }

    @Override
    public void onDragged(float x, float y){
        if(mouseFocus instanceof Character){
            GameObject object = getObjectAt(x, y);

            if(object instanceof Tile){
                updatePath((Tile)object);
            } else if(object instanceof Character){
                if(!(object == player1 ^ isPlayer1Turn)){
                    pathTracker.clear();
                }
            }

        } else if(mouseFocus instanceof Card){
            mouseFocus.setCenter(new Vector2(x, y));
        }
    }

    public GameObject getObjectAt(float x, float y){
        for(int i=0; i<gameObjects.size; i++){
            GameObject object = gameObjects.get(i);
            if(object.isInBound(x, y) && object.isVisible()){
                return object;
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

    public Tile getTile(int row, int collum){
        return tiles[row * BOARD_SIZE + collum];
    }

    public void update(){
        updateActionQueue();
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

        if(pathTracker.size() == 0){
            if(getTileOf(mouseFocus).getNeighbors(this, false).contains(tile)){
                pathTracker.add(tile);
            }
        } else if(!pathTracker.contains(tile)){
            if(pathTracker.getLast().getNeighbors(this, false).contains(tile)){
                pathTracker.add(tile);
            }
        } else{
            int listPosition = pathTracker.indexOf(tile);
            List subPath = pathTracker.subList(0, listPosition + 1);
            pathTracker = new LinkedList(subPath);
        }

    }

    private void endTurn(){
        isPlayer1Turn = !isPlayer1Turn;

        Card[] thisTurnCards = player1Cards;
        Card[] previousTurnCards = player2Cards;
        if(!isPlayer1Turn){
            thisTurnCards = player2Cards;
            previousTurnCards = player1Cards;
        }
        for(int i=0; i<thisTurnCards.length; i++){
            thisTurnCards[i].setVisible(true);
        }
        for(int i=0; i<previousTurnCards.length; i++){
            previousTurnCards[i].setVisible(false);
        }
    }

    private void initBoard(){
        for(int i=0; i<tiles.length; i++){
            tiles[i] = new Tile(i);
        }
    }

    private void initCard(){
        Random random = new Random();
        for(int i=0; i<FULL_HAND; i++){
            player1Cards[i] = new Card(i, random.nextInt(3));
            player2Cards[i] = new Card(i, random.nextInt(3));
        }
    }

    private void drawCard(Card[] playerCards){
        //if()
    }
}
