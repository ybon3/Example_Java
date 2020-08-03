package dante.poc.eshop.ctrl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DataSourceIntercetor extends HandlerInterceptorAdapter {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String contextPath = request.getServletContext().getContextPath();
		String himirror = contextPath + "/himirror";
		String xyz = contextPath + "/xyz";

		String uri = request.getRequestURI();
		logger.info("URI:"+ uri);

		if(uri.startsWith(himirror)) {
			request.setAttribute("keyDS", "HIMIRROR_DS");
		}

		else if(uri.startsWith(xyz)) {
			request.setAttribute("keyDS", "XYZ_DS");
		}

		return true;
	}
}
