package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Effect extends GameObject{
    private ParticleEffect particle;
    private String message;

    public Effect (ParticleEffect particle, String message, float x, float y) {
        this.particle = particle;
        this.message = message;
        setCenter(new Vector2(x, y));

        particle.getEmitters().first().setPosition(x, y);
        particle.start();
    }

    @Override
    public float getWidth(){
        return 0;
    }

    @Override
    public float getHeight(){
        return 0;
    }

    public String getMessage () {
        return message;
    }

    public ParticleEffect getParticle () {
        return particle;
    }

    public boolean draw (SpriteBatch batcher, float delta) {
        particle.draw(batcher);
        particle.update(delta);

        if (particle.getEmitters().first().getPercentComplete() >= 0.5) {
            return false;
        } else {
            return true;
        }
    }

}
