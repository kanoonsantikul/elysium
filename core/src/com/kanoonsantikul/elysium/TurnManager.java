package com.kanoonsantikul.elysium;

import java.util.LinkedList;

public class TurnManager{
    private World world;
    private Player player;

    public LinkedList<TurnTrackAction> turnTrackActionPool;

    public TurnManager(World world){
        this.world = world;

        turnTrackActionPool = new LinkedList<TurnTrackAction>();
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
        for(int i=0; i<turnTrackActionPool.size(); i++){
            turnTrackActionPool.get(i).act();
        }
    }
}
