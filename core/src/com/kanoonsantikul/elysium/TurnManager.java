package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public class TurnManager{
    private World world;
    private Player player;

    public TurnManager(World world){
        this.world = world;
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
        player.setTurnCount(player.getTurnCount() + 1);
        world.drawCard(player.getCards());
        player.updateCards();

        for(int i=0; i<player.getCards().size(); i++){
            player.getCards().get(i).setVisible(true);
        }
        for(int i=0; i<player.getTraps().size(); i++){
            player.getTraps().get(i).setVisible(true);
        }
    }
}
