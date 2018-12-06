
package com.payudon.music.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.payudon.music.entity.AllData;
import com.payudon.music.entity.ClassicalData;
import com.payudon.music.entity.HotSongData;
import com.payudon.music.entity.MusicData;
import com.payudon.music.entity.MusicData.Songlist;
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

	public String getAllListUrl() {
		return "https://u.y.qq.com/cgi-bin/musicu.fcg?callback=recom1250129690723727&g_tk=5381&jsonpCallback=recom1250129690723727&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&data={\"comm\":{\"ct\":24},\"category\":{\"method\":\"get_hot_category\",\"param\":{\"qq\":\"\"},\"module\":\"music.web_category_svr\"},\"recomPlaylist\":{\"method\":\"get_hot_recommend\",\"param\":{\"async\":1,\"cmd\":2},\"module\":\"playlist.HotRecommendServer\"},\"playlist\":{\"method\":\"get_playlist_by_category\",\"param\":{\"id\":8,\"curPage\":1,\"size\":40,\"order\":5,\"titleid\":8},\"module\":\"playlist.PlayListPlazaServer\"},\"new_song\":{\"module\":\"QQMusic.MusichallServer\",\"method\":\"GetNewSong\",\"param\":{\"type\":0}},\"new_album\":{\"module\":\"music.web_album_library\",\"method\":\"get_album_by_tags\",\"param\":{\"area\":1,\"company\":-1,\"genre\":-1,\"type\":-1,\"year\":-1,\"sort\":2,\"get_tags\":1,\"sin\":0,\"num\":40,\"click_albumid\":0}},\"toplist\":{\"module\":\"music.web_toplist_svr\",\"method\":\"get_toplist_index\",\"param\":{}},\"focus\":{\"module\":\"QQMusic.MusichallServer\",\"method\":\"GetFocus\",\"param\":{}}}";
	}

	public String getLyrUrl(String songid) {
		return "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg?nobase64=1&musicid=" + songid
				+ "&callback=jsonp1&g_tk=5381&jsonpCallback=jsonp1&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
	}

	public String getHotListUrl(String disstid) {
		return "https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg?type=1&json=1&utf8=1&onlysong=0&disstid="+disstid+"&format=jsonp&g_tk=5381&jsonpCallback=playlistinfoCallback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
	}
	public String getClassicalDataUrl() {
		return 	"https://c.y.qq.com/splcloud/fcgi-bin/fcg_get_diss_by_tag.fcg?picmid=1&rnd=0.21697651721256328&g_tk=5381&jsonpCallback=getPlaylist&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&categoryId=27&sortId=5&sin=0&ein=29";

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

	public AllData getAllData() {
		String text = UrlUtil.getSongList(getAllListUrl());
		JSONObject object = JSONObject.parseObject(text);
		AllData bean = object.toJavaObject(AllData.class);
		return bean;
	}
	
	public HotSongData getHotSongData(String disstid) throws Exception{
		String urlStr = getHotListUrl(disstid);
		String text = UrlUtil.getHotList(urlStr,disstid).toString();
		text = text.substring(text.indexOf("(")+1,text.lastIndexOf(")"));
		JSONObject object = JSONObject.parseObject(text);
		HotSongData bean = object.toJavaObject(HotSongData.class);
		return bean;
	
	}
	
	public ClassicalData getClassicalData() throws Exception{
		String urlStr = getClassicalDataUrl();
		String text = UrlUtil.getClassicalList(urlStr).toString();
		text = text.substring(text.indexOf("(")+1,text.lastIndexOf(")"));
		JSONObject object = JSONObject.parseObject(text);
		ClassicalData bean = object.toJavaObject(ClassicalData.class);
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
		String urlStr = getLyrUrl(songid);
		return UrlUtil.getlyr(urlStr);
	}
}
