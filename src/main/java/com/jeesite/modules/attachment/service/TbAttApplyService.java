/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.attachment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.attachment.entity.TbAttApply;
import com.jeesite.modules.attachment.dao.TbAttApplyDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_att_applyService
 * @author zhengkj
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbAttApplyService extends CrudService<TbAttApplyDao, TbAttApply> {
	
	/**
	 * 获取单条数据
	 * @param tbAttApply
	 * @return
	 */
	@Override
	public TbAttApply get(TbAttApply tbAttApply) {
		return super.get(tbAttApply);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbAttApply
	 * @return
	 */
	@Override
	public Page<TbAttApply> findPage(Page<TbAttApply> page, TbAttApply tbAttApply) {
		return super.findPage(page, tbAttApply);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbAttApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbAttApply tbAttApply) {
		super.save(tbAttApply);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbAttApply.getId(), "tbAttApply_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbAttApply.getId(), "tbAttApply_file");
	}
	
	/**
	 * 更新状态
	 * @param tbAttApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbAttApply tbAttApply) {
		super.updateStatus(tbAttApply);
	}
	
	/**
	 * 删除数据
	 * @param tbAttApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbAttApply tbAttApply) {
		super.delete(tbAttApply);
	}
	
}