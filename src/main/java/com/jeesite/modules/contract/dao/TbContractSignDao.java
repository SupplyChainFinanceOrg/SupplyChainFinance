/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.contract.entity.TbContractSign;

/**
 * tb_contract_signDAO接口
 * @author hanzl
 * @version 2018-05-10
 */
@MyBatisDao
public interface TbContractSignDao extends CrudDao<TbContractSign> {
	public void saveSign(TbContractSign tbContractSign);
}