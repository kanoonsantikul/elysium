package com.kanoonsantikul.elysium;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.Input.Buttons;

public class InputHandler extends GestureDetector{
    private boolean isDragged = false;

    public interface InputListener{
        public void onClicked(float x, float y);
    }

    private InputListener listener;

    public InputHandler(GestureDetector.GestureListener listener){
        super(listener);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button){
        return true;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer, int button){
        if(!isDragged){
            if(pointer == 0 && button == Buttons.LEFT){
                listener.onClicked(x, Elysium.HEIGHT - 1 - y);
            }
        } else{
            isDragged = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer){
        isDragged = true;
        return true;
    }

    public void setListener(InputListener listener){
        this.listener = listener;
    }
}
