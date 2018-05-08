/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.cooperation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.cooperation.entity.TbTimeCooperation;
import com.jeesite.modules.cooperation.dao.TbTimeCooperationDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_time_cooperationService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbTimeCooperationService extends CrudService<TbTimeCooperationDao, TbTimeCooperation> {
	
	/**
	 * 获取单条数据
	 * @param tbTimeCooperation
	 * @return
	 */
	@Override
	public TbTimeCooperation get(TbTimeCooperation tbTimeCooperation) {
		return super.get(tbTimeCooperation);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbTimeCooperation
	 * @return
	 */
	@Override
	public Page<TbTimeCooperation> findPage(Page<TbTimeCooperation> page, TbTimeCooperation tbTimeCooperation) {
		return super.findPage(page, tbTimeCooperation);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbTimeCooperation
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbTimeCooperation tbTimeCooperation) {
		super.save(tbTimeCooperation);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbTimeCooperation.getId(), "tbTimeCooperation_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbTimeCooperation.getId(), "tbTimeCooperation_file");
	}
	
	/**
	 * 更新状态
	 * @param tbTimeCooperation
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbTimeCooperation tbTimeCooperation) {
		super.updateStatus(tbTimeCooperation);
	}
	
	/**
	 * 删除数据
	 * @param tbTimeCooperation
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbTimeCooperation tbTimeCooperation) {
		super.delete(tbTimeCooperation);
	}
	
}