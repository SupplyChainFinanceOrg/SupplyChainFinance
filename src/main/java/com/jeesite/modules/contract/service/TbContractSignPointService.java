/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractSignPoint;
import com.jeesite.modules.contract.dao.TbContractSignPointDao;

/**
 * tb_contract_sign_pointService
 * @author hanzl
 * @version 2018-05-19
 */
@Service
@Transactional(readOnly=true)
public class TbContractSignPointService extends CrudService<TbContractSignPointDao, TbContractSignPoint> {
	
	/**
	 * 获取单条数据
	 * @param tbContractSignPoint
	 * @return
	 */
	@Override
	public TbContractSignPoint get(TbContractSignPoint tbContractSignPoint) {
		return super.get(tbContractSignPoint);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContractSignPoint
	 * @return
	 */
	@Override
	public Page<TbContractSignPoint> findPage(Page<TbContractSignPoint> page, TbContractSignPoint tbContractSignPoint) {
		return super.findPage(page, tbContractSignPoint);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbContractSignPoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContractSignPoint tbContractSignPoint) {
		super.save(tbContractSignPoint);
	}
	
	/**
	 * 更新状态
	 * @param tbContractSignPoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbContractSignPoint tbContractSignPoint) {
		super.updateStatus(tbContractSignPoint);
	}
	
	/**
	 * 删除数据
	 * @param tbContractSignPoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContractSignPoint tbContractSignPoint) {
		super.delete(tbContractSignPoint);
	}
	
}