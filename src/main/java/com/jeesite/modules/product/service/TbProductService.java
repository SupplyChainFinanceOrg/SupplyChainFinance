/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.product.entity.TbProduct;
import com.jeesite.modules.product.dao.TbProductDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_productService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbProductService extends CrudService<TbProductDao, TbProduct> {
	
	/**
	 * 获取单条数据
	 * @param tbProduct
	 * @return
	 */
	@Override
	public TbProduct get(TbProduct tbProduct) {
		return super.get(tbProduct);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbProduct
	 * @return
	 */
	@Override
	public Page<TbProduct> findPage(Page<TbProduct> page, TbProduct tbProduct) {
		return super.findPage(page, tbProduct);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProduct tbProduct) {
		super.save(tbProduct);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbProduct.getId(), "tbProduct_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbProduct.getId(), "tbProduct_file");
	}
	
	/**
	 * 更新状态
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProduct tbProduct) {
		super.updateStatus(tbProduct);
	}
	
	/**
	 * 删除数据
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProduct tbProduct) {
		super.delete(tbProduct);
	}
	
}