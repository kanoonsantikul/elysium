package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;

public class WaitDataAction extends Action{
    public static interface DataListener{
        public void onDataArrive(String data);
    }

    DataListener listener;

    public WaitDataAction(DataListener listener){
        super(null);
        this.listener = listener;
        Gdx.app.log("Waiting data","...");
    }

    public void onDataArrive(String data){
        if(listener != null){
            listener.onDataArrive(data);
        }
        setActed(true);
    }
}
