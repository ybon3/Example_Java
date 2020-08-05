package dante.poc.eshop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import dante.poc.eshop.multitenancy.MultiTenantContext;

/**
 * 這邊設定 request 進來如何判斷對應 tenantId 的規則
 */
public class EshopMultiTenantInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String contextPath = request.getServletContext().getContextPath();
		String uri = request.getRequestURI();

		//Use Enum maybe better
		if(uri.startsWith(contextPath + "/himirror")) {
			MultiTenantContext.setTenantId("himirror");
			return true;
		}
		else if(uri.startsWith(contextPath + "/xyz")) {
			MultiTenantContext.setTenantId("xyz");
			return true;
		}

		response.setStatus(406);
		response.setHeader("msg", "Given uri not matched merchant.");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		MultiTenantContext.clear();
	}
}
