/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.state.entity.TbState;
import com.jeesite.modules.state.dao.TbStateDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_stateService
 * @author z
 * @version 2018-05-10
 */
@Service
@Transactional(readOnly=true)
public class TbStateService extends CrudService<TbStateDao, TbState> {
	
	/**
	 * 获取单条数据
	 * @param tbState
	 * @return
	 */
	@Override
	public TbState get(TbState tbState) {
		return super.get(tbState);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbState
	 * @return
	 */
	@Override
	public Page<TbState> findPage(Page<TbState> page, TbState tbState) {
		return super.findPage(page, tbState);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbState
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbState tbState) {
		super.save(tbState);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbState.getId(), "tbState_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbState.getId(), "tbState_file");
	}
	
	/**
	 * 更新状态
	 * @param tbState
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbState tbState) {
		super.updateStatus(tbState);
	}
	
	/**
	 * 删除数据
	 * @param tbState
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbState tbState) {
		super.delete(tbState);
	}
	
}