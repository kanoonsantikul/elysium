package com.kanoonsantikul.elysium;

public class BombAction extends Action{
    private Tile tile;
    private int damage;

    public BombAction(Tile tile, int damage){
        super(tile);

        this.tile = tile;
        this.damage = damage;
    }

    @Override
    public void act(){
        Player player = (Player)World.getObjectAt(tile.getCenter(), Player.class, false);
        if(player != null){
            player.setHealth(player.getHealth() - damage);
        }
        setActed(true);
    }

}
