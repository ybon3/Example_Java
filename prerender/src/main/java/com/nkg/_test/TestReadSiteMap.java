package com.nkg._test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nkg.prerender.common.util.RESTClient;
import com.nkg.prerender.pojo.Urlset;
import com.nkg.prerender.pojo.Urlset.Url;

public class TestReadSiteMap {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		String url = "https://web-api.newkinpogroup.com/Api/sitemap";

		//read xml from url
		String xmlStr = RESTClient.get(url, String.class);
		System.out.println(xmlStr);

		//
		XmlMapper xmlMapper = new XmlMapper();
		Urlset value = xmlMapper.readValue(xmlStr, Urlset.class);

		for (Url xmlUrl : value.getUrl()) {
			System.out.println(xmlUrl.getLoc());
		}
		//url list refresh
	}

}
