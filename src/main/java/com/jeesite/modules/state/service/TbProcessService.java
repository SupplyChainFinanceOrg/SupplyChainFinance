/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.state.entity.TbProcess;
import com.jeesite.modules.state.dao.TbProcessDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_processService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbProcessService extends CrudService<TbProcessDao, TbProcess> {
	
	/**
	 * 获取单条数据
	 * @param tbProcess
	 * @return
	 */
	@Override
	public TbProcess get(TbProcess tbProcess) {
		return super.get(tbProcess);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbProcess
	 * @return
	 */
	@Override
	public Page<TbProcess> findPage(Page<TbProcess> page, TbProcess tbProcess) {
		return super.findPage(page, tbProcess);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbProcess
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProcess tbProcess) {
		super.save(tbProcess);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbProcess.getId(), "tbProcess_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbProcess.getId(), "tbProcess_file");
	}
	
	/**
	 * 更新状态
	 * @param tbProcess
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProcess tbProcess) {
		super.updateStatus(tbProcess);
	}
	
	/**
	 * 删除数据
	 * @param tbProcess
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProcess tbProcess) {
		super.delete(tbProcess);
	}
	
}