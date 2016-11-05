package com.kanoonsantikul.elysium;

import java.util.HashMap;

import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

public class NotificationListener implements NotifyListener {

	private ConnectionManager callBack;

	public NotificationListener (ConnectionManager callBack) {
		this.callBack = callBack;
	}

	public void onChatReceived (ChatEvent event) {
	}

	public void onRoomCreated (RoomData event) {
	}

	public void onRoomDestroyed (RoomData event) {
	}

	public void onUpdatePeersReceived (UpdateEvent event) {
		callBack.onGameUpdateReceived(new String(event.getUpdate()));
	}

	public void onUserJoinedLobby (LobbyData event, String username) {
	}

	public void onUserJoinedRoom (RoomData event, String username) {
		callBack.onUserJoinedRoom(event.getId(), username);
	}

	public void onUserLeftLobby (LobbyData event, String username) {
	}

	public void onUserLeftRoom (RoomData roomData, String userName) {
		//callBack.onUserLeftRoom(roomData.getId(), userName);
	}

	@Override
	public void onGameStarted (String sender, String roomId, String nextTurn) {
	}

	@Override
	public void onGameStopped (String sender, String roomId) {
	}

	@Override
	public void onMoveCompleted (MoveEvent event) {
	}

	@Override
	public void onPrivateChatReceived (String sender, String message) {
	}

    @Override
    public void onPrivateUpdateReceived (String arg0, byte[] arg1, boolean arg2){
    }

	@Override
	public void onUserChangeRoomProperty (
            RoomData roomData,
            String userName,
            HashMap<String, Object> properties,
            HashMap<String, String> lockProperties) {
		//int code = Integer.parseInt(properties.get("result").toString());
		//callBack.onResultUpdateReceived(userName, code);
	}

	@Override
	public void onUserPaused (String locationId, boolean isLobby, String userName) {
	}

	@Override
	public void onUserResumed (String locationId, boolean isLobby, String userName) {
	}

    @Override
    public void onNextTurnRequest (String arg0){
    }
}
