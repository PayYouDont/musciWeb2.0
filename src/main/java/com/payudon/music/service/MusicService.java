
package com.payudon.music.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.payudon.music.config.UrlConfig;
import com.payudon.music.entity.AllData;
import com.payudon.music.entity.FMData;
import com.payudon.music.entity.HotSongData;
import com.payudon.music.entity.MusicData;
import com.payudon.music.entity.MusicData.Songlist;
import com.payudon.music.entity.MusicStyleData;
import com.payudon.music.entity.RadioData;
import com.payudon.music.entity.RecommendData;
import com.payudon.music.entity.SearchData;
import com.payudon.util.UrlUtil;

/**
 * @ClassName: MusicService
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年8月24日 上午9:56:46
 * 
 */
@Service
public class MusicService {

	@Resource
	private UrlConfig urlConfig;
	
	public MusicData getMusicData() {
		String text = UrlUtil.getSongList(urlConfig.getSongDataUrl());
		JSONObject object = JSONObject.parseObject(text);
		MusicData bean = object.toJavaObject(MusicData.class);
		return bean;
	}

	public SearchData getSearchData(String search) throws Exception {
		String urlStr = urlConfig.getSearchDataUrl();
		urlStr = urlStr.replace("{searchData}", search);
		String json = UrlUtil.getSearchList(urlStr);
		JSONObject object = JSONObject.parseObject(json);
		SearchData list = object.toJavaObject(SearchData.class);
		return list;
	}

	public AllData getAllData() {
		String text = UrlUtil.getSongList(urlConfig.getAllData());
		JSONObject object = JSONObject.parseObject(text);
		AllData bean = object.toJavaObject(AllData.class);
		return bean;
	}
	
	public HotSongData getHotSongData(String disstid) throws Exception{
		String urlStr = urlConfig.getHotDataUrl();
		urlStr = urlStr.replace("{disstid}", disstid);
		String text = UrlUtil.getHotList(urlStr,disstid).toString();
		text = text.substring(text.indexOf("(")+1,text.lastIndexOf(")"));
		JSONObject object = JSONObject.parseObject(text);
		HotSongData bean = object.toJavaObject(HotSongData.class);
		return bean;
	
	}
	public MusicStyleData getMusicStyleData(String urlStr) throws Exception{
		String text = UrlUtil.getClassicalList(urlStr).toString();
		text = text.substring(text.indexOf("(")+1,text.lastIndexOf(")"));
		JSONObject object = JSONObject.parseObject(text);
		MusicStyleData bean = object.toJavaObject(MusicStyleData.class);
		return bean;
	}
	
	public MusicStyleData getPopularData() throws Exception{
		return getMusicStyleData(urlConfig.getPopularDataUrl());
	}
	
	public MusicStyleData getClassicalData() throws Exception{
		return getMusicStyleData(urlConfig.getClassicalDataUrl());
	}
	
	public MusicStyleData getPureData() throws Exception{
		return getMusicStyleData(urlConfig.getPureDataUrl());
	}
	
	public FMData getFMData() throws Exception{
		String text = UrlUtil.getSearchList(urlConfig.getFMDataUrl());
		JSONObject object = JSONObject.parseObject(text);
		FMData bean = object.toJavaObject(FMData.class);
		return bean;
	}
	public RadioData getRadioData(Integer radioid) throws Exception{
		String text = UrlUtil.getSongList(urlConfig.getRaidoDataUrl(radioid));
		JSONObject object = JSONObject.parseObject(text);
		RadioData bean = object.toJavaObject(RadioData.class);
		return bean;
	}
	public RecommendData getRecommendData(String albummId) throws Exception{
		String text = UrlUtil.getSongList(urlConfig.getAlbumDataUrl(albummId));
		JSONObject object = JSONObject.parseObject(text);
		RecommendData bean = object.toJavaObject(RecommendData.class);
		return bean;
	}
	public List<Songlist> getSonglist(HotSongData hotSongData) {
		List<HotSongData.Songlist> hotSonglist = hotSongData.getCdlist().get(0).getSonglist();
		List<Songlist> songList = new ArrayList<>();
		for (HotSongData.Songlist hots : hotSonglist) {
			MusicData.Data data = hots;
			Songlist song = new Songlist();
			song.setData(data);
			songList.add(song);
		}
		return songList;
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
		return UrlUtil.getlyr(urlConfig.getLyrDataUrl(songid));
	}
}
