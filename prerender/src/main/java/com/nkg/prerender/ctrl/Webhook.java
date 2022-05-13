package com.nkg.prerender.ctrl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.nkg.prerender.service.LineNotifyService;

/**
 * @author Dante
 *
 * This is a simple and flexible version, but the POST content maybe design a Java object to mapping it is better.
 */
@RestController
public class Webhook extends BaseController {

	@Autowired private LineNotifyService lineNotifyService;

	@RequestMapping(value = "/webhook_azure",
			method = RequestMethod.POST,
			produces = MediaType.TEXT_HTML_VALUE)
	public String azure(
			@RequestBody String input) {

		logger.info("AZURE HOOK COME: " + input);

		JsonObject jsonObj = gson.fromJson(input, JsonObject.class);
		JsonObject data = jsonObj.get("data").getAsJsonObject();
		String status = data.get("status").getAsString();

		JsonObject context = data.get("context").getAsJsonObject();
		String description = context.get("description").getAsString();
		String resourceName = context.get("resourceName").getAsString();
		String timestamp = context.get("timestamp").getAsString();

		/**
		 * Build message, format:
		 * 	{description}
		 *	{status?}:::{resourceName}
		 *	{clock_emoji}:::{timestamp}
		 */
		String message = "\n" + description + "\n";

		if ("Activated".equalsIgnoreCase(status)) {
			message += "\uD83C\uDD98";
		} else if ("Deactivated".equalsIgnoreCase(status)) {
			message += "\u2705";
		} else {
			message += "\u2753";
		}

		message += ":::" + resourceName + "\n\u23F1:::" + timestamp;

		//Send nofity to line and build response
		HashMap	<String,Object> map = new HashMap<>();
		map.put("notify_1", lineNotifyService.notify1(message));
		return gson.toJson(map);
	}
}
