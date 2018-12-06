
package com.payudon.music.entity;

import java.util.ArrayList;

/**
 * @ClassName: ClassicalData
 * @Description: TODO(古典音乐歌单数据)
 * @author peiyongdong
 * @date 2018年12月6日 上午11:51:03
 * 
 */
@lombok.Data
public class ClassicalData {

	private int code;
	private int subcode;
	private String message;
	private Data data;

	@lombok.Data
	public static class Data {
		private int uin;
		private int categoryId;
		private int sortId;
		private int sum;
		private int sin;
		private int ein;
		private ArrayList<List> list;
	}

	@lombok.Data
	public static class Creator {
		private int type;
		private long qq;
		private String encrypt_uin;
		private String name;
		private int isVip;
		private String avatarUrl;
		private int followflag;
	}

	@lombok.Data
	public static class List {
		private String dissid;
		private String createtime;
		private String commit_time;
		private String dissname;
		private String imgurl;
		private String introduction;
		private int listennum;
		private float score;
		private int version;
		private Creator creator;
	}
}
