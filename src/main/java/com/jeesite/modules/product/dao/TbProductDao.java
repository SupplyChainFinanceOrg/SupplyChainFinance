/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.product.entity.TbProduct;

/**
 * tb_productDAO接口
 * @author z
 * @version 2018-05-08
 */
@MyBatisDao
public interface TbProductDao extends CrudDao<TbProduct> {
	
}