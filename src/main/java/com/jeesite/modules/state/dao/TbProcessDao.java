/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.state.entity.TbProcess;

/**
 * tb_processDAO接口
 * @author z
 * @version 2018-05-11
 */
@MyBatisDao
public interface TbProcessDao extends CrudDao<TbProcess> {
	
}