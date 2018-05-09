/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.risk.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.risk.entity.TbLoanRisk;

/**
 * tb_loan_riskDAO接口
 * @author zhengkj
 * @version 2018-05-08
 */
@MyBatisDao
public interface TbLoanRiskDao extends CrudDao<TbLoanRisk> {
	
}