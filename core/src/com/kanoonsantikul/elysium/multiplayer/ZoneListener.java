package com.kanoonsantikul.elysium;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

public class ZoneListener implements ZoneRequestListener {

	private ConnectionManager callBack;

	public ZoneListener (ConnectionManager callBack) {
		this.callBack = callBack;
	}

	@Override
	public void onCreateRoomDone (RoomEvent event) {
		if (event.getResult()==WarpResponseResultCode.SUCCESS) {
			callBack.onRoomCreated(event.getData().getId());
		} else {
			callBack.onRoomCreated(null);
		}
	}

	@Override
	public void onDeleteRoomDone (RoomEvent event) {

	}

	@Override
	public void onGetAllRoomsDone (AllRoomsEvent event) {

	}

    @Override
    public void onGetAllRoomsCountDone(AllRoomsEvent event){

    }

	@Override
	public void onGetLiveUserInfoDone (LiveUserInfoEvent event) {

	}

	@Override
	public void onGetMatchedRoomsDone (MatchedRoomsEvent event) {

	}

	@Override
	public void onGetOnlineUsersDone (AllUsersEvent event) {

	}

    @Override
    public void onGetOnlineUsersCountDone(AllUsersEvent event){

    }

	@Override
	public void onSetCustomUserDataDone (LiveUserInfoEvent event) {

	}

    @Override
    public void onGetUserStatusDone (LiveUserInfoEvent event){

    }
}
