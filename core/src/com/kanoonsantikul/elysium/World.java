package com.kanoonsantikul.elysium;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener{
    public static final int BOARD_SIZE = 7;
    public static final int FULL_HAND = 4;
    public static final float ALPHA = 0.55f;

    public LinkedList<Tile> tiles;
    public Player player1;
    public Player player2;
    public Player player;

    public EndTurnButton endTurnButton;
    public CardBar cardBar;
    public FullCard fullCard;
    public Trap trapInstance;
    public GameObject mouseFocus;

    protected static LinkedList<GameObject> gameObjects;

    public WorldState state;
    public WorldState dragCardState;
    public WorldState dragPlayerState;

    public LinkedList<Action> actionQueue;
    public LinkedList<Tile> pathTracker;
    public TurnManager turnManager;

    public World(){
        tiles = new LinkedList<Tile>();
        initBoard();

        player1 = new Player(tiles.getFirst());
        player2 = new Player(tiles.getLast());
        player = player1;

        endTurnButton = new EndTurnButton();
        cardBar = new CardBar();

        gameObjects = new LinkedList<GameObject>();
        gameObjects.add(player1);
        gameObjects.add(player2);
        initCard();
        gameObjects.addAll(tiles);
        gameObjects.add(endTurnButton);

        dragCardState = new DragCardState();
        dragPlayerState = new DragPlayerState();

        trapInstance = new Trap(0, null);
        actionQueue = new LinkedList<Action>();
        turnManager = new TurnManager(this);
    }

    @Override
    public void onClicked(float x, float y){
        fullCard = null;

        GameObject object = getObjectAt(x, y, null);
        if(object instanceof EndTurnButton){
            turnManager.switchTurn();
        }
    }

    @Override
    public void onPressed(float x, float y){
        GameObject object = getObjectAt(x, y ,null);
        if(object instanceof Card && mouseFocus == null){
            fullCard = new FullCard((Card)object);
        }
    }

    @Override
    public void onDragStart(float x, float y){
        fullCard = null;

        GameObject object = getObjectAt(x, y, null);
        if(object == player && state == null){
            mouseFocus = object;

            state = dragPlayerState;
            state.enterState(this);

        } else if(object instanceof Card && state == null){
            mouseFocus = object;

            state = dragCardState;
            state.enterState(this);
        }
    }

    @Override
    public void onDragEnd(float x, float y){
        if(mouseFocus instanceof Player && state != null){
            state.exitState();
            state = null;
        } else if(mouseFocus instanceof Card && state != null){
            state.exitState();
            state = null;
        }

        mouseFocus = null;
    }

    @Override
    public void onDragged(float x, float y){
        if(mouseFocus instanceof Player && state != null){
            state.handleInput(x, y);
        } else if(mouseFocus instanceof Card && state != null){
            state.handleInput(x, y);
        }
    }

    public static GameObject getObjectAt(float x, float y, Class type){
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

    public static GameObject getObjectAt(Vector2 position, Class type){
        return getObjectAt(position.x, position.y, type);
    }

    public Tile getTile(int row, int collum){
        return tiles.get(row * BOARD_SIZE + collum);
    }

    public void update(){
        turnManager.update();
        updateActionQueue();
    }

    private void updateActionQueue(){
        if(actionQueue.size() > 0){
            Action action = actionQueue.getFirst();
            if(action.isActed()){
                actionQueue.removeFirst();
            } else{
                action.act();
            }
        }
    }

    private void initBoard(){
        for(int i=0; i<BOARD_SIZE * BOARD_SIZE; i++){
            tiles.add(new Tile(i));
        }
    }

    private void initCard(){
        while(drawCard(player1));
        while(drawCard(player2));
    }

    public boolean drawCard(Player player){
        Random random = new Random();
        Card card;
        if(player.getCards().size() < FULL_HAND){
            card = new Card(random.nextInt(3));
            player.addCard(card);
            return true;
        }
        return false;
    }
}
