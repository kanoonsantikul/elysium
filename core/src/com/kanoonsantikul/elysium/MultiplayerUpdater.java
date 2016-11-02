package com.kanoonsantikul.elysium;

import org.json.JSONObject;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MultiplayerUpdater implements
        ConnectionManager.ConnectionStateListener{
    private static final int LOCATION_UPDATE_TYPE = 1;
    private static final int TURN_UPDATE_TYPE = 2;
    private static final int CARD_UPDATE_TYPE = 3;
    private static final int TRAP_UPDATE_TYPE = 4;
    private static final int DATA_UPDATE_TYPE = 5;

    private static MultiplayerUpdater multiplayerUpdater;
    private World world;

    public MultiplayerUpdater(World world){
        this.world = world;
        multiplayerUpdater = this;
    }

    public static MultiplayerUpdater instance(){
        return multiplayerUpdater;
    }

    @Override
    public void onGameStarted(int userNumber) {
    }

    @Override
    public void onWaitingStarted(){
    }

    @Override
    public void onGameUpdateReceived(String message){
        Gdx.app.log("Received update", message);

        try{
            JSONObject data = new JSONObject(message);
            if(data.getInt("type") == LOCATION_UPDATE_TYPE){
                updateLocation(data);
            } else if(data.getInt("type") == TURN_UPDATE_TYPE){
                updateTurn();
            } else if(data.getInt("type") == CARD_UPDATE_TYPE){
                updateCards(data);
            } else if(data.getInt("type") == TRAP_UPDATE_TYPE){
                updateTrap(data);
            } else if(data.getInt("type") == DATA_UPDATE_TYPE){
                updateData(data.getString("message"));
            }
        } catch(Exception e){

        }
    }

    public void sendLocationUpdate(
            BoardObject actor,
            LinkedList<Tile> paths,
            LinkedList<Action> actionQueue){

        try {
            JSONObject data = new JSONObject();
            data.put("type", LOCATION_UPDATE_TYPE);

            if(actor == world.player){
                data.put("actor", "enemy");
            } else{
                return;
            }

            String pathString = "";
            for(int i=0; i<paths.size(); i++){
                pathString += paths.get(i).getNumber() + ":";
            }
            data.put("paths", pathString);

            if(actionQueue != null){
                data.put("haveQueue", true);
            } else{
                data.put("haveQueue", false);
            }

            ConnectionManager.instance().sendGameUpdate(data.toString());
        } catch (Exception e) {
            // exception in sendLocation
        }
    }

    public void sendTurnUpdate(){
        try {
            JSONObject data = new JSONObject();
            data.put("type", TURN_UPDATE_TYPE);
            ConnectionManager.instance().sendGameUpdate(data.toString());
        } catch (Exception e) {
            // exception in sendLocation
        }
    }

    public void sendCardUpdate(LinkedList<Card> cards){
        try{
            JSONObject data = new JSONObject();
            data.put("type", CARD_UPDATE_TYPE);

            String cardString = "";
            for(int i=0; i<cards.size(); i++){
                cardString += cards.get(i).getId() + ":";
            }
            data.put("cards", cardString);
            ConnectionManager.instance().sendGameUpdate(data.toString());
        } catch(Exception e){

        }
    }

    public void sendTrapUpdate(Trap trap){
        try{
            JSONObject data = new JSONObject();
            data.put("type", TRAP_UPDATE_TYPE);
            data.put("trapId", trap.getId());
            data.put("tileNumber", trap.getTile().getNumber());
            ConnectionManager.instance().sendGameUpdate(data.toString());
        } catch(Exception e){

        }
    }

    public void sendDataUpdate(String message){
        try{
            JSONObject data = new JSONObject();
            data.put("type", DATA_UPDATE_TYPE);
            data.put("message", message);
            ConnectionManager.instance().sendGameUpdate(data.toString());
        } catch(Exception e){

        }
    }

    private void updateLocation(JSONObject data){
        try {
            BoardObject actor;
            if(data.getString("actor").equals("player")){
                actor = world.player;
            } else{
                actor = world.enemy;
            }

            LinkedList<Tile> paths = new LinkedList<Tile>();
            String[] pathString = data.getString("paths").split(":");
            for(int i=0; i<pathString.length; i++){
                int number = Integer.parseInt(pathString[i]);
                paths.add(world.tiles.get(number));
            }

            if(data.getBoolean("haveQueue") == true){
                world.actionQueue.add(new MoveAction(
                        actor, paths, world.actionQueue));
            } else{
                world.actionQueue.add(new MoveAction(
                        actor, paths, null));
            }
        } catch (Exception e) {
            // exception in onMoveNotificationReceived
        }
    }

    private void updateTurn(){
        world.turnManager.startTurn();
    }

    private void updateCards(JSONObject data){
        try{
            world.enemy.getCards().clear();

            LinkedList<Card> cards = new LinkedList<Card>();
            String[] cardString = data.getString("cards").split(":");
            for(int i=0; i<cardString.length; i++){
                int id = Integer.parseInt(cardString[i]);
                cards.add(new Card(id));
            }

            world.enemy.setCards(cards);
        } catch(Exception e){

        }
    }

    private void updateTrap(JSONObject data){
        try{
            Trap trap = TrapBuilder.build(
                    data.getInt("trapId"),
                    world.tiles.get(data.getInt("tileNumber")),
                    world.enemy);
            world.enemy.addTrap(trap);
            trap.setVisible(false);
        } catch(Exception e){

        }
    }

    private void updateData(String message){
        for(int i=0 ;i<world.actionQueue.size(); i++){
            if(world.actionQueue.get(i) instanceof WaitDataAction){
                ((WaitDataAction)world.actionQueue.get(i))
                        .onDataArrive(message);
            }
        }
    }

}
