
package com.payudon.music.entity;

import java.util.ArrayList;

import lombok.Data;

/**
 * @ClassName: SearchList
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年11月28日 上午11:42:49
 * 
 */
@Data
public class SearchData {
	private int code;
	private Data data;
	private String message;
	private String notice;
	private int subcode;
	private int time;
	private String tips;

	@lombok.Data
	public static class Data {
		private String keyword;
		private int priority;
		private ArrayList<Object> qc;
		private Semantic semantic;
		private Song song;
		private int tab;
		private ArrayList<Object> taglist;
		private int totaltime;
		private Zhida zhida;
	}

	@lombok.Data
	public static class Semantic {
		private int curnum;
		private int curpage;
		private ArrayList<Object> list;
		private int totalnum;
	}

	@lombok.Data
	public static class Song {
		private int curnum;
		private int curpage;
		private ArrayList<List> list;
		private int totalnum;
	}

	@lombok.Data
	public static class Action {
		private int alert;
		private int icons;
		private int msg;
	}

	@lombok.Data
	public static class Album {
		private int id;
		private String mid;
		private String name;
		private String subtitle;
		private String title;
		private String title_hilight;
	}

	@lombok.Data
	public static class File {
		private String media_mid;
		private int size_128;
		private int size_320;
		private int size_aac;
		private int size_ape;
		private int size_dts;
		private int size_flac;
		private int size_ogg;
		private int size_try;
		private String strMediaMid;
		private int try_begin;
		private int try_end;
	}

	@lombok.Data
	public static class Ksong {
		private int id;
		private String mid;
	}

	@lombok.Data
	public static class Mv {
		private int id;
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
		private String title_hilight;
		private int type;
		private int uin;
	}

	@lombok.Data
	public static class Volume {
		private float gain;
		private float lra;
		private float peak;
	}

	@lombok.Data
	public static class List {
		private Action action;
		private Album album;
		private int chinesesinger;
		private String desc;
		private String desc_hilight;
		private String docid;
		private File file;
		private int fnote;
		private int genre;
		private ArrayList<Object> grp;
		private int id;
		private int index_album;
		private int index_cd;
		private int interval;
		private int isonly;
		private Ksong ksong;
		private int language;
		private String lyric;
		private String lyric_hilight;
		private String mid;
		private Mv mv;
		private String name;
		private int newStatus;
		private int nt;
		private Pay pay;
		private int pure;
		private ArrayList<Singer> singer;
		private int status;
		private String subtitle;
		private int t;
		private int tag;
		private String time_public;
		private String title;
		private String title_hilight;
		private int type;
		private String url;
		private int ver;
		private Volume volume;
	}

	@lombok.Data
	public static class Zhida {
		private int type;
		private Zhida_singer zhida_singer;
	}

	@lombok.Data
	public static class Zhida_singer {
		private int albumNum;
		private ArrayList<Hotalbum> hotalbum;
		private ArrayList<Hotsong> hotsong;
		private int mvNum;
		private int singerID;
		private String singerMID;
		private String singerName;
		private String singerPic;
		private String singername_hilight;
		private int songNum;
	}

	@lombok.Data
	public static class Hotalbum {
		private int albumID;
		private String albumMID;
		private String albumName;
		private String albumname_hilight;
	}

	@lombok.Data
	public static class Hotsong {
		private String f;
		private int songID;
		private String songMID;
		private String songName;
		private String songname_hilight;
	}
}
