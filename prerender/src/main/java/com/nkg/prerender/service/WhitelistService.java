package com.nkg.prerender.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * Format as:
 * <p>
 * www.newkinpogroup.com<br>
 * www.kinpo.com.tw<br>
 * www.ccpt.com.tw<br>
 * ...
 * <p>
 * or String spilt by comma:
 * <p>
 * www.newkinpogroup.com, www.kinpo.com.tw, www.ccpt.com.tw,...
 */
@Service
public class WhitelistService extends BaseService {

	private Set<String> whitelist = ConcurrentHashMap.newKeySet();

	@PostConstruct
	public void init() {
		whitelist.add("www.newkinpogroup.com");
		whitelist.add("www.kinpo.com.tw");
		whitelist.add("www.ccpt.com.tw");
		whitelist.add("www.ickptech.com");
		whitelist.add("www.calcomp.co.th");
		whitelist.add("www.ccau.co.th");
		whitelist.add("www.ccph.com.ph");
		whitelist.add("www.ccbr.com.br");
		whitelist.add("www.himirror.com");
		whitelist.add("www.xyzprinting.com");
		whitelist.add("2021tamsui.tw");
		whitelist.add("www.2021tamsui.tw");
		whitelist.add("dev.2021tamsui.tw");
		whitelist.add("hci-sg.kinpo.com.tw");
		whitelist.add("www.hci.org.tw");
		whitelist.add("www-sg.hci.org.tw");
		whitelist.add("www-v2-sg.kinpo.com.tw");

		logger.info("============== whitelist =============\r\n" + whitelist);
	}

	public boolean isAccept(String uri) {
		try {
			String host = new URI(uri).getHost();

			if (host == null) {
				return false;
			}

			return whitelist.contains(host);
		} catch (URISyntaxException e) {
			//Ignore
			return false;
		}
	}

	/**
	 * split by comma
	 */
	public int reload(String list) {
		Set<String> newList = ConcurrentHashMap.newKeySet();
		newList.addAll(Arrays.asList(list.split(",")));
		whitelist = newList;
		return newList.size();
	}
}
