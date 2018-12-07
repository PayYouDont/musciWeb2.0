
package com.payudon.music.entity;

import java.util.ArrayList;

/** 
* @ClassName: RecommendData 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年12月7日 下午4:01:23 
*  
*/
@lombok.Data
public class RecommendData {

	private int code;
	private Data data;
	private String message;
	private int subcode;
	
	@lombok.Data
	public static class Data {
		private String aDate;
		private String albumTips;
		private int color;
		private String company;
		private Company_new company_new;
		private int cur_song_num;
		private String desc;
		private String genre;
		private int id;
		private String lan;
		private ArrayList<List> list;
		private String mid;
		private String name;
		private int radio_anchor;
		private int singerid;
		private String singermblog;
		private String singermid;
		private String singername;
		private int song_begin;
		private int total;
		private int total_song_num;
	}

	@lombok.Data
	public static class Company_new {
		private String brief;
		private String headPic;
		private int id;
		private int is_show;
		private String name;
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
	public static class List extends MusicData.Data{}
}