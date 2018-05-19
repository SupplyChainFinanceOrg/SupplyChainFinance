/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.contract.entity.TbContractSignPoint;

/**
 * tb_contract_sign_pointDAO接口
 * @author hanzl
 * @version 2018-05-19
 */
@MyBatisDao
public interface TbContractSignPointDao extends CrudDao<TbContractSignPoint> {
	
}