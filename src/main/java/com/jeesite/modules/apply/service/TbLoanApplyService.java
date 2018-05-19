/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.apply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.apply.entity.TbLoanApply;
import com.jeesite.modules.apply.dao.TbLoanApplyDao;
import com.jeesite.modules.attachment.entity.TbLoanAttachment;
import com.jeesite.modules.attachment.service.TbLoanAttachmentService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tb.entity.TbComp;

/**
 * tb_loan_applyService
 * @author z
 * @version 2018-05-12
 */
@Service
@Transactional(readOnly=true)
public class TbLoanApplyService extends CrudService<TbLoanApplyDao, TbLoanApply> {
	@Autowired
	private TbLoanAttachmentService tbLoanAttachmentService;
	@Autowired
	private RoleService roleService;
	/**
	 * 获取单条数据
	 * @param tbLoanApply
	 * @return
	 */
	@Override
	public TbLoanApply get(TbLoanApply tbLoanApply) {
		return super.get(tbLoanApply);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbLoanApply
	 * @return
	 */
	@Override
	public Page<TbLoanApply> findPage(Page<TbLoanApply> page, TbLoanApply tbLoanApply) {
		return super.findPage(page, tbLoanApply);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbLoanApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbLoanApply tbLoanApply) {
		super.save(tbLoanApply);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbLoanApply.getId(), "tbLoanApply_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbLoanApply.getId(), "tbLoanApply_file");
		
		Role r=new Role();
		r.setUserCode(UserUtils.getUser().getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		String userrolecode="";
		if(rolelist!=null&&rolelist.size()>0){
			userrolecode=rolelist.get(0).getRoleCode();
		}
		if(userrolecode.equals(TbComp.JKQYROLECODE)){
		
		//企业附件
				TbLoanAttachment tbatt=new TbLoanAttachment();
				tbatt.setAttachmentType(0);
				if(TbComp.HXQYROLECODE.equals(userrolecode)){
					tbatt.setIsCoreVisible(0);
				}
				if(TbComp.JRQYROLECODE.equals(userrolecode)){
					tbatt.setIsBankVisible(0);	
				}
				tbatt.setIsdel(0);
				List<TbLoanAttachment> list= tbLoanAttachmentService.findList(tbatt);
				if(list!=null&&list.size()>0){
					for(TbLoanAttachment att:list){
						FileUploadUtils.saveFileUpload(tbLoanApply.getId(), "tbLoanApply_corp_"+att.getId());
					}
				}
				//个人附件
				tbatt=new TbLoanAttachment();
				tbatt.setAttachmentType(1);
				if(TbComp.HXQYROLECODE.equals(userrolecode)){
					tbatt.setIsCoreVisible(0);
				}
				if(TbComp.JRQYROLECODE.equals(userrolecode)){
					tbatt.setIsBankVisible(0);	
				}
				tbatt.setIsdel(0);
				list=tbLoanAttachmentService.findList(tbatt);
				if(list!=null&&list.size()>0){
					for(TbLoanAttachment att:list){
						FileUploadUtils.saveFileUpload(tbLoanApply.getId(), "tbLoanApply_people_"+att.getId());
					}
				}	
		}
	}
	
	/**
	 * 更新状态
	 * @param tbLoanApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbLoanApply tbLoanApply) {
		super.updateStatus(tbLoanApply);
	}
	
	/**
	 * 删除数据
	 * @param tbLoanApply
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbLoanApply tbLoanApply) {
		super.delete(tbLoanApply);
	}
	
}