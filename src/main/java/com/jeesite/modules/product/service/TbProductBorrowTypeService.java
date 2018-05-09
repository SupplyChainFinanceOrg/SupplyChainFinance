/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.product.entity.TbProductBorrowType;
import com.jeesite.modules.product.dao.TbProductBorrowTypeDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_product_borrow_typeService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbProductBorrowTypeService extends CrudService<TbProductBorrowTypeDao, TbProductBorrowType> {
	
	/**
	 * 获取单条数据
	 * @param tbProductBorrowType
	 * @return
	 */
	@Override
	public TbProductBorrowType get(TbProductBorrowType tbProductBorrowType) {
		return super.get(tbProductBorrowType);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbProductBorrowType
	 * @return
	 */
	@Override
	public Page<TbProductBorrowType> findPage(Page<TbProductBorrowType> page, TbProductBorrowType tbProductBorrowType) {
		return super.findPage(page, tbProductBorrowType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbProductBorrowType
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProductBorrowType tbProductBorrowType) {
		super.save(tbProductBorrowType);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbProductBorrowType.getId(), "tbProductBorrowType_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbProductBorrowType.getId(), "tbProductBorrowType_file");
	}
	
	/**
	 * 更新状态
	 * @param tbProductBorrowType
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProductBorrowType tbProductBorrowType) {
		super.updateStatus(tbProductBorrowType);
	}
	
	/**
	 * 删除数据
	 * @param tbProductBorrowType
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProductBorrowType tbProductBorrowType) {
		super.delete(tbProductBorrowType);
	}
	
}