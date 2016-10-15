package com.kanoonsantikul.elysium;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class DragCardState implements WorldState{
    private World world;
    private Card card;
    private Trap trapInstance;
    private Tile lastFocusTile;

    @Override
    public void enterState(World world){
        this.world = world;
        this.card = (Card)world.mouseFocus;
        this.trapInstance = world.trapInstance;
    }

    @Override
    public void handleInput(float x, float y){
        GameObject mouseOver = world.getObjectAt(x, y ,Tile.class);
        if(mouseOver instanceof Tile){
            LinkedList<Trap> traps = world.player.getTraps();
            boolean tileIsEmpty = true;
            for(int i=0; i<traps.size(); i++){
                if(traps.get(i).getTile() == mouseOver){
                    tileIsEmpty = false;
                }
            }

            if(tileIsEmpty){
                lastFocusTile = (Tile)mouseOver;
                card.setVisible(false);
                trapInstance.setId(card.getId());
                trapInstance.setCenter(mouseOver.getCenter());
            }

        } else{
            card.setVisible(true);
            trapInstance.setId(0);
            lastFocusTile = null;
        }

        card.setCenter(new Vector2(x, y));
    }

    @Override
    public void update(){

    }

    @Override
    public void exitState(){
        if(lastFocusTile != null){
            world.player.getTraps().add(
                    new Trap(trapInstance.getId(), lastFocusTile));
            world.player.getCards().remove(card);
            world.gameObjects.remove(card);
            world.player.updateCards();

        } else{
            card.setVisible(true);
            card.positioning();
        }

        trapInstance.setId(0);
    }
}
