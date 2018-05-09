/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.risk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.risk.entity.TbLoanRisk;
import com.jeesite.modules.risk.dao.TbLoanRiskDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_loan_riskService
 * @author zhengkj
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbLoanRiskService extends CrudService<TbLoanRiskDao, TbLoanRisk> {
	
	/**
	 * 获取单条数据
	 * @param tbLoanRisk
	 * @return
	 */
	@Override
	public TbLoanRisk get(TbLoanRisk tbLoanRisk) {
		return super.get(tbLoanRisk);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbLoanRisk
	 * @return
	 */
	@Override
	public Page<TbLoanRisk> findPage(Page<TbLoanRisk> page, TbLoanRisk tbLoanRisk) {
		return super.findPage(page, tbLoanRisk);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbLoanRisk
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbLoanRisk tbLoanRisk) {
		super.save(tbLoanRisk);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbLoanRisk.getId(), "tbLoanRisk_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbLoanRisk.getId(), "tbLoanRisk_file");
	}
	
	/**
	 * 更新状态
	 * @param tbLoanRisk
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbLoanRisk tbLoanRisk) {
		super.updateStatus(tbLoanRisk);
	}
	
	/**
	 * 删除数据
	 * @param tbLoanRisk
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbLoanRisk tbLoanRisk) {
		super.delete(tbLoanRisk);
	}
	
}