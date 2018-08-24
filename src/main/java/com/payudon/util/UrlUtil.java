package com.payudon.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlUtil {

	public static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);
	
	public static Document httpsData(String url) {
		try {
			 Document doc = Jsoup.connect(url).get();
			 return doc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getSongList(String url) {
		Document doc = httpsData(url);
		Elements body = doc.getElementsByTag("body");
		if(body.size()>0) {
			String text = body.get(0).text();
			text = text.substring(text.indexOf("(")+1,text.lastIndexOf(")"));
			return text;
		}
		return null;
	}
	
}
