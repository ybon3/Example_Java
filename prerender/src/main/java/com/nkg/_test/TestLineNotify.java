package com.nkg._test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.nkg.prerender.common.util.GsonUtil;
import com.nkg.prerender.common.util.RESTClient;

public class TestLineNotify {

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("message", "Hello DMKT");
		String url = "https://notify-api.line.me/api/notify";

		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//		headers.add("Authorization", "Bearer DJMbVpu81YU0kEgG0PKjkPtGqKb9EoMjZukFYkk9FwG");
//		System.out.println(GsonUtil.create().toJson(RESTClient.postForm( url, headers, params, RetObject.class)));


		headers.clear();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Authorization", "Bearer hV1ZJhOKT5GHe7TuUPUmNlPstAxgJxjgxgYtuSE5pCH");
		System.out.println(GsonUtil.create().toJson(RESTClient.postForm( url, headers, params, RetObject.class)));
	}

	public static class RetObject {
		public int status;
		public String message;
	}
}