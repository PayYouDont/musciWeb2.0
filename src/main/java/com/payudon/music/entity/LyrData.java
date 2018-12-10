
package com.payudon.music.entity;

import java.util.List;

import com.payudon.music.vo.LyrVO;

import lombok.Data;

/** 
* @ClassName: lyrData 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年12月10日 上午10:48:34 
*  
*/
@Data
public class LyrData {

	private int retcode;
	private int code;
	private int subcode;
	private int type;
	private int songt;
	private String lyric;
	private List<LyrVO> lyrs;
}
