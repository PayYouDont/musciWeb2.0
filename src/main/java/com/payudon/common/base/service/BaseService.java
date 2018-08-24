
package com.payudon.common.base.service;

import java.util.List;
import java.util.Map;

/** 
* @ClassName: BaseService 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年8月23日 下午3:49:28 
*  
*/
public interface BaseService<T> {
	
	int save(T entity) throws Exception;
	
	int delete(Integer id) throws Exception;
	
	T findById(Integer id);
	
	List<T> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
}
