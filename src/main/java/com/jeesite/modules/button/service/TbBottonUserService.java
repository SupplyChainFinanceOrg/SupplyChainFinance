/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.button.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.button.entity.TbBottonUser;
import com.jeesite.modules.button.dao.TbBottonUserDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_botton_userService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbBottonUserService extends CrudService<TbBottonUserDao, TbBottonUser> {
	
	/**
	 * 获取单条数据
	 * @param tbBottonUser
	 * @return
	 */
	@Override
	public TbBottonUser get(TbBottonUser tbBottonUser) {
		return super.get(tbBottonUser);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbBottonUser
	 * @return
	 */
	@Override
	public Page<TbBottonUser> findPage(Page<TbBottonUser> page, TbBottonUser tbBottonUser) {
		return super.findPage(page, tbBottonUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbBottonUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbBottonUser tbBottonUser) {
		super.save(tbBottonUser);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbBottonUser.getId(), "tbBottonUser_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbBottonUser.getId(), "tbBottonUser_file");
	}
	
	/**
	 * 更新状态
	 * @param tbBottonUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbBottonUser tbBottonUser) {
		super.updateStatus(tbBottonUser);
	}
	
	/**
	 * 删除数据
	 * @param tbBottonUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbBottonUser tbBottonUser) {
		super.delete(tbBottonUser);
	}
	
}