/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.nature.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.nature.entity.TbCompNature;
import com.jeesite.modules.nature.dao.TbCompNatureDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_comp_natureService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbCompNatureService extends CrudService<TbCompNatureDao, TbCompNature> {
	
	/**
	 * 获取单条数据
	 * @param tbCompNature
	 * @return
	 */
	@Override
	public TbCompNature get(TbCompNature tbCompNature) {
		return super.get(tbCompNature);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbCompNature
	 * @return
	 */
	@Override
	public Page<TbCompNature> findPage(Page<TbCompNature> page, TbCompNature tbCompNature) {
		return super.findPage(page, tbCompNature);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbCompNature
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbCompNature tbCompNature) {
		super.save(tbCompNature);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbCompNature.getId(), "tbCompNature_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbCompNature.getId(), "tbCompNature_file");
	}
	
	/**
	 * 更新状态
	 * @param tbCompNature
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbCompNature tbCompNature) {
		super.updateStatus(tbCompNature);
	}
	
	/**
	 * 删除数据
	 * @param tbCompNature
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbCompNature tbCompNature) {
		super.delete(tbCompNature);
	}
	
}