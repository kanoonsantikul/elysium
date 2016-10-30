package com.kanoonsantikul.elysium;

import java.util.HashMap;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;

import com.badlogic.gdx.Gdx;

public class ConnectionManager{
    public static interface ConnectionStateListener{
        public void onWaitingStarted();
        public void onGameStarted(int userNumber);
        public void onGameUpdateReceived(String message);
    }

    public static final String APP_KEY = "14a611b4b3075972be364a7270d9b69a5d2b24898ac483e32d4dc72b2df039ef";
    public static final String SECRET_KEY = "55216a9a165b08d93f9390435c9be4739888d971a17170591979e5837f618059";

    private static ConnectionManager connectionManager;

    private WarpClient warpClient;
    private ConnectionStateListener listener;
    private String roomId;
    private String username;
    private int userNumber;

    private boolean isConnected = false;
    boolean isUDPEnabled = false;

    public ConnectionManager(){
        initAppwarp();
        warpClient.addConnectionRequestListener(new ConnectionListener(this));
        warpClient.addRoomRequestListener(new RoomListener(this));
        warpClient.addZoneRequestListener(new ZoneListener(this));
        warpClient.addNotificationListener(new NotificationListener(this));
    }

    public static ConnectionManager instance(){
        if(connectionManager == null){
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public void setListener(ConnectionStateListener listener){
        this.listener = listener;
    }

    public void connect(String username){
        this.username = username;
        warpClient.connectWithUserName(username);
    }

    public void disconnect(){
        if(isConnected){
			warpClient.unsubscribeRoom(roomId);
			warpClient.leaveRoom(roomId);
			warpClient.deleteRoom(roomId);
		}

        warpClient.removeConnectionRequestListener(new ConnectionListener(this));
        warpClient.removeZoneRequestListener(new ZoneListener(this));
        warpClient.removeRoomRequestListener(new RoomListener(this));
        warpClient.removeNotificationListener(new NotificationListener(this));
        warpClient.disconnect();
    }

    public void sendGameUpdate(String msg){
        if(isConnected){
            if(isUDPEnabled){
                warpClient.sendUDPUpdatePeers((username+"#@"+msg).getBytes());
            }else{
                warpClient.sendUpdatePeers((username+"#@"+msg).getBytes());
            }
        }
    }

    public void onConnectDone(boolean status){
        Gdx.app.log("onConnectDone: "+status,"");
        if(status){
            warpClient.initUDP();
            warpClient.joinRoomInRange(1, 1, false);
        } else{
            isConnected = false;
            handleError();
        }
    }

    public void onRoomCreated(String roomId){
        if(roomId != null){
            warpClient.joinRoom(roomId);
        }else{
            handleError();
        }
    }

    public void onJoinRoomDone(RoomEvent event){
        Gdx.app.log("onJoinRoomDone: "+event.getResult(), "");
        if(event.getResult() == WarpResponseResultCode.SUCCESS){
            this.roomId = event.getData().getId();
            warpClient.subscribeRoom(roomId);

        } else if(event.getResult() == WarpResponseResultCode.RESOURCE_NOT_FOUND){
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("result", "");
            warpClient.createRoom("elysium", "test", 2, data);

        } else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onRoomSubscribed(String roomId){
        Gdx.app.log("onSubscribeRoomDone: "+roomId, "");
        if(roomId != null){
            isConnected = true;
            warpClient.getLiveRoomInfo(roomId);
        } else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onGetLiveRoomInfo(String[] liveUsers){
        Gdx.app.log("onGetLiveRoomInfo: "+liveUsers.length,"");
        userNumber = liveUsers.length;

        if(liveUsers != null){
            if(liveUsers.length == 2){
                startGame(userNumber);
            } else{
                waitForOtherUser();
            }
        } else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onUserJoinedRoom(String roomId, String username){
        if(!this.username.equals(username)){
            startGame(userNumber);
        }
    }

    public void onGameUpdateReceived(String message){
        String username = message.substring(0, message.indexOf("#@"));
        String data = message.substring(message.indexOf("#@")+2, message.length());

        if(!this.username.equals(username) && listener != null){
            listener.onGameUpdateReceived(data);
        }
    }

    private void initAppwarp(){
        try {
            WarpClient.initialize(APP_KEY, SECRET_KEY);
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleError(){
        if(roomId != null && roomId.length() > 0){
            warpClient.deleteRoom(roomId);
        }
        disconnect();
    }

    private void startGame(int userNumber){
        if(listener != null){
            listener.onGameStarted(userNumber);
        }
    }

    private void waitForOtherUser(){
        if(listener != null){
            listener.onWaitingStarted();
        }
    }

}
