/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.dao;

import java.util.List;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.TbUser;

/**
 * tb_compDAO接口
 * @author zheng
 * @version 2018-05-09
 */
@MyBatisDao
public interface TbUserDao extends CrudDao<TbUser> {
	public List<TbUser> finduser(TbUser user);
}