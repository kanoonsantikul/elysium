package com.kanoonsantikul.elysium;

public class TrapBuilder{
    private static final Trap[] trapInstance = new Trap[]{
        new Trap(0, 0, null, null),
        new BearTrap(null, null),
        new BoobyTrap(null, null),
        new TickingTimeBomb(null, null),
        new ExplosiveBomb(null, null),
        new SnareTrap(null, null),
        new FuzzyBomb(null, null),
        new VenomGas(null, null),
        new SpikeTrap(null, null)
    };

    public static Trap build(int id, Tile tile, Player user){
        return trapInstance[id].createCopy(tile, user);
    }

    public static Trap getInstance(int id){
        return trapInstance[id];
    }
}
