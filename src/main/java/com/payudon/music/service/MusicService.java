
package com.payudon.music.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.payudon.music.entity.MusicData;
import com.payudon.music.entity.SearchData;
import com.payudon.music.entity.MusicData.Songlist;
import com.payudon.util.UrlUtil;

import lombok.Data;

/**
 * @ClassName: MusicService
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年8月24日 上午9:56:46
 * 
 */
@Service
@Data
public class MusicService {
	
	private List<Songlist> songlist;
	
	public String getSearchListUrl(String search) {
		return "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?ct=24&qqmusic_ver=1298&new_json=1&remoteplace=txt.yqq.song&searchid=71064181038426066&t=0&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=1&n=20&w="
				+ search
				+ "&g_tk=5381&jsonpCallback=MusicJsonCallback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
	}

	public String getSongListUrl() {
		Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?" + "tpl=3&page=detail&date=" + dateStr
				+ "&topid=27&type=top&" + "song_begin=0&song_num=30&g_tk=5381&jsonpCallback=MusicJsonCallbacktoplist&"
				+ "loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&"
				+ "notice=0&platform=yqq&needNewCode=0";
	}

	public String getLyrUrl(String songid) {
		return "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg?nobase64=1&musicid=" + songid
				+ "&callback=jsonp1&g_tk=5381&jsonpCallback=jsonp1&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
	}

	public MusicData getMusicData() {
		String text = UrlUtil.getSongList(getSongListUrl());
		JSONObject object = JSONObject.parseObject(text);
		MusicData bean = object.toJavaObject(MusicData.class);
		return bean;
	}

	public SearchData getSearchData(String search) throws Exception {
		String urlStr = getSearchListUrl(search);
		String json = UrlUtil.getSearchList(urlStr);
		JSONObject object = JSONObject.parseObject(json);
		SearchData list = object.toJavaObject(SearchData.class);
		return list;
	}

	public StringBuffer getVkey(String urlStr) throws Exception {
		return UrlUtil.getVkey(urlStr);
	}

	public InputStream getMusicStream(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		connection.connect();
		return connection.getInputStream();
	}

	public StringBuffer getlyr(String songid) throws Exception {
		String urlStr = getLyrUrl(songid);
		return UrlUtil.getlyr(urlStr);
	}
}
