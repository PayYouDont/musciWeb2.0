
package com.payudon.music.entity;

import java.util.ArrayList;

/**
 * @ClassName: FMData
 * @Description: TODO( )
 * @author peiyongdong
 * @date 2018年12月7日 上午10:42:44
 * 
 */
@lombok.Data
public class FMData {

	private int code;
	private int subcode;
	private String message;
	private Data data;

	@lombok.Data
	public static class Data {
		private Data data;
		private Object gmgg;
		private ArrayList<GroupList> groupList;
	}

	@lombok.Data
	public static class RadioList {
		private String Fnew;
		private String Fonly;
		private String listenNum;
		private String radioFphoto1;
		private String radioFphoto2;
		private String radioId;
		private String radioImg;
		private String radioName;
	}

	@lombok.Data
	public static class GroupList {
		private String name;
		private ArrayList<RadioList> radioList;
		private String type;
	}
}
