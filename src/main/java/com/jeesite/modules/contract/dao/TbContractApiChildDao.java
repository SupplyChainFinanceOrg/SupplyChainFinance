/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.contract.entity.TbContractApiChild;

/**
 * tb_contract_api_childDAO接口
 * @author hanzl
 * @version 2018-05-15
 */
@MyBatisDao
public interface TbContractApiChildDao extends CrudDao<TbContractApiChild> {
	
}