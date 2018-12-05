
package com.payudon.music.entity;

import java.util.ArrayList;

/** 
* @ClassName: HotData 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年12月4日 上午10:14:38 
*  
*/
@lombok.Data
public class HotSongData {

	private int code;
	private int subcode;
	private int accessed_plaza_cache;
	private int accessed_favbase;
	private String login;
	private int cdnum;
	private ArrayList<Cdlist> cdlist;
	private int realcdnum;
	
	@lombok.Data
	public static class Tags {
		private int id;
		private String name;
		private int pid;
	}
	
	@lombok.Data
	public static class Pay {
		private int payalbum;
		private int payalbumprice;
		private int paydownload;
		private int payinfo;
		private int payplay;
		private int paytrackmouth;
		private int paytrackprice;
		private int timefree;
	}
	
	@lombok.Data
	public static class Preview {
		private int trybegin;
		private int tryend;
		private int trysize;
	}

	@lombok.Data
	public static class Singer {
		private int id;
		private String mid;
		private String name;
	}
	public static class Songlist extends MusicData.Data{
		
	}

	@lombok.Data
	public static class Cdlist {
		private String disstid;
		private int dirid;
		private String coveradurl;
		private int dissid;
		private String login;
		private String uin;
		private String encrypt_uin;
		private int owndir;
		private String dissname;
		private String logo;
		private String pic_mid;
		private String album_pic_mid;
		private int pic_dpi;
		private int isAd;
		private String desc;
		private int ctime;
		private int mtime;
		private String headurl;
		private String ifpicurl;
		private String nick;
		private String nickname;
		private int type;
		private int singerid;
		private String singermid;
		private int isvip;
		private int isdj;
		private ArrayList<Tags> tags;
		private int songnum;
		private String songids;
		private String songtypes;
		private int disstype;
		private String dir_pic_url2;
		private int song_update_time;
		private int song_update_num;
		private int total_song_num;
		private int song_begin;
		private int cur_song_num;
		private ArrayList<Songlist> songlist;
		private int visitnum;
		private int cmtnum;
		private int buynum;
		private String scoreavage;
		private int scoreusercount;
	}
}
