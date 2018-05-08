/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.control.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.control.entity.TbRiskControl;
import com.jeesite.modules.control.dao.TbRiskControlDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_risk_controlService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbRiskControlService extends CrudService<TbRiskControlDao, TbRiskControl> {
	
	/**
	 * 获取单条数据
	 * @param tbRiskControl
	 * @return
	 */
	@Override
	public TbRiskControl get(TbRiskControl tbRiskControl) {
		return super.get(tbRiskControl);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbRiskControl
	 * @return
	 */
	@Override
	public Page<TbRiskControl> findPage(Page<TbRiskControl> page, TbRiskControl tbRiskControl) {
		return super.findPage(page, tbRiskControl);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbRiskControl
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbRiskControl tbRiskControl) {
		super.save(tbRiskControl);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbRiskControl.getId(), "tbRiskControl_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbRiskControl.getId(), "tbRiskControl_file");
	}
	
	/**
	 * 更新状态
	 * @param tbRiskControl
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbRiskControl tbRiskControl) {
		super.updateStatus(tbRiskControl);
	}
	
	/**
	 * 删除数据
	 * @param tbRiskControl
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbRiskControl tbRiskControl) {
		super.delete(tbRiskControl);
	}
	
}