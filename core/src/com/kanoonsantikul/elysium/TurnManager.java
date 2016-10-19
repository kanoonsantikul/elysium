package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;

public class TurnManager{
    private World world;

    public static LinkedList<Action> turnTrackActionPool;

    public TurnManager(World world){
        this.world = world;

        turnTrackActionPool = new LinkedList<Action>();
    }

    public void switchTurn(){
        endTurn(world.player);

        if(world.player == world.player1){
            world.player = world.player2;
        } else{
            world.player = world.player1;
        }

        startTurn(world.player);
        update();
    }

    public void endTurn(Player player){
        for(int i=0; i<player.getCards().size(); i++){
            player.getCards().get(i).setVisible(false);
        }
        for(int i=0; i<player.getTraps().size(); i++){
            player.getTraps().get(i).setVisible(false);
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
    }

    public void update(){
        Action action;
        for(int i=0; i<turnTrackActionPool.size(); i++){
            action = turnTrackActionPool.get(i);
            if(action.isActed()){
                turnTrackActionPool.remove(i);
                continue;
            }
            if(action.actor == world.player){
                action.act();
            }
        }
    }

}
