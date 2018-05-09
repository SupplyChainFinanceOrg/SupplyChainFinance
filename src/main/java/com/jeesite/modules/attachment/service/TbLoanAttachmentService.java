/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.attachment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.attachment.entity.TbLoanAttachment;
import com.jeesite.modules.attachment.dao.TbLoanAttachmentDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_loan_attachmentService
 * @author zhengkj
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbLoanAttachmentService extends CrudService<TbLoanAttachmentDao, TbLoanAttachment> {
	
	/**
	 * 获取单条数据
	 * @param tbLoanAttachment
	 * @return
	 */
	@Override
	public TbLoanAttachment get(TbLoanAttachment tbLoanAttachment) {
		return super.get(tbLoanAttachment);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbLoanAttachment
	 * @return
	 */
	@Override
	public Page<TbLoanAttachment> findPage(Page<TbLoanAttachment> page, TbLoanAttachment tbLoanAttachment) {
		return super.findPage(page, tbLoanAttachment);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbLoanAttachment
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbLoanAttachment tbLoanAttachment) {
		super.save(tbLoanAttachment);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbLoanAttachment.getId(), "tbLoanAttachment_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbLoanAttachment.getId(), "tbLoanAttachment_file");
	}
	
	/**
	 * 更新状态
	 * @param tbLoanAttachment
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbLoanAttachment tbLoanAttachment) {
		super.updateStatus(tbLoanAttachment);
	}
	
	/**
	 * 删除数据
	 * @param tbLoanAttachment
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbLoanAttachment tbLoanAttachment) {
		super.delete(tbLoanAttachment);
	}
	
}