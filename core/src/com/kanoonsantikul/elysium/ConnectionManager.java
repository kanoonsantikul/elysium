package com.kanoonsantikul.elysium;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;

import com.badlogic.gdx.Gdx;

public class ConnectionManager{
    public static final String APP_KEY = "14a611b4b3075972be364a7270d9b69a5d2b24898ac483e32d4dc72b2df039ef";
    public static final String SECRET_KEY = "55216a9a165b08d93f9390435c9be4739888d971a17170591979e5837f618059";

    private static ConnectionManager connectionManager;

    private WarpClient warpClient;
    private ConnectionRequestListener listener;
    private String username;

    public ConnectionManager(){
        initAppwarp();

        listener = new ConnectionRequestListener(){
               @Override
               public void onConnectDone(ConnectEvent event){
                   if(event.getResult() == WarpResponseResultCode.SUCCESS){
                       Gdx.app.log("yipee I have connected","");
                   }
               }

               @Override
               public void onDisconnectDone(ConnectEvent event){
                   Gdx.app.log("On Disconnected invoked","");
               }

               @Override
               public void onInitUDPDone(byte resultCode){
                   //throw new NotImplementedException();
               }
        };

        warpClient.addConnectionRequestListener(listener);
    }

    public static ConnectionManager instance(){
        if(connectionManager == null){
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public void connect(String username){
        this.username = username;
        warpClient.connectWithUserName(username);
    }

    public void disconnect(){
        warpClient.removeConnectionRequestListener(listener);
        warpClient.disconnect();
    }

    private void initAppwarp(){
        try {
            WarpClient.initialize(APP_KEY, SECRET_KEY);
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
