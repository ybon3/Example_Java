package com.nkg.prerender.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.nkg.prerender.common.util.RESTClient;

@Service
public class LineNotifyService extends BaseService {

	@Value("${line.notify.api.url}")
	private String NOTIFY_API_URL;

	@Value("${line.notify.token1}")
	private String notify_token1;


	public RetObject notify1(String message) {
		return notify(notify_token1, message);
	}

	private RetObject notify(String token, String message) {
		Map<String, String> params = new HashMap<>();
		params.put("message", message);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Authorization", token);
		return RESTClient.postForm(NOTIFY_API_URL, headers, params, RetObject.class);
	}

	public static class RetObject {
		public int status;
		public String message;
	}
}
