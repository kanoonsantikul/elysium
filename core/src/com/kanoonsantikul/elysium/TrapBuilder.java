package com.kanoonsantikul.elysium;

public class TrapBuilder{

    public static Trap build(int id, Tile tile, Player user){
        if(id == 0){
            return null;
        } else if(id == 1){
            return new BearTrap(id, tile, user);
        } else if(id == 2){
            return new BoobyTrap(id, tile, user);
        } else if(id == 3){
            return new TickingTimeBomb(id, tile, user);
        } else if(id == 4){
            return new ExplosiveBomb(id, tile, user);
        } else if(id == 5){
            return new SnareTrap(id, tile, user);
        } else if(id == 6){
            return new FuzzyBomb(id, tile, user);
        } else if(id == 7){
            return new VenomGas(id, tile, user);
        } else if(id == 8){
            return new SpikeTrap(id, tile, user);
        }
        return null;
    }

}
