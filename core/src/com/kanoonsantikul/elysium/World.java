package com.kanoonsantikul.elysium;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener{
    public static final int BOARD_SIZE = 7;
    public static final int FULL_HAND = 4;
    public static final float ALPHA = 0.55f;

    public LinkedList<Tile> tiles;
    public LinkedList<Card> player1Cards;
    public LinkedList<Card> player2Cards;
    public Character player1;
    public Character player2;

    public EndTurnButton endTurnButton;
    public CardBar cardBar;
    public FullCard fullCard;

    public boolean isPlayer1Turn;

    private LinkedList<GameObject> gameObjects;

    public Trap trapInstance;
    private GameObject mouseFocus;
    private Queue<Action> actionQueue;
    public LinkedList<Tile> pathTracker;

    public World(){
        tiles = new LinkedList<Tile>();
        initBoard();

        player1Cards = new LinkedList<Card>();
        player2Cards = new LinkedList<Card>();
        initCard();

        player1 = new Character(tiles.getFirst());
        player2 = new Character(tiles.getLast());

        isPlayer1Turn = true;
        endTurnButton = new EndTurnButton();
        cardBar = new CardBar();

        gameObjects = new LinkedList<GameObject>();
        gameObjects.addAll(player1Cards);
        gameObjects.addAll(player2Cards);
        gameObjects.add(player1);
        gameObjects.add(player2);
        gameObjects.addAll(tiles);
        gameObjects.add(endTurnButton);

        trapInstance = new Trap(0, null);
        actionQueue = new Queue<Action>();
        pathTracker = new LinkedList<Tile>();
    }

    @Override
    public void onClicked(float x, float y){
        GameObject object = getObjectAt(x, y, true);
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
        GameObject object = getObjectAt(x, y ,true);
        if(mouseFocus == null && object instanceof Card){
            fullCard = new FullCard((Card)object);
        }
    }

    @Override
    public void onDragStart(float x, float y){
        fullCard = null;

        GameObject object = getObjectAt(x, y, true);
        if(object instanceof Character && !object.isOnAction()){
            if(!(object == player1 ^ isPlayer1Turn)){
                mouseFocus = object;
            }

        } else if(object instanceof Card){
            mouseFocus = object;
        }
    }

    @Override
    public void onDragEnd(float x, float y){
        if(mouseFocus instanceof Character){
            actionQueue.addLast(new MoveAction(mouseFocus, pathTracker));

        } else if(mouseFocus instanceof Card){
            mouseFocus.setVisible(true);
            trapInstance.setId(0);
            ((Card)mouseFocus).positioning();
        }

        mouseFocus = null;
    }

    @Override
    public void onDragged(float x, float y){
        if(mouseFocus instanceof Character){
            GameObject object = getObjectAt(x, y, true);

            if(object instanceof Tile){
                updatePath((Tile)object);
            } else if(object instanceof Character){
                if(!(object == player1 ^ isPlayer1Turn)){
                    pathTracker.clear();
                }
            }

        } else if(mouseFocus instanceof Card){
            if(getObjectAt(x, y, false) instanceof Tile){
                mouseFocus.setVisible(false);
                trapInstance.setId(((Card)mouseFocus).getId());
                trapInstance.setCenter(getObjectAt(x, y, false).getCenter());
            } else{
                mouseFocus.setVisible(true);
                trapInstance.setId(0);
            }
            mouseFocus.setCenter(new Vector2(x, y));
        }
    }

    public GameObject getObjectAt(float x, float y, boolean isDescending){
        GameObject object;
        if(isDescending){
            for(int i=0; i<gameObjects.size(); i++){
                object = gameObjects.get(i);
                if(object.isInBound(x, y) && object.isVisible()){
                    return object;
                }
            }
        } else{
            for(int i=gameObjects.size()-1; i>-1; i--){
                object = gameObjects.get(i);
                if(object.isInBound(x, y) && object.isVisible()){
                    return object;
                }
            }
        }

        return null;
    }

    public Tile getTile(int row, int collum){
        return tiles.get(row * BOARD_SIZE + collum);
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
            if(((Character)mouseFocus).getTile().getNeighbors(this, false).contains(tile)){
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

        LinkedList<Card> thisTurnCards = player1Cards;
        LinkedList<Card> previousTurnCards = player2Cards;
        if(!isPlayer1Turn){
            thisTurnCards = player2Cards;
            previousTurnCards = player1Cards;
        }
        for(int i=0; i<thisTurnCards.size(); i++){
            thisTurnCards.get(i).setVisible(true);
        }
        for(int i=0; i<previousTurnCards.size(); i++){
            previousTurnCards.get(i).setVisible(false);
        }
    }

    private void initBoard(){
        for(int i=0; i<BOARD_SIZE * BOARD_SIZE; i++){
            tiles.add(new Tile(i));
        }
    }

    private void initCard(){
        while(drawCard(player1Cards));
        while(drawCard(player2Cards));
    }

    private boolean drawCard(LinkedList<Card> playerCards){
        Random random = new Random();
        if(playerCards.size() < FULL_HAND){
            playerCards.add(new Card(random.nextInt(3), playerCards.size()));
            return true;
        }
        return false;
    }
}
