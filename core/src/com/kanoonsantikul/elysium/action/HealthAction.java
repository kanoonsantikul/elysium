package com.kanoonsantikul.elysium;

public class HealthAction extends Action {
    private Player player;
    private int health;

    public HealthAction (Player player, int health) {
        super(player);

        this.player = player;
        this.health = health;
    }

    @Override
    public void act () {
        if (player != null) {
            player.health += health;
        }

        setActed(true);
    }

}
