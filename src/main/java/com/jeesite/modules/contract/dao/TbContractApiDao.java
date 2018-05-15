/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.contract.entity.TbContractApi;

/**
 * tb_contract_apiDAO接口
 * @author hanzl
 * @version 2018-05-14
 */
@MyBatisDao
public interface TbContractApiDao extends CrudDao<TbContractApi> {
	
}