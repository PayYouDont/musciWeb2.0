
package com.payudon.common.entity;

import lombok.Data;

/** 
* @ClassName: Page 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年8月27日 上午10:04:25 
*  
*/
@Data
public class Page {
	private int pageIndex = 1; // 当前页
	private int pageSize=10; // 每页最大行数
	private int total;
	public int getBegin() {
		return (pageIndex - 1) * pageSize;
	}
	public int getEnd() {
		return pageIndex * pageSize;
	}
}
