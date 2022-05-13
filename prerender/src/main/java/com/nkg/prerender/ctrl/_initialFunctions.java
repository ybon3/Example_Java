package com.nkg.prerender.ctrl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class _initialFunctions {

	@PostConstruct
	public void init() throws Exception {
		//googleAnalyticsService.updateGaCount("2021-01-01", "2021-12-31");
		//eshopOrderService.syncXYZEShop();
	}
}
