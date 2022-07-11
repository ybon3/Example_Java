package com.dante.ws.ctrl;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/devtools",
		produces = MediaType.TEXT_HTML_VALUE)
public class DevTools extends BaseController {

	@RequestMapping(value = "/hello",
			method = RequestMethod.GET)
	public String hello() throws Exception {
		return "OK";
	}
}
