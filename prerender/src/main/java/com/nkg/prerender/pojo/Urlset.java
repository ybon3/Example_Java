package com.nkg.prerender.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Urlset {

	@JacksonXmlProperty(localName = "url")
	@JacksonXmlElementWrapper(useWrapping = false)
	private Url[] url;

	public Url[] getUrl() {
		return url;
	}

	public void setUrl(Url[] url) {
		this.url = url;
	}

	public static class Url {

		@JacksonXmlProperty(localName = "loc")
		@JacksonXmlCData
		private String loc;

		public String getLoc() {
			return loc;
		}

		public void setLoc(String loc) {
			this.loc = loc;
		}
	}
}
