package com.payudon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
	public static String getSearchList(String urlStr) throws Exception {
		StringBuffer sb = connection(urlStr, null);
		return sb.substring(sb.indexOf("(")+1,sb.indexOf(")"));
	}
	public static StringBuffer getVkey(String urlStr) throws Exception {
		String referer = "https://y.qq.com/portal/player.html";
		return connection(urlStr, referer);
	}
	public static StringBuffer getlyr(String urlStr) throws Exception {
		String referer = "https://y.qq.com/portal/playlist.html";
		return connection(urlStr, referer);
	}
	public static StringBuffer getHotList(String urlStr,String disstid) throws Exception {
		String referer = "https://y.qq.com/n/yqq/playlist/"+disstid+".html";
		return connection(urlStr, referer);
	}
	public static StringBuffer getClassicalList(String urlStr) throws Exception {
		String referer = "https://y.qq.com/portal/playlist.html";
		return connection(urlStr, referer);
	}
	public static StringBuffer connection(String urlStr,String referer) throws Exception {
		URL url = new URL(urlStr);
		// 初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		// 设置User-Agent 加上下面这句后便可进行爬取
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		if(referer!=null) {
			connection.setRequestProperty("referer", referer);	
		}
		connection.connect();// 连接会话

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String str = null;
		while ((str = in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		return sb;
	}
	/*public static String getAlbumData(String albumId) {
		return "https://c.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg?albummid="+albumId+"&g_tk=5381&jsonpCallback=albuminfoCallback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
	}
	public static void main(String[] args) throws Exception {
		String albummid = "002FVfzB0jzfKi";
		String urlStr = getAlbumData(albummid);
		String text = getSongList(urlStr);
		System.out.println(text);
		JSONObject object = JSONObject.parseObject(text);
		RecommendData bean = object.toJavaObject(RecommendData.class);
		List<Songlist> list = ParseUtil.parseSonglist(bean);
		System.out.println(list);
	}*/
}
