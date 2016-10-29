package com.kanoonsantikul.elysium;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class DamageEffect{
    private ParticleEffect effect;
    private int damage;

    public DamageEffect(ParticleEffect effect, int damage){
        this.effect = effect;
        this.damage = damage;

        effect.start();
    }

    public int getDamage(){
        return damage;
    }

    public ParticleEffect getEffect(){
        return effect;
    }
}
