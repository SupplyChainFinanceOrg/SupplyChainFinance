/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.state.entity.TbProcessLog;
import com.jeesite.modules.state.dao.TbProcessLogDao;

/**
 * tb_process_logService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbProcessLogService extends CrudService<TbProcessLogDao, TbProcessLog> {
	
	/**
	 * 获取单条数据
	 * @param tbProcessLog
	 * @return
	 */
	@Override
	public TbProcessLog get(TbProcessLog tbProcessLog) {
		return super.get(tbProcessLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbProcessLog
	 * @return
	 */
	@Override
	public Page<TbProcessLog> findPage(Page<TbProcessLog> page, TbProcessLog tbProcessLog) {
		return super.findPage(page, tbProcessLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbProcessLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProcessLog tbProcessLog) {
		super.save(tbProcessLog);
	}
	
	/**
	 * 更新状态
	 * @param tbProcessLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProcessLog tbProcessLog) {
		super.updateStatus(tbProcessLog);
	}
	
	/**
	 * 删除数据
	 * @param tbProcessLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProcessLog tbProcessLog) {
		super.delete(tbProcessLog);
	}
	
}