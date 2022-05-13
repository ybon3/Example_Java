package com.nkg.prerender.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.NavigateOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

@Service
public class PlaywrightService extends BaseService {

	@Value("${playwright.headless:true}")
	private boolean headless;

	private GenericObjectPool<Page> pool;

	private Playwright playwright;
	private BrowserType chromium;
	private Browser browser;
	private NavigateOptions naviOptions = new NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE);

	@PostConstruct
	public void init() {
		try {
			//Playwright
			logger.info("Initial browser & playwright ===========================================");
			playwright = Playwright.create();
			chromium = playwright.chromium();
			browser = chromium.launch(new BrowserType.LaunchOptions().setHeadless(headless));

			//ObjectPool
			logger.info("Initial ObjectPool ===========================================");
			GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
			conf.setMaxTotal(16);
			conf.setMaxIdle(8);
			pool = new GenericObjectPool<>(new PageObjectFactory(browser), conf);

		} catch (Exception e) {
			logger.error("init()", e);
		}
	}

	public String prerender(String url) {
		long startTime = System.currentTimeMillis();
		Page page = null;

		try {
			page = pool.borrowObject();
			page.navigate(url, naviOptions);
			return page.innerHTML("head");
		}
		catch (Exception e) {
			logger.error("prerender()", e);
			return "prerender() failure: " + e.getMessage();
		}
		finally {
			// return instance back to the pool
			if (page != null) {
				pool.returnObject(page);
			}

			logger.info("Render used " + (System.currentTimeMillis() - startTime) + " ms. - " + url);
		}
	}

	public String poolStatus() {
		return "=========== STATUS ===========\r\n"
		+ "NumActive: " + pool.getNumActive() + "\r\n"
		+ "NumIdle: " + pool.getNumIdle() + "\r\n"
		+ "NumWaiters: " + pool.getNumWaiters() + "\r\n"
		+ "MaxWaitMillis: " + pool.getMaxWaitMillis() + "\r\n";
	}

	@PreDestroy
	public void destroy() {
		logger.info("Destroy browser & playwright ===========================================");
		if (browser != null) {
			browser.close();
		}
		if (playwright != null) {
			playwright.close();
		}
	}
}

class PageObjectFactory implements PooledObjectFactory<Page>{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Browser browser;

	public PageObjectFactory(Browser browser) {
		this.browser = browser;
	}

	@Override
	public void activateObject(PooledObject<Page> obj) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroyObject(PooledObject<Page> obj) throws Exception {
		obj.getObject().close();
		logger.info("Page closed.");
	}

	@Override
	public PooledObject<Page> makeObject() throws Exception {
		logger.info("Page creating...");
		return new DefaultPooledObject<>(browser.newPage());
	}

	/**
	 * When an object is returned to the pool
	 */
	@Override
	public void passivateObject(PooledObject<Page> obj) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean validateObject(PooledObject<Page> obj) {
		return !obj.getObject().isClosed();
	}

}
