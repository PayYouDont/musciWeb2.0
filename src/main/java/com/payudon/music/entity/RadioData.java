
package com.payudon.music.entity;

import java.util.ArrayList;

import lombok.EqualsAndHashCode;

/**
 * @ClassName: RadioData
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年12月7日 下午1:59:29
 * 
 */
@lombok.Data
public class RadioData {

	private Radiolist radiolist;
	private Songlist songlist;
	private int code;
	private long ts;

	@lombok.Data
	public static class Radiolist {
		private Data data;
		private int code;
	}

	@lombok.Data
	public static class Data {
		private ArrayList<Radio_list> radio_list;
		private String title;
		private int freshtime;
		private int id;
		private ArrayList<Track_list> track_list;
	}

	@lombok.Data
	public static class List {
		private int id;
		private String title;
		private String listenDesc;
		private int listenNum;
		private String tjreport;
		private String pic_url;
		private int rec_type;
		private String subscript_picurl;
	}

	@lombok.Data
	public static class Radio_list {
		private int id;
		private String title;
		private int group_type;
		private ArrayList<List> list;
		private String radio_list;
		private String tjreport;
	}

	@lombok.Data
	public static class Songlist {
		private Data data;
		private int code;
	}
	@lombok.Data
	@EqualsAndHashCode(callSuper=false)
	public static class Singer extends MusicData.Singer{
		private String title;
		private int type;
		private int uin;
	}

	@lombok.Data
	public static class Album {
		private int id;
		private String mid;
		private String name;
		private String title;
		private String subtitle;
		private String time_public;
	}

	@lombok.Data
	public static class Mv {
		private int id;
		private String vid;
		private String name;
		private String title;
	}

	@lombok.Data
	public static class File {
		private String media_mid;
		private int size_24aac;
		private int size_48aac;
		private int size_96aac;
		private int size_192ogg;
		private int size_192aac;
		private int size_128mp3;
		private int size_320mp3;
		private int size_ape;
		private int size_flac;
		private int size_dts;
		private int size_try;
		private int try_begin;
		private int try_end;
		private String url;
		private int size_hires;
		private int hires_sample;
		private int hires_bitdepth;
		private int b_30s;
		private int e_30s;
	}

	@lombok.Data
	public static class Pay {
		private int pay_month;
		private int price_track;
		private int price_album;
		private int pay_play;
		private int pay_down;
		private int pay_status;
		private int time_free;
	}

	@lombok.Data
	public static class Action {
		private int msgid;
		private int alert;
		private int icons;
		private int msgshare;
		private int msgfav;
		private int msgdown;
		private int msgpay;
	}

	@lombok.Data
	public static class Ksong {
		private int id;
		private String mid;
	}

	@lombok.Data
	public static class Volume {
		private float gain;
		private float peak;
		private float lra;
	}

	@lombok.Data
	public static class Track_list {
		private int id;
		private int type;
		private String mid;
		private String name;
		private String title;
		private String subtitle;
		private ArrayList<Singer> singer;
		private Album album;
		private Mv mv;
		private int interval;
		private int isonly;
		private int language;
		private int genre;
		private int index_cd;
		private int index_album;
		private String time_public;
		private int status;
		private int fnote;
		private File file;
		private Pay pay;
		private Action action;
		private Ksong ksong;
		private Volume volume;
		private String label;
		private String url;
		private int bpm;
		private int version;
		private String trace;
		private int data_type;
		private int modify_stamp;
		private String pingpong;
	}
}
