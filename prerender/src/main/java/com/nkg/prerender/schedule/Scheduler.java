package com.nkg.prerender.schedule;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nkg.prerender.common.util.RESTClient;
import com.nkg.prerender.pojo.Urlset;
import com.nkg.prerender.pojo.Urlset.Url;
import com.nkg.prerender.service.PrerenderService;

@Component
public class Scheduler {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${schedule.sitemap.url}")
	private String url = "https://web-api.newkinpogroup.com/Api/sitemap";

	@Autowired private PrerenderService prerenderService;

	@PostConstruct
	public void init() {
		//force run in startup
		//reloadBySitemap();
	}

	@Scheduled(cron = "${cron.reload-by-sitemap}")
	public void reloadBySitemap() {
		logger.info("reloadBySitemap() Running...");

		try {
			//read xml from url
			String xmlStr = RESTClient.get(url, String.class);
			logger.info(xmlStr);

			//Parsing
			XmlMapper xmlMapper = new XmlMapper();
			Urlset value = xmlMapper.readValue(xmlStr, Urlset.class);

			//url list refresh
			for (Url xmlUrl : value.getUrl()) {
				prerenderService.renderAnyway(xmlUrl.getLoc());
			}
		} catch (Exception e) {
			logger.error("reloadBySitemap()", e);
		}
	}
}
