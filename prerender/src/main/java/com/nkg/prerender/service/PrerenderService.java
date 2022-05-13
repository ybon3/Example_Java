package com.nkg.prerender.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PrerenderService extends BaseService {

	private static final Map<String, Object> keyLocks = new ConcurrentHashMap<>();

	@Autowired private PlaywrightService playwrightService;
	@Autowired private RedisService redisService;

	private boolean isRedisOK = true;
	private Map<String, String> memCache = new ConcurrentHashMap<>();

	public String render(String uri) {
		synchronized (keyLocks.computeIfAbsent(uri, k -> new Object())) {
			String content = getFromRedis(uri);
			if (content == null) {
				content = playwrightService.prerender(uri);
				if (!StringUtils.isEmpty(content)) {
					setIntoRedis(uri, content);
				}
			}

			return content;
		}
	}

	public String renderAnyway(String uri) {
		synchronized (keyLocks.computeIfAbsent(uri, k -> new Object())) {
			String content = playwrightService.prerender(uri);
			setIntoRedis(uri, content);
			return content;
		}
	}

	private String getFromRedis(String key) {
		try {
			if (isRedisOK) {
				return redisService.get(key);
			}
			else {
				return memCache.get(key);
			}
		}
		catch (Exception e) {
			logger.warn("getFromRedis()", e);
			pollingCheckRedisConnection();
			return memCache.get(key);
		}
	}

	private void setIntoRedis(String key, String value) {
		try {
			if (isRedisOK) {
				redisService.set(key, value);
			}
			else {
				memCache.put(key, value);
			}
		}
		catch (Exception e) {
			logger.warn("setIntoRedis()", e);
			pollingCheckRedisConnection();
			memCache.put(key, value);
		}
	}

	/**
	 * When RedisService throws exception, call this try fix status.
	 */
	synchronized private void pollingCheckRedisConnection() {
		if (!isRedisOK) { return; }

		isRedisOK = false;
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				while (!isRedisOK) {
					try {
						Thread.sleep(600000); //default 10 min.
						logger.info("Starting try reconnect Redis...");
						int size = redisService.size();

						//If arrive here means re-connected successful
						logger.info("Redis re-connected successful, size: " + size);
						isRedisOK = true;
						memCache.clear();
					} catch (InterruptedException ie) {
						//Critical error
						logger.error("pollingCheckRedisConnection()", ie);
					} catch (Exception e) {
						//Ignore error from redis
					}
				}
			}
		});
	}
}
