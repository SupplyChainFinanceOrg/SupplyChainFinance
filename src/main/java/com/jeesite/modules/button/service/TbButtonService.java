/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.button.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.button.entity.TbButton;
import com.jeesite.modules.button.dao.TbButtonDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_buttonService
 * @author z
 * @version 2018-05-11
 */
@Service
@Transactional(readOnly=true)
public class TbButtonService extends CrudService<TbButtonDao, TbButton> {
	
	/**
	 * 获取单条数据
	 * @param tbButton
	 * @return
	 */
	@Override
	public TbButton get(TbButton tbButton) {
		return super.get(tbButton);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbButton
	 * @return
	 */
	@Override
	public Page<TbButton> findPage(Page<TbButton> page, TbButton tbButton) {
		return super.findPage(page, tbButton);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbButton
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbButton tbButton) {
		super.save(tbButton);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbButton.getId(), "tbButton_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbButton.getId(), "tbButton_file");
	}
	
	/**
	 * 更新状态
	 * @param tbButton
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbButton tbButton) {
		super.updateStatus(tbButton);
	}
	
	/**
	 * 删除数据
	 * @param tbButton
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbButton tbButton) {
		super.delete(tbButton);
	}
	
}