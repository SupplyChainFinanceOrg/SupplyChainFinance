/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contact.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.contact.entity.TbContract;

/**
 * contractDAO接口
 * @author hanzl
 * @version 2018-05-09
 */
@MyBatisDao
public interface TbContractDao extends CrudDao<TbContract> {
	
}