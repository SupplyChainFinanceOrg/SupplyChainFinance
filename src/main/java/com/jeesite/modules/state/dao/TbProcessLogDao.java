/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.state.entity.TbProcessLog;

/**
 * tb_process_logDAO接口
 * @author z
 * @version 2018-05-08
 */
@MyBatisDao
public interface TbProcessLogDao extends CrudDao<TbProcessLog> {
	
}