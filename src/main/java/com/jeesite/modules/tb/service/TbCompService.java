/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.tb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.dao.TbCompDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_compService
 * @author zheng
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbCompService extends CrudService<TbCompDao, TbComp> {
	
	/**
	 * 获取单条数据
	 * @param tbComp
	 * @return
	 */
	@Override
	public TbComp get(TbComp tbComp) {
		return super.get(tbComp);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbComp
	 * @return
	 */
	@Override
	public Page<TbComp> findPage(Page<TbComp> page, TbComp tbComp) {
		return super.findPage(page, tbComp);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbComp
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbComp tbComp) {
		super.save(tbComp);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbComp.getId(), "tbComp_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbComp.getId(), "tbComp_file");
	}
	
	/**
	 * 更新状态
	 * @param tbComp
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbComp tbComp) {
		super.updateStatus(tbComp);
	}
	
	/**
	 * 删除数据
	 * @param tbComp
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbComp tbComp) {
		super.delete(tbComp);
	}
	
}