package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;

public class TurnManager{
    private World world;

    public LinkedList<TurnStateChangeListener> listeners;

    public interface TurnStateChangeListener{
        public void onTurnStart(Player player);
        public void onTurnEnd(Player player);
    }

    public TurnManager(World world){
        this.world = world;

        listeners = new LinkedList<TurnStateChangeListener>();
    }

    public void addListener(TurnStateChangeListener listener){
        listeners.add(listener);
    }

    public void removeListener(TurnStateChangeListener listener){
        listeners.remove(listener);
    }

    public void endTurn(){
        world.endTurnButton.setPressed(true);
        world.isMyTurn = false;

        LinkedList<TurnStateChangeListener> listeners
                = new LinkedList<TurnStateChangeListener>(this.listeners);
        for(int i=0; i<listeners.size(); i++){
            listeners.get(i).onTurnEnd(world.player);
            listeners.get(i).onTurnStart(world.enemy);
        }

        MultiplayerUpdater.instance().sendTurnUpdate();
    }

    public void startTurn(){
        world.endTurnButton.setPressed(false);
        world.isMyTurn = true;

        world.player.setIsMoved(false);
        world.drawCard();

        LinkedList<TurnStateChangeListener> listeners
                = new LinkedList<TurnStateChangeListener>(this.listeners);
        for(int i=0; i<listeners.size(); i++){
            listeners.get(i).onTurnStart(world.player);
            listeners.get(i).onTurnEnd(world.enemy);
        }
    }
}
