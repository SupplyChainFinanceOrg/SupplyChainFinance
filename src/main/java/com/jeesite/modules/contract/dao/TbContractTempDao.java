/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.contract.entity.TbContractTemp;

/**
 * tb_contractDAO接口
 * @author z
 * @version 2018-05-08
 */
@MyBatisDao
public interface TbContractTempDao extends CrudDao<TbContractTemp> {
	
}