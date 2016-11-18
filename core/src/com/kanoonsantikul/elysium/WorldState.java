package com.kanoonsantikul.elysium;

public interface WorldState {
    public void enterState();

    public void exitState ();

    public void onClicked (float x, float y);

    public void onPressed (float x, float y);

    public void onDragStart (float x, float y);

    public void onDragEnd (float x, float y);

    public void onDragged (float x, float y);

    public void update ();

}
