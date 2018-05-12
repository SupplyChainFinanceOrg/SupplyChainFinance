/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.apply.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.apply.entity.TbLoanApply;
import com.jeesite.modules.apply.dao.TbLoanApplyDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_loan_applyService
 * @author z
 * @version 2018-05-12
 */
@Service
@Transactional(readOnly=true)
public class TbLoanApplyService extends CrudService<TbLoanApplyDao, TbLoanApply> {
	
	/**
	 * 获取单条数据
	 * @param tbLoanApply
	 * @return
	 */
	@Override
	public TbLoanApply get(TbLoanApply tbLoanApply) {
		return super.get(tbLoanApply);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbLoanApply
	 * @return
	 */
	@Override
	public Page<TbLoanApply> findPage(Page<TbLoanApply> page, TbLoanApply tbLoanApply) {
		return super.findPage(page, tbLoanApply);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbLoanApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbLoanApply tbLoanApply) {
		super.save(tbLoanApply);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbLoanApply.getId(), "tbLoanApply_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbLoanApply.getId(), "tbLoanApply_file");
	}
	
	/**
	 * 更新状态
	 * @param tbLoanApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbLoanApply tbLoanApply) {
		super.updateStatus(tbLoanApply);
	}
	
	/**
	 * 删除数据
	 * @param tbLoanApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbLoanApply tbLoanApply) {
		super.delete(tbLoanApply);
	}
	
}