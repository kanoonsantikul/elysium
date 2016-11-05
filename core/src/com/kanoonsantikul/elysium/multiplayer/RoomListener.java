package com.kanoonsantikul.elysium;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

public class RoomListener implements RoomRequestListener {

	private ConnectionManager callBack;

	public RoomListener (ConnectionManager callBack) {
		this.callBack = callBack;
	}

	public void onGetLiveRoomInfoDone (LiveRoomInfoEvent event) {
		if (event.getResult() == WarpResponseResultCode.SUCCESS) {
			callBack.onGetLiveRoomInfo(event.getJoinedUsers());
		} else {
			callBack.onGetLiveRoomInfo(null);
		}
	}

	public void onJoinRoomDone (RoomEvent event) {
		callBack.onJoinRoomDone(event);
	}

	public void onLeaveRoomDone (RoomEvent event) {

	}

	public void onSetCustomRoomDataDone (LiveRoomInfoEvent event) {

	}

	public void onSubscribeRoomDone (RoomEvent event) {
		if (event.getResult() == WarpResponseResultCode.SUCCESS) {
			callBack.onRoomSubscribed(event.getData().getId());
		} else {
			callBack.onRoomSubscribed(null);
		}
	}

	public void onUnSubscribeRoomDone (RoomEvent event) {

	}

	public void onUpdatePropertyDone (LiveRoomInfoEvent event) {

	}

	@Override
	public void onLockPropertiesDone (byte result) {

	}

	@Override
	public void onUnlockPropertiesDone (byte result) {

	}

    @Override
    public void onLeaveAndUnsubscribeRoomDone (RoomEvent event){

    }

    @Override
    public void onJoinAndSubscribeRoomDone (RoomEvent event){

    }
}
