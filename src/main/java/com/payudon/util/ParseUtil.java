
package com.payudon.util;

import java.util.ArrayList;
import java.util.List;

import com.payudon.music.entity.MusicData.Data;
import com.payudon.music.entity.MusicData.Singer;
import com.payudon.music.entity.MusicData.Songlist;
import com.payudon.music.entity.SearchData;

/** 
* @ClassName: ParseUtil 
* @Description: TODO(     ) 
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
			for (SearchData.Singer searchSinger: searchSingers) {
				Singer singer = new Singer();
				singer.setId(searchSinger.getId());
				singer.setMid(searchSinger.getMid());
				singer.setName(searchSinger.getName());
				singers.add(singer);
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
}
