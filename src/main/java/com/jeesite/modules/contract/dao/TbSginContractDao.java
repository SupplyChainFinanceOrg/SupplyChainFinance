/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.contract.entity.TbSginContract;

/**
 * tb_sgin_contractDAO接口
 * @author zhengkj
 * @version 2018-05-08
 */
@MyBatisDao
public interface TbSginContractDao extends CrudDao<TbSginContract> {
	public void saveByXml(TbSginContract tbSginContract);
}