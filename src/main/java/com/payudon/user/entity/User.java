package com.payudon.user.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态(0:未激活邮箱、1:已激活邮箱，-1:禁用)
     */
    private Byte status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱激活记号
     */
    private String token;

    /**
     * 激活过期时间
     */
    private Date activateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}