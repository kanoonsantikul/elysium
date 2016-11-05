package com.kanoonsantikul.elysium;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Gdx;

public class InputHandler extends GestureDetector {
    public static final float LONG_PRESS_SECONDS = 0.09f;

    private boolean isDragged = false;
    private boolean isJustClick = true;
    private InputListener listener;

    public interface InputListener {
        public void onClicked(float x, float y);
        public void onPressed(float x, float y);
        public void onDragStart(float x, float y);
        public void onDragEnd(float x, float y);
        public void onDragged(float x, float y);
    }

    public class GestureHandler extends GestureAdapter {
        @Override
        public boolean longPress(float x, float y) {
            if (listener != null && !isJustClick) {
                listener.onPressed(x, flipAxis(y));
            }
            return true;
        }
    }

    public InputHandler (GestureAdapter adapter) {
        super(adapter);
    }

    @Override
    public boolean touchDown (float x, float y, int pointer, int button) {
        isJustClick = false;
        return false;
    }

    @Override
    public boolean touchUp (float x, float y, int pointer, int button) {
        isJustClick = true;

        if (pointer == 0 && button == Buttons.LEFT && listener != null) {
            if (!isDragged) {
                listener.onClicked(x, flipAxis(y));
            } else {
                isDragged = false;
                listener.onDragEnd(x, flipAxis(y));
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged (float x, float y, int pointer) {
        if (pointer == 0 && listener != null) {
            if (!isDragged) {
                isDragged = true;
                listener.onDragStart(x, flipAxis(y));
            }

            listener.onDragged(x , flipAxis(y));
        }
        return true;
    }

    public void setListener (InputListener listener) {
        this.listener = listener;
    }

    private static float flipAxis (float y) {
        return Elysium.HEIGHT - 1 - y;
    }
}
