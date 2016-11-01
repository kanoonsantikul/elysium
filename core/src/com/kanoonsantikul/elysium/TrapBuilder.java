package com.kanoonsantikul.elysium;

public class TrapBuilder{
    private static final Trap[] trapInstance = new Trap[]{
        new Trap(0, 0, 0, null, null),
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

    public static int randomTrapId(){
        double completeWeight = 0.0;
        for(Trap trap : trapInstance){
            completeWeight += trap.getWeight();
        }

        double random = Math.random() * completeWeight;
        double countWeight = 0.0;
        for(Trap trap : trapInstance) {
            countWeight += trap.getWeight();
            if(countWeight >= random){
                return trap.getId();
            }
        }

        return 0;
    }

}
