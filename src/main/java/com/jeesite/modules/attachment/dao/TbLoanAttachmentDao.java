/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.attachment.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.attachment.entity.TbLoanAttachment;

/**
 * tb_loan_attachmentDAO接口
 * @author zhengkj
 * @version 2018-05-08
 */
@MyBatisDao
public interface TbLoanAttachmentDao extends CrudDao<TbLoanAttachment> {
	
}