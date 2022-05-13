package com.nkg.prerender.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * Low level API for access Redis.
 */
@Service
public class RedisService extends BaseService {
	@Autowired private RedisTemplate<String, String> stringRedisTemplate;

	@Value("${redis.key.expire.days:3}")
	private int REDIS_KEY_EXPIRE_DAYS; // default 3 days

	public void refreshKeyExpire(String key) {
		stringRedisTemplate.expire(key, REDIS_KEY_EXPIRE_DAYS, TimeUnit.DAYS);
	}

	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value, REDIS_KEY_EXPIRE_DAYS, TimeUnit.DAYS);
	}

	public String get(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			refreshKeyExpire(key);
		}
		return value;
	}

	public long getExpire(String key) {
		return stringRedisTemplate.getExpire(key, TimeUnit.MINUTES);
	}

	public boolean remove(String key) {
		return stringRedisTemplate.delete(key);
	}

	public long remove(String... keys) {
		return stringRedisTemplate.delete(Arrays.asList(keys));
	}

	public long remove(Collection<String> keys) {
		return stringRedisTemplate.delete(keys);
	}

	/**
	 * @see <a href="http://redis.io/commands/keys">Redis Documentation: KEYS</a>
	 */
	public Set<String> keys(String pattern) {
		return stringRedisTemplate.keys(pattern);
	}

	public Set<String> allkeys() {
		return stringRedisTemplate.keys("*");
	}

	public int size() {
		return stringRedisTemplate.keys(new String("*")).size();
	}
}
