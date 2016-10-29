package com.kanoonsantikul.elysium;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class ConnectionListener implements ConnectionRequestListener {

	ConnectionManager callBack;

	public ConnectionListener(ConnectionManager callBack){
		this.callBack = callBack;
	}

	public void onConnectDone(ConnectEvent event) {
		if(event.getResult() == WarpResponseResultCode.SUCCESS){
			callBack.onConnectDone(true);
		}else{
			callBack.onConnectDone(false);
		}
	}

	public void onDisconnectDone(ConnectEvent event) {
	}

	@Override
	public void onInitUDPDone (byte result) {
		if(result == WarpResponseResultCode.SUCCESS){
			callBack.isUDPEnabled = true;
		}
	}

}
