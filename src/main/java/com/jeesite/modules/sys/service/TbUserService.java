/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sys.dao.TbUserDao;
import com.jeesite.modules.sys.entity.TbUser;

/**
 * tb_compService
 * @author zheng
 * @version 2018-05-09
 */
@Service
@Transactional(readOnly=true)
public class TbUserService extends CrudService<TbUserDao, TbUser> {
	@Autowired
	private TbUserDao tbUserDao;
	/**
	 * 获取单条数据
	 * @param tbComp
	 * @return
	 */
	@Override
	public TbUser get(TbUser tbUser) {
		return super.get(tbUser);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbComp
	 * @return
	 */
	@Override
	public Page<TbUser> findPage(Page<TbUser> page, TbUser tbUser) {
		return super.findPage(page, tbUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbComp
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbUser tbUser) {
		super.save(tbUser);
		// 保存上传图片
		
	}
	
	
	

	public List<TbUser> finduser(TbUser user) {
		return tbUserDao.finduser(user);
	}
	
}