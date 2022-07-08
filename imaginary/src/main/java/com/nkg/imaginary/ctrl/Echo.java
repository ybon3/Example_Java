package com.nkg.imaginary.ctrl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Echo extends BaseController {

	@Value("${version}")
	private String version;

	@RequestMapping(value = "/hello",
			method = RequestMethod.GET)
	public String hello() throws Exception {
		return "OK";
	}

	@RequestMapping(value = "/version",
			method = RequestMethod.GET)
	public String version() throws Exception {
		return version;
	}
}
