package com.nkg.imaginary.common.util;

public class HtmlUtil {

	public static final String HEAD_TAG_START = "<head>";
	public static final String HEAD_TAG_END = "</head>";
	public static final int HEAD_TAG_START_LEN = HEAD_TAG_START.length();


	public static String subStrHead(String html) {
		int beginIndex = html.indexOf(HEAD_TAG_START);
		if (beginIndex != -1) {
			int endIndex = html.indexOf(HEAD_TAG_END, beginIndex);
			if (endIndex != -1) {
				return html.substring(beginIndex + HEAD_TAG_START_LEN, endIndex);
			}
			else {
				return html.substring(beginIndex + HEAD_TAG_START_LEN);
			}
		}

		return "";
	}
}
