
package com.payudon.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;

import com.payudon.music.entity.LyrData;
import com.payudon.music.entity.MusicData.Data;
import com.payudon.music.entity.MusicData.Singer;
import com.payudon.music.entity.MusicData.Songlist;
import com.payudon.music.entity.RadioData;
import com.payudon.music.entity.RadioData.Track_list;
import com.payudon.music.entity.RecommendData;
import com.payudon.music.entity.SearchData;
import com.payudon.music.vo.LyrVO;

/**
 * @ClassName: ParseUtil
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年11月28日 下午2:32:51
 * 
 */
public class ParseUtil {

	public static List<Songlist> parseSonglist(SearchData searchData) {
		List<Songlist> songs = new ArrayList<>();
		List<SearchData.List> searchList = searchData.getData().getSong().getList();
		for (SearchData.List list : searchList) {
			String albummid = list.getAlbum().getMid();
			String albumname = list.getAlbum().getName();
			Integer albumid = list.getAlbum().getId();
			Integer songid = list.getId();
			String songmid = list.getMid();
			String songname = list.getName();
			ArrayList<Singer> singers = new ArrayList<>();
			List<SearchData.Singer> searchSingers = list.getSinger();
			for (Singer searchSinger : searchSingers) {
				singers.add(searchSinger);
			}
			Songlist song = new Songlist();
			Data data = new Data();
			data.setAlbumid(albumid);
			data.setAlbummid(albummid);
			data.setAlbumname(albumname);
			data.setSongid(songid);
			data.setSongmid(songmid);
			data.setSongname(songname);
			data.setSinger(singers);
			song.setData(data);
			songs.add(song);
		}
		return songs;
	}

	public static List<Songlist> parseSonglist(RadioData radioData) {
		List<Songlist> songs = new ArrayList<>();
		ArrayList<Track_list> track_list = radioData.getSonglist().getData().getTrack_list();
		for (Track_list list : track_list) {
			String albummid = list.getAlbum().getMid();
			String albumname = list.getAlbum().getName();
			Integer albumid = list.getAlbum().getId();
			Integer songid = list.getId();
			String songmid = list.getMid();
			String songname = list.getName();
			ArrayList<Singer> singers = new ArrayList<>();
			List<RadioData.Singer> radioSingers = list.getSinger();
			for (Singer radioSinger : radioSingers) {
				singers.add(radioSinger);
			}
			Songlist song = new Songlist();
			Data data = new Data();
			data.setAlbumid(albumid);
			data.setAlbummid(albummid);
			data.setAlbumname(albumname);
			data.setSongid(songid);
			data.setSongmid(songmid);
			data.setSongname(songname);
			data.setSinger(singers);
			song.setData(data);
			songs.add(song);
		}
		return songs;
	}

	public static List<Songlist> parseSonglist(RecommendData recommendData) {
		List<Songlist> songs = new ArrayList<>();
		ArrayList<RecommendData.List> recommendList = recommendData.getData().getList();
		for (RecommendData.List list : recommendList) {
			Songlist song = new Songlist();
			song.setData(list);
			songs.add(song);
		}
		return songs;
	}

	public static LyrData parseLyr(LyrData lyrData) {
		String lyric = lyrData.getLyric();
		lyric = StringEscapeUtils.unescapeHtml4(lyric);
		String[] lyrs = lyric.split("\n");
		List<LyrVO> vos = new ArrayList<>();
		for (int i = 0; i < lyrs.length; i++) {
			lyrs[i] = lyrs[i].substring(1); 
			String[] lyr = lyrs[i].split("]");
			if(lyr.length>1) {
				LyrVO vo = new LyrVO();
				vo.setStartTime(lyr[0]);
				vo.setLyr(lyr[1]);
				vos.add(vo);
			}
		}
		lyrData.setLyrs(vos);
		return lyrData;
	}
}
