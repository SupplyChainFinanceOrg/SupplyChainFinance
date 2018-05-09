/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbSginContract;
import com.jeesite.modules.contract.dao.TbSginContractDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_sgin_contractService
 * @author zhengkj
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbSginContractService extends CrudService<TbSginContractDao, TbSginContract> {
	
	/**
	 * 获取单条数据
	 * @param tbSginContract
	 * @return
	 */
	@Override
	public TbSginContract get(TbSginContract tbSginContract) {
		return super.get(tbSginContract);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbSginContract
	 * @return
	 */
	@Override
	public Page<TbSginContract> findPage(Page<TbSginContract> page, TbSginContract tbSginContract) {
		return super.findPage(page, tbSginContract);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbSginContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbSginContract tbSginContract) {
		super.save(tbSginContract);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbSginContract.getId(), "tbSginContract_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbSginContract.getId(), "tbSginContract_file");
	}
	
	/**
	 * 更新状态
	 * @param tbSginContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbSginContract tbSginContract) {
		super.updateStatus(tbSginContract);
	}
	
	/**
	 * 删除数据
	 * @param tbSginContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbSginContract tbSginContract) {
		super.delete(tbSginContract);
	}
	
}