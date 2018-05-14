/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.apply.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.apply.entity.TbLoanApply;

/**
 * tb_loan_applyDAO接口
 * @author z
 * @version 2018-05-12
 */
@MyBatisDao
public interface TbLoanApplyDao extends CrudDao<TbLoanApply> {
	
}