package com.kanoonsantikul.elysium;

public class ShowTextAction extends DelayAction{
    public static final int NOTIFY_NO_MATERIAL_TEXT = 1;

    private static final long DELAY_MILLI = 2000;

    private World world = World.instance();
    private int text;

    public ShowTextAction(int text){
        super(DELAY_MILLI);
        this.text = text;
    }

    public void enter(){
        world.notifyText = text;
    }

    public void exit(){
        world.notifyText = 0;
    }
}
