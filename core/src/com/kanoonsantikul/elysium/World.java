package com.kanoonsantikul.elysium;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class World implements InputHandler.InputListener {
    public static final int BOARD_WIDTH = 4;
    public static final int BOARD_HEIGHT = 9;
    public static final int FULL_HAND = 4;
    public static final float ALPHA = 0.55f;

    private static World world;
    private static GameStateChangeListener listener;
    protected static LinkedList<GameObject> gameObjects;

    protected Random random;
    protected EndTurnButton endTurnButton;
    protected FullCard fullCard;
    protected int notifyText;
    protected GameObject mouseFocus;
    protected LinkedList<Tile> pathTracker;
    protected LinkedList<Tile> targetTiles;
    protected LinkedList<Effect> effectPool;

    protected LinkedList<Tile> tiles;
    protected Player player;
    protected Player enemy;
    protected boolean isMyTurn;

    protected LinkedList<Action> actionQueue;
    protected TurnManager turnManager;

    protected WorldState state;
    protected WorldState handleState;
    protected WorldState dragCardState;
    protected WorldState dragPlayerState;

    public interface GameStateChangeListener {
        public void onGameOver(GameOverScreen.WinState winState);
    }

    public World (int userNumber) {
        world = this;

        random = new Random();
        endTurnButton = new EndTurnButton();
        fullCard = new FullCard();
        targetTiles = new LinkedList<Tile>();
        effectPool = new LinkedList<Effect>();

        tiles = new LinkedList<Tile>();
        initBoard();

        if (userNumber == Player.PLAYER1) {
            player = new Player(Player.PLAYER1, tiles.get(Tile.getNumberOf(0, 1)));
            enemy = new Player(Player.PLAYER2, tiles.get(Tile.getNumberOf(BOARD_HEIGHT - 1, 2)));
            isMyTurn = true;
            endTurnButton.setPressed(false);
        } else if (userNumber == 2) {
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

        handleState = new HandleState(this);
        dragCardState = new DragCardState(this);
        dragPlayerState = new DragPlayerState(this);
        setState(handleState);
    }

    public static World instance () {
        if (world != null) {
            return world;
        }
        return null;
    }

    public static GameObject getObjectAt (float x, float y, Class type) {
        return getObjectAt(x, y, type, false);
    }

    public static GameObject getObjectAt (
            float x,
            float y,
            Class type,
            boolean ignoreVisible) {
        for (GameObject object : gameObjects) {
            if (object.isInBound(x, y)) {
                if (type == null || type.isAssignableFrom(object.getClass())) {
                    if (!ignoreVisible && object.isVisible()) {
                        return object;
                    } else if(ignoreVisible) {
                        return object;
                    }
                }
            }
        }
        return null;
    }

    public static GameObject getObjectAt (
            Vector2 position,
            Class type,
            boolean ignoreVisible) {
        return getObjectAt(position.x, position.y, type, ignoreVisible);
    }

    @Override
    public void onClicked (float x, float y) {
        fullCard.setCardId(FullCard.NULL_CARD, FullCard.PRESSED_SHOW_TYPE);
        fullCard.setPosition(
                new Vector2( FullCard.AUTO_SHOW_X, FullCard.AUTO_SHOW_Y));

        state.onClicked(x, y);
    }

    @Override
    public void onPressed (float x, float y) {
        GameObject object = getObjectAt(x, y ,null);
        if (object instanceof Card && mouseFocus == null) {
            fullCard.setCardId(((Card)object).getId(), FullCard.PRESSED_SHOW_TYPE);

        } else if ((object = getObjectAt(x, y, Trap.class)) != null) {
            fullCard.setCardId(((Trap)object).getId(), FullCard.PRESSED_SHOW_TYPE);

        }
    }

    @Override
    public void onDragStart (float x, float y) {
        fullCard.setCardId(FullCard.NULL_CARD, FullCard.PRESSED_SHOW_TYPE);
        fullCard.setPosition(
                new Vector2( FullCard.AUTO_SHOW_X, FullCard.AUTO_SHOW_Y));

        state.onDragStart(x, y);
    }

    @Override
    public void onDragEnd (float x, float y) {
        fullCard.setCardId(FullCard.NULL_CARD, FullCard.PRESSED_SHOW_TYPE);
        fullCard.setPosition(
                new Vector2( FullCard.AUTO_SHOW_X, FullCard.AUTO_SHOW_Y));

        state.onDragEnd(x, y);
    }

    @Override
    public void onDragged (float x, float y) {
        fullCard.setCardId(FullCard.NULL_CARD, FullCard.PRESSED_SHOW_TYPE);
        fullCard.setPosition(
                new Vector2( FullCard.AUTO_SHOW_X, FullCard.AUTO_SHOW_Y));

        state.onDragged(x, y);
    }

    public void setListener (GameStateChangeListener listener) {
        this.listener = listener;
    }

    public void setState (WorldState state) {
        if (this.state != null) {
            this.state.exitState();
        }

        this.state = state;
        state.enterState();
    }

    public void syncPlayerData () {
        while (drawCard());
    }

    public void update () {
        if (state == dragCardState && isMyTurn && !player.isLock()) {
            getTargetTile();
        } else if (player.isLock()) {
            targetTiles.clear();
        }
        updateActionQueue();
        CheckPlayerHealth();
    }

    public void getTargetTile () {
        for (Action action : actionQueue) {
            if (action instanceof MoveAction) {
                return;
            }
        }

        targetTiles.clear();
        LinkedList<Tile> neighborTiles = player.getTile()
                .getNeighbors(player.getTrapRange(), Tile.CIRCLE_RANGE);

        Tile tile;
        for (int i = 0; i < neighborTiles.size(); i++) {
            tile = neighborTiles.get(i);
            if(getObjectAt(tile.getCenter(), Trap.class, false) == null){
                targetTiles.add(tile);
            }
        }
    }

    private void updateActionQueue () {
        if (actionQueue.size() > 0) {
            Action action = actionQueue.getFirst();
            if (action.isActed()) {
                actionQueue.removeFirst();
            } else {
                action.act();
            }
        }
    }

    private void CheckPlayerHealth () {
        if (player.getHealth() <= 0 || enemy.getHealth() <= 0) {
            GameOverScreen.WinState winState;
            if (player.getHealth() <= 0 && enemy.getHealth() <= 0) {
                winState = GameOverScreen.WinState.DRAW;
            } else if (player.getHealth() <= 0) {
                winState = GameOverScreen.WinState.LOSE;
            } else {
                winState = GameOverScreen.WinState.WIN;
            }

            if (listener != null) {
                listener.onGameOver(winState);
            }
        }
    }

    private void initBoard () {
        for (int i=0; i<BOARD_WIDTH * BOARD_HEIGHT; i++) {
            tiles.add(new Tile(i));
        }
        tiles.get(Tile.getNumberOf(3,0)).setVisible(false);
        tiles.get(Tile.getNumberOf(4,0)).setVisible(false);
        tiles.get(Tile.getNumberOf(5,0)).setVisible(false);
        tiles.get(Tile.getNumberOf(3,3)).setVisible(false);
        tiles.get(Tile.getNumberOf(4,3)).setVisible(false);
        tiles.get(Tile.getNumberOf(5,3)).setVisible(false);
    }

    public boolean drawCard () {
        int cardId;
        if (player.getCards().size() < FULL_HAND) {
            cardId = TrapBuilder.randomTrapId();
            player.addCard(new Card(cardId));
            return true;
        }
        return false;
    }
}
