/**   
* @Title: UserServiceImpl.java 
* @Package com.payudon.user.service.impl 
* @Description: TODO(     ) 
* @author peiyongdong  
* @date 2018年8月23日 下午3:54:49 
*/
package com.payudon.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.payudon.user.dao.UserDAO;
import com.payudon.user.entity.User;
import com.payudon.user.service.UserService;

/** 
* @ClassName: UserServiceImpl 
* @Description: TODO(     ) 
* @author peiyongdong
* @date 2018年8月23日 下午3:54:49 
*  
*/
@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDAO dao;

	/** 
	 * <p>Title: save</p> 
	 * <p>Description: </p> 
	 * @param entity
	 * @return
	 * @throws Exception 
	 * @see com.payudon.common.base.service.BaseService#save(java.lang.Object) 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年8月23日 下午3:56:29
	 */
	@Override
	public int save(User entity) throws Exception {
		if(entity.getId()!=null) {
			return dao.updateByPrimaryKeySelective(entity);
		}else {
			return dao.insertSelective(entity);
		}
	}

	/** 
	 * <p>Title: delete</p> 
	 * <p>Description: </p> 
	 * @param id
	 * @return
	 * @throws Exception 
	 * @see com.payudon.common.base.service.BaseService#delete(java.lang.Object) 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年8月23日 下午3:56:29
	 */
	@Override
	public int delete(Integer id) throws Exception {
		return dao.deleteByPrimaryKey(id);
	}

	/** 
	 * <p>Title: findById</p> 
	 * <p>Description: </p> 
	 * @param id
	 * @return 
	 * @see com.payudon.common.base.service.BaseService#findById(java.lang.Integer) 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年8月23日 下午3:56:29
	 */
	@Override
	public User findById(Integer id) {
		return dao.selectByPrimaryKey(id);
	}

	/** 
	 * <p>Title: list</p> 
	 * <p>Description: </p> 
	 * @param map
	 * @return 
	 * @see com.payudon.common.base.service.BaseService#list(java.util.Map) 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年8月23日 下午3:56:29
	 */
	@Override
	public List<User> list(Map<String, Object> map) {
		return dao.list(map);
	}

	/** 
	 * <p>Title: count</p> 
	 * <p>Description: </p> 
	 * @param map
	 * @return 
	 * @see com.payudon.common.base.service.BaseService#count(java.util.Map) 
	 * @throws 
	 * @author peiyongdong
	 * @date 2018年8月23日 下午3:56:29
	 */
	@Override
	public int count(Map<String, Object> map) {
		return dao.count(map);
	}

}
