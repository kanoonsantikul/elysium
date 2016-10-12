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
    public LinkedList<Trap> player1Traps;
    public LinkedList<Trap> player2Traps;
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

        player1Traps = new LinkedList<Trap>();
        player2Traps = new LinkedList<Trap>();

        player1 = new Character(tiles.getFirst());
        player2 = new Character(tiles.getLast());

        isPlayer1Turn = true;
        endTurnButton = new EndTurnButton();
        cardBar = new CardBar();

        gameObjects = new LinkedList<GameObject>();
        gameObjects.add(player1);
        gameObjects.add(player2);
        initCard();
        gameObjects.addAll(tiles);
        gameObjects.add(endTurnButton);


        trapInstance = new Trap(0, null);
        actionQueue = new Queue<Action>();
        pathTracker = new LinkedList<Tile>();
    }

    @Override
    public void onClicked(float x, float y){
        GameObject object = getObjectAt(x, y, null);
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
        GameObject object = getObjectAt(x, y ,null);
        if(mouseFocus == null && object instanceof Card){
            fullCard = new FullCard((Card)object);
        }
    }

    @Override
    public void onDragStart(float x, float y){
        fullCard = null;

        GameObject object = getObjectAt(x, y, null);
        if(object instanceof Character
                && !object.isOnAction()
                && !((Character)object).getIsMoved()){
            if(!(object == player1 ^ isPlayer1Turn)){
                mouseFocus = object;
            }

        } else if(object instanceof Card){
            mouseFocus = object;
        }
    }

    @Override
    public void onDragEnd(float x, float y){
        fullCard = null;

        if(mouseFocus instanceof Character){
            actionQueue.addLast(new MoveAction(
                    (BoardObject)mouseFocus,
                    new LinkedList<Tile>(pathTracker)));
                    pathTracker.clear();
        } else if(mouseFocus instanceof Card){
            GameObject object = getObjectAt(x, y, Tile.class);
            if(object != null){
                boolean tileIsEmpty = true;
                LinkedList<Trap> traps;
                if(isPlayer1Turn){
                    traps = player1Traps;
                } else{
                    traps = player2Traps;
                }

                for(int i=0; i<traps.size(); i++){
                    if(traps.get(i).getTile() == object){
                        tileIsEmpty = false;
                    }
                }

                if(tileIsEmpty){
                    if(isPlayer1Turn){
                        player1Traps.add(new Trap(trapInstance.getId(), (Tile)object));
                        player1Cards.remove(mouseFocus);
                        gameObjects.remove(mouseFocus);
                    } else{
                        player2Traps.add(new Trap(trapInstance.getId(), (Tile)object));
                        player2Cards.remove(mouseFocus);
                        gameObjects.remove(mouseFocus);
                    }
                    updatePlayerCards();
                } else{
                    mouseFocus.setVisible(true);
                    ((Card)mouseFocus).positioning();
                }
            } else{
                mouseFocus.setVisible(true);
                ((Card)mouseFocus).positioning();
            }
            trapInstance.setId(0);
        }

        mouseFocus = null;
    }

    @Override
    public void onDragged(float x, float y){
        if(mouseFocus instanceof Character){
            GameObject object = getObjectAt(x, y, null);
            if(object instanceof Tile){
                updatePath((Tile)object);
            } else if(object instanceof Character){
                if(!(object == player1 ^ isPlayer1Turn)){
                    pathTracker.clear();
                }
            }

        } else if(mouseFocus instanceof Card){
            GameObject object = getObjectAt(x, y, Tile.class);
            if(object != null){
                boolean tileIsEmpty = true;
                LinkedList<Trap> traps;
                if(isPlayer1Turn){
                    traps = player1Traps;
                } else{
                    traps = player2Traps;
                }

                for(int i=0; i<traps.size(); i++){
                    if(traps.get(i).getTile() == object){
                        tileIsEmpty = false;
                    }
                }

                if(tileIsEmpty){
                    mouseFocus.setVisible(false);
                    trapInstance.setId(((Card)mouseFocus).getId());
                    trapInstance.setCenter(object.getCenter());
                }
            } else{
                mouseFocus.setVisible(true);
                trapInstance.setId(0);
            }
            mouseFocus.setCenter(new Vector2(x, y));
        }
    }

    public GameObject getObjectAt(float x, float y, Class type){
        GameObject object;
        for(int i=0; i<gameObjects.size(); i++){
            object = gameObjects.get(i);
            if(object.isInBound(x, y) && object.isVisible()){
                if(type == null || object.getClass() == type){
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

        Character character = (Character)mouseFocus;
        if(pathTracker.size() == 0){
            if(character.getTile().getNeighbors(this, false).contains(tile)){
                pathTracker.add(tile);
            }
        } else if(!pathTracker.contains(tile)){
            if(pathTracker.getLast().getNeighbors(this, false).contains(tile)
                    && pathTracker.size() < character.moveRange){
                pathTracker.add(tile);
            }
        } else{
            int listPosition = pathTracker.indexOf(tile);
            List subPath = pathTracker.subList(0, listPosition + 1);
            pathTracker = new LinkedList(subPath);
        }

    }

    private void updatePlayerCards(){
        Card card;
        for(int i=0; i<player1Cards.size(); i++){
            card = player1Cards.get(i);
            card.setNumber(i);
        }
        for(int i=0; i<player2Cards.size(); i++){
            card = player2Cards.get(i);
            card.setNumber(i);
        }
    }

    private void endTurn(){
        isPlayer1Turn = !isPlayer1Turn;

        LinkedList<Card> thisTurnCards = player1Cards;
        LinkedList<Card> previousTurnCards = player2Cards;
        LinkedList<Trap> thisTurnTraps = player1Traps;
        LinkedList<Trap> previousTurnTraps = player2Traps;

        if(!isPlayer1Turn){
            thisTurnCards = player2Cards;
            previousTurnCards = player1Cards;
            thisTurnTraps =  player2Traps;
            previousTurnTraps = player1Traps;

            player2.setIsMoved(false);
            drawCard(player2Cards);
        } else{
            player1.setIsMoved(false);
            drawCard(player1Cards);
        }
        updatePlayerCards();

        for(int i=0; i<thisTurnCards.size(); i++){
            thisTurnCards.get(i).setVisible(true);
        }
        for(int i=0; i<previousTurnCards.size(); i++){
            previousTurnCards.get(i).setVisible(false);
        }
        for(int i=0; i<thisTurnTraps.size(); i++){
            thisTurnTraps.get(i).setVisible(true);
        }
        for(int i=0; i<previousTurnTraps.size(); i++){
            previousTurnTraps.get(i).setVisible(false);
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
        updatePlayerCards();
    }

    private boolean drawCard(LinkedList<Card> playerCards){
        Random random = new Random();
        Card card;
        if(playerCards.size() < FULL_HAND){
            card = new Card(random.nextInt(3));
            playerCards.add(card);
            gameObjects.add(card);
            return true;
        }
        return false;
    }
}
