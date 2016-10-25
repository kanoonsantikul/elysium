package com.kanoonsantikul.elysium;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

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
        ParticleEffect effect = new ParticleEffect(Assets.bombEffect);
        effect.getEmitters().first().setPosition(
                tile.getCenter().x,
                tile.getCenter().y);
        effect.start();
        World.instance().effects.add(effect);

        Player player = (Player)World.getObjectAt(tile.getCenter(), Player.class, false);
        if(player != null){
            player.setHealth(player.getHealth() - damage);
        }
        setActed(true);
    }

}
