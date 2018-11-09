
package com.payudon.music.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.payudon.music.entity.MusicList;
import com.payudon.util.JsonWrapper;
import com.payudon.util.UrlUtil;

import lombok.Data;

/** 
* @ClassName: MusicService 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年8月24日 上午9:56:46 
*  
*/
@Service
@Data
public class MusicService {
	private Date date = new Date(new Date().getTime()-24*60*60*1000);
	private String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
	private String songListUrl = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?" +
			"tpl=3&page=detail&date="+dateStr+"&topid=27&type=top&" +
			"song_begin=0&song_num=30&g_tk=5381&jsonpCallback=MusicJsonCallbacktoplist&" +
			"loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&" +
			"notice=0&platform=yqq&needNewCode=0";
	
	public MusicList getList() {
		String text = UrlUtil.getSongList(getSongListUrl());
		JSONObject object = JSONObject.parseObject(text);
		MusicList bean = object.toJavaObject(MusicList.class);
		return bean;
	}
	public StringBuffer getVkey(String urlStr) throws Exception{
		URL url = new URL(urlStr);
		//初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接  
		//设置User-Agent 加上下面这句后便可进行爬取
		connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		connection.setRequestProperty("referer","https://y.qq.com/portal/player.html");
		connection.connect();// 连接会话 
		
		BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream(),"utf-8"));   
		StringBuffer sb = new StringBuffer(); 
	    String str = null;
	    while((str = in.readLine()) != null) {
    		sb.append(str);
    	}
	    in.close();
		return sb;
	}
	public InputStream getMusicStream(String urlStr) throws Exception{
		URL url = new URL(urlStr);
		//初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接  
		connection.connect();// 连接会话 
		return connection.getInputStream();
	}
	public StringBuffer search(String urlStr,String encoding) throws Exception{
		URL url = new URL(urlStr);
		//初始化一个链接到那个url的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接  
		//设置User-Agent 
		connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		connection.setRequestProperty("referer","https://y.qq.com/portal/playlist.html");
		connection.connect();// 连接会话 
		InputStream is = connection.getInputStream();
		BufferedReader in = new BufferedReader( new InputStreamReader(is,encoding));   
		StringBuffer sb = new StringBuffer(); 
	    String str = null;
	    while((str = in.readLine()) != null) {
    		sb.append(str);
    	}
	    in.close();
		return sb;
	}
	public StringBuffer getlyr(String songid) throws Exception {
		String urlStr = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg?nobase64=1&musicid=" + songid
				+ "&callback=jsonp1&g_tk=5381&jsonpCallback=jsonp1&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
		return search(urlStr, "UTF-8");
	}
}
