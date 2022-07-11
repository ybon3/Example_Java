package com.dante.ws.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

public class WebSocketUtil {
	private static final Map<String, Session> ONLINE_SESSION = new ConcurrentHashMap<>();

	public static void addSession(String userNick, Session session) {
		ONLINE_SESSION.put(userNick, session);
	}

	public static void removeSession(String userNick) {
		ONLINE_SESSION.remove(userNick);
	}

	public static void sendMessage(Session session, String msg) {
		if (session == null) {
			return;
		}

		session.getAsyncRemote().sendText(msg);
	}

	public static void broadcast(String msg) {
		ONLINE_SESSION.forEach((sessionId, session) -> sendMessage(session, msg));
	}
}
