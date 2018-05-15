/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractApiLog;
import com.jeesite.modules.contract.dao.TbContractApiLogDao;

/**
 * tb_contract_api_logService
 * @author hanzl
 * @version 2018-05-14
 */
@Service
@Transactional(readOnly=true)
public class TbContractApiLogService extends CrudService<TbContractApiLogDao, TbContractApiLog> {
	
	/**
	 * 获取单条数据
	 * @param tbContractApiLog
	 * @return
	 */
	@Override
	public TbContractApiLog get(TbContractApiLog tbContractApiLog) {
		return super.get(tbContractApiLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContractApiLog
	 * @return
	 */
	@Override
	public Page<TbContractApiLog> findPage(Page<TbContractApiLog> page, TbContractApiLog tbContractApiLog) {
		return super.findPage(page, tbContractApiLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbContractApiLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContractApiLog tbContractApiLog) {
		super.save(tbContractApiLog);
	}
	
	/**
	 * 更新状态
	 * @param tbContractApiLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbContractApiLog tbContractApiLog) {
		super.updateStatus(tbContractApiLog);
	}
	
	/**
	 * 删除数据
	 * @param tbContractApiLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContractApiLog tbContractApiLog) {
		super.delete(tbContractApiLog);
	}
	
}