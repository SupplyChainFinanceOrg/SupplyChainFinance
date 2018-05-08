/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.distribution.entity.TbMoneyDistribution;

/**
 * tb_money_distributionDAO接口
 * @author z
 * @version 2018-05-08
 */
@MyBatisDao
public interface TbMoneyDistributionDao extends CrudDao<TbMoneyDistribution> {
	
}