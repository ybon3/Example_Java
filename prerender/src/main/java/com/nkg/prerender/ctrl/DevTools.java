package com.nkg.prerender.ctrl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkg.prerender.service.PlaywrightService;
import com.nkg.prerender.service.RedisService;

@RestController
@RequestMapping(value = "/devtools",
		produces = MediaType.TEXT_HTML_VALUE)
public class DevTools extends BaseController {

	@Autowired private RedisService redisService;
	@Autowired private PlaywrightService playwrightService;

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

	@RequestMapping(value = "/redis/get",
			method = RequestMethod.GET)
	public String redisAdd(
			@RequestParam String key) throws Exception {
		return redisService.get(key);
	}

	@RequestMapping(value = "/redis/set",
			method = RequestMethod.GET)
	public String redisSet(
			@RequestParam String key,
			@RequestParam String value) throws Exception {
		redisService.set(key, value);
		return "Done";
	}

	@RequestMapping(value = "/redis/remove",
			method = RequestMethod.GET)
	public String redisRemove(
			@RequestParam String key) throws Exception {
		return redisService.remove(key) + "";
	}

	@RequestMapping(value = "/redis/keys",
			method = RequestMethod.GET)
	public String redisKeys() throws Exception {
		return StringUtils.join(redisService.allkeys(), "\r\n");
	}

	@RequestMapping(value = "/redis/size",
			method = RequestMethod.GET)
	public String redisSize() throws Exception {
		return redisService.size() + "";
	}

	@RequestMapping(value = "/redis/clear",
			method = RequestMethod.GET)
	public String redisClear() throws Exception {
		return "Remove " + redisService.remove(redisService.allkeys()) + "keys";
	}

	@RequestMapping(value = "/poolStatus",
			method = RequestMethod.GET)
	public String poolStatus() {
		return playwrightService.poolStatus();
	}
}
