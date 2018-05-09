/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContract;
import com.jeesite.modules.contract.dao.TbContractDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_contractService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbContractService extends CrudService<TbContractDao, TbContract> {
	
	/**
	 * 获取单条数据
	 * @param tbContract
	 * @return
	 */
	@Override
	public TbContract get(TbContract tbContract) {
		return super.get(tbContract);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContract
	 * @return
	 */
	@Override
	public Page<TbContract> findPage(Page<TbContract> page, TbContract tbContract) {
		return super.findPage(page, tbContract);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContract tbContract) {
		super.save(tbContract);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbContract.getId(), "tbContract_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbContract.getId(), "tbContract_file");
	}
	
	/**
	 * 更新状态
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbContract tbContract) {
		super.updateStatus(tbContract);
	}
	
	/**
	 * 删除数据
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContract tbContract) {
		super.delete(tbContract);
	}
	
}