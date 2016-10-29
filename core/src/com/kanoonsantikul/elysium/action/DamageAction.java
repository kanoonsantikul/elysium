package com.kanoonsantikul.elysium;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class DamageAction extends Action{
    private Tile tile;
    private int damage;

    public DamageAction(Tile tile, int damage){
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
        World.instance().effects.add(new DamageEffect(effect, damage));

        Player player = (Player)World.getObjectAt(tile.getCenter(), Player.class, false);
        if(player != null){
            player.setHealth(player.getHealth() - damage);
        }
        setActed(true);
    }

}
