/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.distribution.entity.TbMoneyDistribution;
import com.jeesite.modules.distribution.dao.TbMoneyDistributionDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_money_distributionService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbMoneyDistributionService extends CrudService<TbMoneyDistributionDao, TbMoneyDistribution> {
	
	/**
	 * 获取单条数据
	 * @param tbMoneyDistribution
	 * @return
	 */
	@Override
	public TbMoneyDistribution get(TbMoneyDistribution tbMoneyDistribution) {
		return super.get(tbMoneyDistribution);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbMoneyDistribution
	 * @return
	 */
	@Override
	public Page<TbMoneyDistribution> findPage(Page<TbMoneyDistribution> page, TbMoneyDistribution tbMoneyDistribution) {
		return super.findPage(page, tbMoneyDistribution);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbMoneyDistribution
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbMoneyDistribution tbMoneyDistribution) {
		super.save(tbMoneyDistribution);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbMoneyDistribution.getId(), "tbMoneyDistribution_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbMoneyDistribution.getId(), "tbMoneyDistribution_file");
	}
	
	/**
	 * 更新状态
	 * @param tbMoneyDistribution
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbMoneyDistribution tbMoneyDistribution) {
		super.updateStatus(tbMoneyDistribution);
	}
	
	/**
	 * 删除数据
	 * @param tbMoneyDistribution
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbMoneyDistribution tbMoneyDistribution) {
		super.delete(tbMoneyDistribution);
	}
	
}