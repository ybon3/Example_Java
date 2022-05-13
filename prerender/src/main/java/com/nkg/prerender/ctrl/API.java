package com.nkg.prerender.ctrl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkg.prerender.service.LineNotifyService;
import com.nkg.prerender.service.PrerenderService;

@RestController
@RequestMapping(value = "/api",
		produces = MediaType.TEXT_HTML_VALUE)
public class API extends BaseController {

	@Autowired private LineNotifyService lineNotifyService;
	@Autowired private PrerenderService prerenderService;

	@RequestMapping(value = "/prerender",
			method = RequestMethod.GET)
	public String prerender(
			@RequestParam String uri) throws Exception {

		return prerenderService.render(uri);
	}

	@RequestMapping(value = "/prerender/refresh",
			method = RequestMethod.GET)
	public String renderAnyway(
			@RequestParam String uri) throws Exception {

		return prerenderService.renderAnyway(uri);
	}

	@RequestMapping(value = "/notify",
			method = RequestMethod.GET)
	public String notify(
			@RequestParam String message) throws Exception {

		HashMap	<String,Object> map = new HashMap<>();
		map.put("notify_1", lineNotifyService.notify1(message));
		return gson.toJson(map);
	}
}
