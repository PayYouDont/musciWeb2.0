package com.payudon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
		URL url = new URL(urlStr);
		// 初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		// 设置User-Agent 加上下面这句后便可进行爬取
		connection.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		connection.connect();// 连接会话
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String str = null;
		while ((str = in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		String jsonStr = sb.substring(18,sb.length()-1);
		return jsonStr;
	}
	public static StringBuffer getVkey(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		// 初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		// 设置User-Agent 加上下面这句后便可进行爬取
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		connection.setRequestProperty("referer", "https://y.qq.com/portal/player.html");
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
	public static StringBuffer getlyr(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		// 初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		// 设置User-Agent
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		connection.setRequestProperty("referer", "https://y.qq.com/portal/playlist.html");
		connection.connect();// 连接会话
		InputStream is = connection.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf8"));
		StringBuffer sb = new StringBuffer();
		String str = null;
		while ((str = in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		return sb;
	}
	public static StringBuffer getHotList(String urlStr,String disstid) throws Exception {
		URL url = new URL(urlStr);
		// 初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		// 设置User-Agent
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
		connection.setRequestProperty("referer", "https://y.qq.com/n/yqq/playlist/"+disstid+".html");
		connection.connect();// 连接会话
		InputStream is = connection.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf8"));
		StringBuffer sb = new StringBuffer();
		String str = null;
		while ((str = in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		return sb;
	}
	/*public static String getHotListUrl(String disstid) {
		return "https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg?type=1&json=1&utf8=1&onlysong=0&disstid="+disstid+"&format=jsonp&g_tk=5381&jsonpCallback=playlistinfoCallback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
	}
	public static void main(String[] args) throws Exception {
		String disstid = "5813775548";
		String urlStr = getHotListUrl(disstid);
		String text = UrlUtil.getHotList(urlStr,disstid).toString();
		text = text.substring(text.indexOf("(")+1,text.lastIndexOf(")"));
		JSONObject object = JSONObject.parseObject(text);
		HotSongData bean = object.toJavaObject(HotSongData.class);
		System.out.println(bean.getCdlist().get(0).getSonglist());
	}*/
}
