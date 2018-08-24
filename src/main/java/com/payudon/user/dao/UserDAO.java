package com.payudon.user.dao;
import com.payudon.common.base.dao.MyBatisBaseDao;
import com.payudon.user.entity.User;

/**
 * UserDAO继承基类
 */
public interface UserDAO extends MyBatisBaseDao<User, Integer> {
}