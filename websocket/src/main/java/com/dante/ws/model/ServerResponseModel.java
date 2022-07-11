package com.dante.ws.model;

public class ServerResponseModel {
	private String responseMessage;

	public ServerResponseModel(String responseMessage) {
		super();
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
