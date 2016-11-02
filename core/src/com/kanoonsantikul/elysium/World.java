package com.kanoonsantikul.elysium;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener{
    public static final int BOARD_WIDTH = 4;
    public static final int BOARD_HEIGHT = 9;
    public static final int FULL_HAND = 4;
    public static final float ALPHA = 0.55f;

    private static World world;
    private static GameStateChangeListener listener;
    protected static LinkedList<GameObject> gameObjects;

    protected Random random;
    protected EndTurnButton endTurnButton;
    protected CardBar cardBar;
    protected FullCard fullCard;
    protected int notifyText;
    protected GameObject mouseFocus;
    protected LinkedList<Tile> pathTracker;
    protected LinkedList<Tile> targetTiles;
    protected LinkedList<DamageEffect> effects;

    protected LinkedList<Tile> tiles;
    protected Player player;
    protected Player enemy;
    protected boolean isMyTurn;

    protected LinkedList<Action> actionQueue;
    protected TurnManager turnManager;

    protected WorldState state;
    protected WorldState dragCardState;
    protected WorldState dragPlayerState;

    public interface GameStateChangeListener{
        public void onGameOver(GameOverScreen.WinState winState);
    }

    public World(int userNumber){
        world = this;

        random = new Random();
        endTurnButton = new EndTurnButton();
        cardBar = new CardBar();
        fullCard = new FullCard();
        targetTiles = new LinkedList<Tile>();
        effects = new LinkedList<DamageEffect>();

        tiles = new LinkedList<Tile>();
        initBoard();

        if(userNumber == Player.PLAYER1){
            player = new Player(Player.PLAYER1, tiles.get(Tile.getNumberOf(0, 1)));
            enemy = new Player(Player.PLAYER2, tiles.get(Tile.getNumberOf(BOARD_HEIGHT - 1, 2)));
            isMyTurn = true;
            endTurnButton.setPressed(false);
        } else if(userNumber == 2){
            player = new Player(Player.PLAYER2, tiles.get(Tile.getNumberOf(BOARD_HEIGHT - 1, 2)));
            enemy = new Player(Player.PLAYER1, tiles.get(Tile.getNumberOf(0, 1)));
            isMyTurn = false;
            endTurnButton.setPressed(true);
        }

        gameObjects = new LinkedList<GameObject>();
        gameObjects.add(player);
        gameObjects.add(enemy);
        gameObjects.addAll(tiles);
        gameObjects.add(endTurnButton);

        actionQueue = new LinkedList<Action>();
        turnManager = new TurnManager(this);

        dragCardState = new DragCardState();
        dragPlayerState = new DragPlayerState();
    }

    public static World instance(){
        if(world != null){
            return world;
        }
        return null;
    }

    public static GameObject getObjectAt(float x, float y, Class type){
        return getObjectAt(x, y, type, false);
    }

    public static GameObject getObjectAt(
            float x,
            float y,
            Class type,
            boolean ignoreVisible){
        GameObject object;
        for(int i=0; i<gameObjects.size(); i++){
            object = gameObjects.get(i);
            if(object.isInBound(x, y)){
                if(type == null || type.isAssignableFrom(object.getClass())){
                    if(!ignoreVisible && object.isVisible()){
                        return object;
                    } else if(ignoreVisible){
                        return object;
                    }
                }
            }
        }
        return null;
    }

    public static GameObject getObjectAt(
            Vector2 position,
            Class type,
            boolean ignoreVisible){
        return getObjectAt(position.x, position.y, type, ignoreVisible);
    }

    @Override
    public void onClicked(float x, float y){
        fullCard.setCardId(FullCard.NULL_CARD);

        GameObject object = getObjectAt(x, y, null);
        if(object instanceof EndTurnButton && isMyTurn){
            turnManager.endTurn();
        }
    }

    @Override
    public void onPressed(float x, float y){
        GameObject object = getObjectAt(x, y ,null);
        if(object instanceof Card && mouseFocus == null){
            fullCard.setCardId(((Card)object).getId());
        } else if((object = getObjectAt(x, y, Trap.class)) != null){
            fullCard.setCardId(((Trap)object).getId());
        }

        if(state == dragCardState){
            state.handleInput(x, y);
        }
    }

    @Override
    public void onDragStart(float x, float y){
        fullCard.setCardId(FullCard.NULL_CARD);

        GameObject object = getObjectAt(x, y, null);
        mouseFocus = object;
        if(object == player && state == null){
            state = dragPlayerState;
            state.enterState(this);
        } else if(object instanceof Card && state == null){
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

    public void setListener(GameStateChangeListener listener){
        this.listener = listener;
    }

    public void syncPlayerData(){
        while(drawCard());
    }

    public void update(){
        updateActionQueue();
        CheckPlayerHealth();
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

    private void CheckPlayerHealth(){
        if(player.getHealth() <= 0 || enemy.getHealth() <= 0){
            GameOverScreen.WinState winState;
            if(player.getHealth() <= 0 && enemy.getHealth() <= 0){
                winState = GameOverScreen.WinState.DRAW;
            } else if(player.getHealth() <= 0){
                winState = GameOverScreen.WinState.LOSE;
            } else{
                winState = GameOverScreen.WinState.WIN;
            }

            if(listener != null){
                listener.onGameOver(winState);
            }
        }
    }

    private void initBoard(){
        for(int i=0; i<BOARD_WIDTH * BOARD_HEIGHT; i++){
            tiles.add(new Tile(i));
        }
    }

    public boolean drawCard(){
        int cardId;
        if(player.getCards().size() < FULL_HAND){
            cardId = TrapBuilder.randomTrapId();
            player.addCard(new Card(cardId));
            return true;
        }
        return false;
    }
}
