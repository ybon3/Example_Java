package com.nkg.prerender.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkg.prerender.service.PrerenderService;

@RestController
public class API4Migration extends BaseController {

	@Autowired private PrerenderService prerenderService;

	@RequestMapping(value = "/meta",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_HTML_VALUE)
	public String meta(
			@RequestParam String uri) throws Exception {

		return prerenderService.render(uri);
	}
}
