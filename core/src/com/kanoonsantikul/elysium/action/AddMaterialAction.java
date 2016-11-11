package com.kanoonsantikul.elysium;

public class AddMaterialAction extends Action {
    private Player player;
    private int material;

    public AddMaterialAction (Player player, int material) {
        super(player);

        this.player = player;
        this.material = material;
    }

    @Override
    public void act () {
        if (player != null) {
            player.material += material;
        }

        setActed(true);
    }

}
