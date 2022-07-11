package com.dante.ws.ctrl;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.dante.ws.common.util.WebSocketUtil;

@Component
@ServerEndpoint("/webSocketServer/{usernick}")
public class CharRoomController extends BaseController {

	@OnOpen
	public void onOpen(
			@PathParam("usernick") String userNick,
			Session session) {

		String msg = "有新成員[" + userNick + "]加入";
		logger.info(msg);
		WebSocketUtil.addSession(userNick, session);
		WebSocketUtil.broadcast(msg);
	}

	@OnClose
	public void onClose(
			@PathParam("usernick") String userNick,
			Session session) {

		String msg = "成員[" + userNick + "]退出";
		logger.info(msg);
		WebSocketUtil.removeSession(userNick);
		WebSocketUtil.broadcast(msg);
	}

	@OnMessage
	public void onMsg(
			@PathParam("usernick") String userNick,
			String message) {

		String msg = "成員[" + userNick + "] " + message;
		logger.info(msg);
		WebSocketUtil.broadcast(msg);
	}

	@OnError
	public void onError(
			Session session,
			Throwable throwable) {

		logger.error(throwable.getMessage());

		try {
			session.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		throwable.printStackTrace();
	}
}
