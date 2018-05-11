/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.add.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.add.entity.UserRoleBase;
import com.jeesite.modules.add.dao.UserRoleBaseDao;

/**
 * 用户与角色关联表Service
 * @author z
 * @version 2018-05-11
 */
@Service
@Transactional(readOnly=true)
public class UserRoleBaseService extends CrudService<UserRoleBaseDao, UserRoleBase> {
	
	/**
	 * 获取单条数据
	 * @param userRoleBase
	 * @return
	 */
	@Override
	public UserRoleBase get(UserRoleBase userRoleBase) {
		return super.get(userRoleBase);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param userRoleBase
	 * @return
	 */
	@Override
	public Page<UserRoleBase> findPage(Page<UserRoleBase> page, UserRoleBase userRoleBase) {
		return super.findPage(page, userRoleBase);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userRoleBase
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserRoleBase userRoleBase) {
		super.save(userRoleBase);
	}
	
	/**
	 * 更新状态
	 * @param userRoleBase
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserRoleBase userRoleBase) {
		super.updateStatus(userRoleBase);
	}
	
	/**
	 * 删除数据
	 * @param userRoleBase
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserRoleBase userRoleBase) {
		super.delete(userRoleBase);
	}
	
}