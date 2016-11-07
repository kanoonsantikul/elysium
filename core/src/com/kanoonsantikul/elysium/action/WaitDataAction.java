package com.kanoonsantikul.elysium;

public class WaitDataAction extends Action {
    
    public static interface DataListener {
        public void onDataArrive(String data);
    }

    private DataListener listener;

    public WaitDataAction (DataListener listener) {
        super(null);

        this.listener = listener;
    }

    public void onDataArrive (String data) {
        if (listener != null) {
            listener.onDataArrive(data);
        }
        setActed(true);
    }
}
