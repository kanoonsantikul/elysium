package com.kanoonsantikul.elysium;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public abstract class GameObject {
    public abstract float getWidth();
    public abstract float getHeight();

    protected boolean isLock = false;
    protected boolean isVisible = true;

    private Vector2 position;

    public void setPosition (Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition () {
        return new Vector2(position);
    }

    public Vector2 getCenter () {
        float x = this.position.x + getWidth()/2;
        float y = this.position.y + getHeight()/2;
        return new Vector2(x, y);
    }

    public void setCenter (Vector2 position) {
        float x = position.x - getWidth()/2;
        float y = position.y - getHeight()/2;
        setPosition(new Vector2(x, y));
    }

    public boolean isInBound (float x, float y) {
        if (x >= position.x && x <= position.x + getWidth()
                && y >= position.y && y <= position.y + getHeight()) {
            return true;
        }
        return false;
    }
}
