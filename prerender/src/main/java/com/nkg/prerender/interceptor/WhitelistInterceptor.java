package com.nkg.prerender.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nkg.prerender.service.WhitelistService;

@Component
public class WhitelistInterceptor implements HandlerInterceptor {

	@Autowired private WhitelistService whitelistService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getParameter("uri");

		if (StringUtils.isBlank(uri)) {
			response.setStatus(400); //Bad Request
			return false;
		}

		if (!whitelistService.isAccept(uri)) {
			response.setStatus(406); //Not Acceptable
			return false;
		}

		return true;
	}

}
