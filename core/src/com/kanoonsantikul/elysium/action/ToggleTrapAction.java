package com.kanoonsantikul.elysium;

public class ToggleTrapAction extends Action {
    private World world = World.instance();

    private Tile tile;

    public ToggleTrapAction (Player actor, Tile tile) {
        super(actor);

        this.tile = tile;
    }

    public static void toggleTrap (Player player ,Tile tile, Player actor) {
        for (Trap trap : player.traps) {
            if (trap.getTile() == tile) {
                trap.toggle(actor);
                break;
            }
        }
    }

    @Override
    public void act () {
        if (world.player.getNumber() == Player.PLAYER1) {
            toggleTrap(world.player, tile, (Player)actor);
            toggleTrap(world.enemy, tile, (Player)actor);
        } else {
            toggleTrap(world.enemy, tile, (Player)actor);
            toggleTrap(world.player, tile, (Player)actor);
        }
        setActed(true);
    }

}
