
package com.payudon.music.entity;

import java.util.ArrayList;

/**
 * @ClassName: AllData
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年11月30日 下午3:31:40
 * 
 */
@lombok.Data
public class AllData {

	private Category category;
	private Focus focus;
	private New_album new_album;
	private New_song new_song;
	private Playlist playlist;
	private RecomPlaylist recomPlaylist;
	private Toplist toplist;
	private int code;
	private long ts;

	@lombok.Data
	public static class Category {
		private Data data;
		private int group_id;
		private String group_name;
		private ArrayList<Items> items;
		private int code;
	}

	@lombok.Data
	public static class Data {
		private ArrayList<Category> category;
		private ArrayList<Content> content;
		private int id;
		private ArrayList<Object> sub_cube;
		private String title;
		private ArrayList<List> list;
		private Tags tags;
		private int total;
		private int rec;
		private int sort;
		private int size;
		private ArrayList<Song_list> song_list;
		private int type;
		private ArrayList<Type_info> type_info;
		private ArrayList<V_playlist> v_playlist;
		private int page;
		private ArrayList<V_hot> v_hot;
		private ArrayList<Group_list> group_list;
	}

	@lombok.Data
	public static class Items {
		private int item_id;
		private String item_name;
		private String item_desc;
		private int item_new;
		private int item_hot;
		private int show_type;
		private int is_parent;
		private String reimgurl;
		private String item_share_pic;
		private int show_detail;
		private int group_id;
	}

	@lombok.Data
	public static class Focus {
		private Data data;
		private int code;
	}

	@lombok.Data
	public static class Jump_info {
		private int id;
		private String mid;
		private String url;
	}

	@lombok.Data
	public static class Pic_info {
		private String mid;
		private String url;
		private String urlex1;
		private String urlex2;
	}

	@lombok.Data
	public static class Content {
		private String cover;
		private int id;
		private Jump_info jump_info;
		private int listen_num;
		private Pic_info pic_info;
		private String report;
		private String sub_title;
		private String title;
		private int type;
	}

	@lombok.Data
	public static class New_album {
		private Data data;
		private int code;
	}

	@lombok.Data
	public static class Singers {
		private int singer_id;
		private String singer_mid;
		private String singer_name;
	}

	@lombok.Data
	public static class List {
		private int album_id;
		private String album_mid;
		private String album_name;
		private String public_time;
		private ArrayList<Singers> singers;
		private int week;
		private int year;
		private int id;
		private String name;
		private int type;
		private String pic;
		private int listen_num;
		private String show_time;
		private String update_key;
		private ArrayList<Songlist> songlist;
	}

	@lombok.Data
	public static class Tags {
		private ArrayList<Area> area;
		private ArrayList<Company> company;
		private ArrayList<Genre> genre;
		private ArrayList<Type> type;
		private ArrayList<Year> year;
	}

	@lombok.Data
	public static class Area {
		private int id;
		private String name;
		private int area;
		private int start;
		private int end;
		private String tjreport;
	}

	@lombok.Data
	public static class Company {
		private int id;
		private String name;
		private int area;
		private int start;
		private int end;
		private String tjreport;
	}

	@lombok.Data
	public static class Genre {
		private int id;
		private String name;
		private int area;
		private int start;
		private int end;
		private String tjreport;
	}

	@lombok.Data
	public static class Type {
		private int id;
		private String name;
		private int area;
		private int start;
		private int end;
		private String tjreport;
	}

	@lombok.Data
	public static class Year {
		private int id;
		private String name;
		private int area;
		private int start;
		private int end;
		private String tjreport;
	}

	@lombok.Data
	public static class New_song {
		private Data data;
		private int code;
	}

	@lombok.Data
	public static class Action {
		private int alert;
		private int icons;
		private int msgdown;
		private int msgfav;
		private int msgid;
		private int msgpay;
		private int msgshare;
	}

	@lombok.Data
	public static class Album {
		private int id;
		private String mid;
		private String name;
		private String subtitle;
		private String time_public;
		private String title;
	}

	@lombok.Data
	public static class File {
		private int b_30s;
		private int e_30s;
		private int hires_bitdepth;
		private int hires_sample;
		private String media_mid;
		private int size_128mp3;
		private int size_192aac;
		private int size_192ogg;
		private int size_24aac;
		private int size_320mp3;
		private int size_48aac;
		private int size_96aac;
		private int size_ape;
		private int size_dts;
		private int size_flac;
		private int size_hires;
		private int size_try;
		private int try_begin;
		private int try_end;
		private String url;
	}

	@lombok.Data
	public static class Ksong {
		private int id;
		private String mid;
	}

	@lombok.Data
	public static class Mv {
		private int id;
		private String name;
		private String title;
		private String vid;
	}

	@lombok.Data
	public static class Pay {
		private int pay_down;
		private int pay_month;
		private int pay_play;
		private int pay_status;
		private int price_album;
		private int price_track;
		private int time_free;
	}

	@lombok.Data
	public static class Singer {
		private int id;
		private String mid;
		private String name;
		private String title;
		private int type;
		private int uin;
	}

	@lombok.Data
	public static class Volume {
		private float gain;
		private float lra;
		private int peak;
	}

	@lombok.Data
	public static class Song_list {
		private Action action;
		private Album album;
		private int bpm;
		private int data_type;
		private File file;
		private int fnote;
		private int genre;
		private int id;
		private int index_album;
		private int index_cd;
		private int interval;
		private int isonly;
		private Ksong ksong;
		private String label;
		private int language;
		private String mid;
		private int modify_stamp;
		private Mv mv;
		private String name;
		private Pay pay;
		private ArrayList<Singer> singer;
		private int status;
		private String subtitle;
		private String time_public;
		private String title;
		private String trace;
		private int type;
		private String url;
		private int version;
		private Volume volume;
	}

	@lombok.Data
	public static class Type_info {
		private int id;
		private String report;
		private String title;
	}

	@lombok.Data
	public static class Playlist {
		private Data data;
		private int code;
	}

	@lombok.Data
	public static class Creator_info {
		private String avatar;
		private int is_dj;
		private String nick;
		private String taoge_avatar;
		private String taoge_nick;
		private long uin;
		private int vip_type;
	}

	@lombok.Data
	public static class V_playlist {
		private int access_num;
		private String album_pic_mid;
		private ArrayList<Object> censor_remark;
		private int censor_status;
		private int censor_time;
		private int commit_time;
		private String cover_mid;
		private String cover_url_big;
		private String cover_url_medium;
		private String cover_url_small;
		private int create_time;
		private Creator_info creator_info;
		private long creator_uin;
		private String desc;
		private int dirid;
		private int fav_num;
		private int modify_time;
		private String pic_mid;
		private String rcmdcontent;
		private String rcmdtemplate;
		private int score;
		private ArrayList<Integer> song_ids;
		private ArrayList<Integer> song_types;
		private ArrayList<Integer> tag_ids;
		private ArrayList<Object> tag_names;
		private long tid;
		private String title;
		private String tjreport;
	}

	@lombok.Data
	public static class RecomPlaylist {
		private Data data;
		private int code;
	}

	@lombok.Data
	public static class V_hot {
		private String album_pic_mid;
		private long content_id;
		private String cover;
		private long creator;
		private String edge_mark;
		private int id;
		private boolean is_dj;
		private boolean is_vip;
		private String jump_url;
		private int listen_num;
		private String pic_mid;
		private String rcmdcontent;
		private String rcmdtemplate;
		private int rcmdtype;
		private int singerid;
		private String title;
		private String tjreport;
		private int type;
		private String username;
	}

	@lombok.Data
	public static class Toplist {
		private Data data;
		private int code;
	}

	@lombok.Data
	public static class Songlist {
		private int singer_id;
		private String singer_name;
		private int track_id;
		private String track_name;
	}

	@lombok.Data
	public static class Group_list {
		private int group_id;
		private String group_name;
		private int type;
		private ArrayList<List> list;
	}
}
