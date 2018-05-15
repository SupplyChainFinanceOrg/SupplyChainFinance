/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractApiChild;
import com.jeesite.modules.contract.dao.TbContractApiChildDao;

/**
 * tb_contract_api_childService
 * @author hanzl
 * @version 2018-05-15
 */
@Service
@Transactional(readOnly=true)
public class TbContractApiChildService extends CrudService<TbContractApiChildDao, TbContractApiChild> {
	
	/**
	 * 获取单条数据
	 * @param tbContractApiChild
	 * @return
	 */
	@Override
	public TbContractApiChild get(TbContractApiChild tbContractApiChild) {
		return super.get(tbContractApiChild);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContractApiChild
	 * @return
	 */
	@Override
	public Page<TbContractApiChild> findPage(Page<TbContractApiChild> page, TbContractApiChild tbContractApiChild) {
		return super.findPage(page, tbContractApiChild);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbContractApiChild
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContractApiChild tbContractApiChild) {
		super.save(tbContractApiChild);
	}
	
	/**
	 * 更新状态
	 * @param tbContractApiChild
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbContractApiChild tbContractApiChild) {
		super.updateStatus(tbContractApiChild);
	}
	
	/**
	 * 删除数据
	 * @param tbContractApiChild
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContractApiChild tbContractApiChild) {
		super.delete(tbContractApiChild);
	}
	
}