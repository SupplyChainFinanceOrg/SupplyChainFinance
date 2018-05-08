/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractField;
import com.jeesite.modules.contract.dao.TbContractFieldDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_contract_fieldService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbContractFieldService extends CrudService<TbContractFieldDao, TbContractField> {
	
	/**
	 * 获取单条数据
	 * @param tbContractField
	 * @return
	 */
	@Override
	public TbContractField get(TbContractField tbContractField) {
		return super.get(tbContractField);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContractField
	 * @return
	 */
	@Override
	public Page<TbContractField> findPage(Page<TbContractField> page, TbContractField tbContractField) {
		return super.findPage(page, tbContractField);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbContractField
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContractField tbContractField) {
		super.save(tbContractField);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbContractField.getId(), "tbContractField_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbContractField.getId(), "tbContractField_file");
	}
	
	/**
	 * 更新状态
	 * @param tbContractField
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbContractField tbContractField) {
		super.updateStatus(tbContractField);
	}
	
	/**
	 * 删除数据
	 * @param tbContractField
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContractField tbContractField) {
		super.delete(tbContractField);
	}
	
}