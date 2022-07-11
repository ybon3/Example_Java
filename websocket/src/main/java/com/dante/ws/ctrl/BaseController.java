package com.dante.ws.ctrl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dante.ws.common.util.GsonUtil;
import com.google.gson.Gson;

/**
 * To provide all Controller common functions
 * <p>
 * @author Dante
 */
public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Gson gson = GsonUtil.create();

	/**
	 * Notice: All {@link Date} field will be parse to "Timestamp" (as long type),
	 * and vice versa.
	 */
	protected <T> T parse(String input, Class<T> clazz) {
		return gson.fromJson(input, clazz);
	}
}
