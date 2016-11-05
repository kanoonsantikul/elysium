package com.kanoonsantikul.elysium;

public class ToggleTrapAction extends Action {
    private World world = World.instance();

    private Tile tile;

    public ToggleTrapAction (Player actor, Tile tile) {
        super(actor);

        this.tile = tile;
    }

    public static void toggleTrap (Player player ,Tile tile) {
        for (int i = 0; i < player.toggleTraps().size(); i++) {
            if (player.toggleTraps().get(i).getTile() == tile) {
                player.toggleTraps().get(i).toggle(actor);
                break;
            }
        }
    }

    @Override
    public void act() {
        if(world.player.getNumber() == Player.PLAYER1) {
            toggleTrap(world.player, tile);
            toggleTrap(world.enemy, tile);
        } else {
            toggleTrap(world.enemy, tile);
            toggleTrap(world.player, tile);
        }
        setActed(true);
    }

}
