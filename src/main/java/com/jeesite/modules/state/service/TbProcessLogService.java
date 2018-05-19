/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.button.service.TbButtonService;
import com.jeesite.modules.state.entity.TbProcessLog;
import com.jeesite.modules.state.dao.TbProcessLogDao;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * tb_process_logService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbProcessLogService extends CrudService<TbProcessLogDao, TbProcessLog> {
	@Autowired
	private TbButtonService tbButtonService;
	@Autowired
	private RoleService roleService;
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
	/**
	 * 状态		
	 * // 类型 0注册 1借款
				// 产品id
			// 借款id
			// 用户id
				// 操作时间
				// 操作内容
			// 操作意见
				// 借款企业可见
			// 核心企业可见
				// 金融机构可见
	 * */
	@Transactional(readOnly=false)
	public void saveLog(Integer  state,Integer type,String compId,String productId,String loanId,String operationRemark,Integer loanCompVisible,Integer coreCompVisible,Integer bankCompVisible,String operationLog) {
		TbProcessLog tbProcessLog=new TbProcessLog ();
		tbProcessLog.setLogState(state);
		tbProcessLog.setType(type);
		tbProcessLog.setProductId(productId);
		tbProcessLog.setLoanId(loanId);
		tbProcessLog.setUserId(UserUtils.getUser().getId());
		tbProcessLog.setOperationTime(new Date());
		tbProcessLog.setCompId(compId);
		tbProcessLog.setOperationRemark(operationRemark);
		tbProcessLog.setOperationLog(operationLog);
		if((operationRemark!=null&&operationRemark.length()==0)||"".equals(operationRemark)){
			tbProcessLog.setOperationRemark(null);
		}
		tbProcessLog.setCoreCompVisible(coreCompVisible);
		tbProcessLog.setLoanCompVisible(loanCompVisible);
		tbProcessLog.setBankCompVisible(bankCompVisible);
		super.save(tbProcessLog);
	}
}