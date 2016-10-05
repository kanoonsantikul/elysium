package com.kanoonsantikul.elysium;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.Input.Buttons;

public class InputHandler extends GestureDetector{
    private boolean isDragged = false;

    public interface InputListener{
        public void onClicked(float x, float y);
        public void onDragStart(float x, float y);
        public void onDragEnd(float x, float y);
        public void onDragged(float x, float y);
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
                listener.onClicked(x, flipAxis(y));
            }
        } else{
            isDragged = false;
            listener.onDragEnd(x, flipAxis(y));
        }
        return true;
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer){
        if(pointer == 0){
            if(!isDragged){
                isDragged = true;
                listener.onDragStart(x, flipAxis(y));
            }

            listener.onDragged(x , flipAxis(y));
        }
        return true;
    }

    public void setListener(InputListener listener){
        this.listener = listener;
    }

    private float flipAxis(float y){
        return Elysium.HEIGHT - 1 - y;
    }
}
