/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.tb.dao;

import java.util.List;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.TbUser;
import com.jeesite.modules.tb.entity.TbComp;

/**
 * tb_compDAO接口
 * @author zheng
 * @version 2018-05-09
 */
@MyBatisDao
public interface TbCompDao extends CrudDao<TbComp> {
	public List<TbUser> finduser(TbUser user);
}