/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.lend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lend.entity.TbLend;
import com.jeesite.modules.lend.dao.TbLendDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_lendService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbLendService extends CrudService<TbLendDao, TbLend> {
	
	/**
	 * 获取单条数据
	 * @param tbLend
	 * @return
	 */
	@Override
	public TbLend get(TbLend tbLend) {
		return super.get(tbLend);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbLend
	 * @return
	 */
	@Override
	public Page<TbLend> findPage(Page<TbLend> page, TbLend tbLend) {
		return super.findPage(page, tbLend);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbLend
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbLend tbLend) {
		super.save(tbLend);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbLend.getId(), "tbLend_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbLend.getId(), "tbLend_file");
	}
	
	/**
	 * 更新状态
	 * @param tbLend
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbLend tbLend) {
		super.updateStatus(tbLend);
	}
	
	/**
	 * 删除数据
	 * @param tbLend
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbLend tbLend) {
		super.delete(tbLend);
	}
	
}