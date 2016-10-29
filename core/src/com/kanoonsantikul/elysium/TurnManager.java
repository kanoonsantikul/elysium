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

    public void switchTurn(){
        endTurn(world.player);

        if(world.player == world.player1){
            world.player = world.player2;
        } else{
            world.player = world.player1;
        }

        startTurn(world.player);
    }

    public void endTurn(Player player){
        for(int i=0; i<player.getCards().size(); i++){
            player.getCards().get(i).setVisible(false);
        }
        for(int i=0; i<player.getTraps().size(); i++){
            player.getTraps().get(i).setVisible(false);
        }

        for(int i=0; i<listeners.size(); i++){
            listeners.get(i).onTurnEnd(player);
        }
    }

    public void startTurn(Player player){
        player.setIsMoved(false);
        world.drawCard(player);
        player.updateCards();

        for(int i=0; i<player.getCards().size(); i++){
            player.getCards().get(i).setVisible(true);
        }
        for(int i=0; i<player.getTraps().size(); i++){
            player.getTraps().get(i).setVisible(true);
        }

        LinkedList<TurnStateChangeListener> listeners
                = new LinkedList<TurnStateChangeListener>(this.listeners);
        for(int i=0; i<listeners.size(); i++){
            listeners.get(i).onTurnStart(player);
        }
    }
}
