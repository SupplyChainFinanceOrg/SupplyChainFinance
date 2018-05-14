/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.counter.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.counter.entity.TbCounter;

/**
 * tb_counterDAO接口
 * @author hanzl
 * @version 2018-05-14
 */
@MyBatisDao
public interface TbCounterDao extends CrudDao<TbCounter> {
	
}