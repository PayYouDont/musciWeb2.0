
package com.payudon.music.entity;

import java.util.ArrayList;

/**
 * @ClassName: A
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年8月24日 下午3:44:13
 * 
 */
@lombok.Data
public class MusicData{
	private int code;
	private int color;
	private int comment_num;
	private int cur_song_num;
	private String date;
	private String day_of_year;
	private int song_begin;
	private ArrayList<Songlist> songlist;
	private int total_song_num;
	private String update_time;

	@lombok.Data
	public static class Data {
		private String albumdesc;
		private int albumid;
		private String albummid;
		private String albumname;
		private int alertid;
		private int belongCD;
		private int cdIdx;
		private int interval;
		private int isonly;
		private String label;
		private int msgid;
		private Pay pay;
		private Preview preview;
		private int rate;
		private ArrayList<Singer> singer;
		private int size128;
		private int size320;
		private int size5_1;
		private int sizeape;
		private int sizeflac;
		private int sizeogg;
		private int songid;
		private String songmid;
		private String songname;
		private String songorig;
		private int songtype;
		private String strMediaMid;
		private int stream;
		private int type;
		private String vid;
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
	@lombok.Data
	public static class Vid {
		private Object Fstatus;
	}
	@lombok.Data
	public static class Songlist {
		private String Franking_value;
		private String cur_count;
		private Data data;
		private String in_count;
		private String mb;
		private String old_count;
		private Vid vid;

	}
}
