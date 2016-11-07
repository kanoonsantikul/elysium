package com.kanoonsantikul.elysium;

public abstract class DelayAction extends Action {
    public abstract void enter();
    public abstract void exit();

    private long startTime = 0;
    private long milliSecond;

    public DelayAction (long milliSecond) {
        super(null);

        this.milliSecond = milliSecond;
    }

    @Override
    public void act() {
        if (startTime == 0) {
            enter();
            startTime = System.currentTimeMillis();
        } else {
            if (System.currentTimeMillis() - startTime >= milliSecond) {
                exit();
                setActed(true);
            }
        }
    }

}
