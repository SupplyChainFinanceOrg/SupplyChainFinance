/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.tb.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sys.entity.Company;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.entity.UserRole;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.service.PostService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.dao.TbCompDao;
import com.jeesite.modules.add.entity.UserRoleBase;
import com.jeesite.modules.add.service.UserRoleBaseService;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tb_compService
 * @author zheng
 * @version 2018-05-09
 */
@Service
@Transactional(readOnly=true)
public class TbCompService extends CrudService<TbCompDao, TbComp> {
	@Autowired
	private TbCompDao tbCompDao;
	@Autowired
	private EmpUserService empUserService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private UserRoleBaseService userRoleBaseService;

	/**
	 * 获取单条数据
	 * @param tbComp
	 * @return
	 */
	@Override
	public TbComp get(TbComp tbComp) {
		return super.get(tbComp);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbComp
	 * @return
	 */
	@Override
	public Page<TbComp> findPage(Page<TbComp> page, TbComp tbComp) {
		return super.findPage(page, tbComp);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbComp
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbComp tbComp) {
		tbComp.setOperationData(new Date());
		tbComp.setOperationUserId(UserUtils.getUser().getUserCode());
		super.save(tbComp);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbComp.getId(), "tbComp_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbComp.getId(), "tbComp_file");
		
		String[] files={"tbComp_idCardA","tbComp_idCardB","tbComp_specialTradeLicense","tbComp_businessLicense","tbComp_compProfileAttachment","tbComp_articlesAssociation","tbComp_capitalVerificationReport"};
		for(String file:files){
			FileUploadUtils.saveFileUpload(tbComp.getId(), file);
		}
	}
	/**
	 * 保持以及创建用户
	 * @param tbComp
	 */
	@Transactional(readOnly=false)
	public void saveAndCreate(TbComp tbComp,boolean isadd) {
		super.save(tbComp);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbComp.getId(), "tbComp_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tbComp.getId(), "tbComp_file");
		
		String[] files={"tbComp_idCardA","tbComp_idCardB","tbComp_specialTradeLicense","tbComp_businessLicense","tbComp_compProfileAttachment","tbComp_articlesAssociation","tbComp_capitalVerificationReport"};
		for(String file:files){
			FileUploadUtils.saveFileUpload(tbComp.getId(), file);
		}
		if(isadd){
			
			EmpUser empUser=new EmpUser();
			empUser.setUserType(User.USER_TYPE_EMPLOYEE);
			empUser.setMgrType(User.MGR_TYPE_NOT_ADMIN);
			//机构、公司、账户号、名称、手机、邮箱、角色、SD1001off
			
					
			empUser.setLoginCode(tbComp.getContactPhone());
			empUser.setUserName(tbComp.getCompName());
			empUser.setPhone(tbComp.getContactPhone());
			empUser.setEmail(tbComp.getCompEmail());
			UserRole role=new UserRole();
			Office of=new Office();
			of.setOfficeCode("SD1001off");
			Company com=new Company();
			com.setCompanyCode("SD1");
			if(tbComp.getCompType()==TbComp.JKQYTYPE){
				role.setRoleCode(TbComp.JKQYROLECODE);
				of.setOfficeName("借款企业");
			}else if(tbComp.getCompType()==TbComp.HXQYTYPE){
				role.setRoleCode(TbComp.HXQYROLECODE);
				of.setOfficeName("核心企业");
			}else if(tbComp.getCompType()==TbComp.JRQYTYPE){
				role.setRoleCode(TbComp.JRQYROLECODE);
				of.setOfficeName("金融机构");
			}
			Employee employee=new Employee();
			employee.setOffice(of);
			employee.setCompany(com);;
			empUser.setEmployee(employee);	
			//List<Role> rolelist=roleService.findList(role);	
			empUser.getUserRoleList().add(role);
			empUserService.save(empUser);
			UserRoleBase userRoleBase=new UserRoleBase();
			userRoleBase.setUserCode(empUser.getUserCode());
			userRoleBase.setRoleCode(role.getRoleCode());
			userRoleBaseService.save(userRoleBase);
			tbComp.setUserId(empUser.getUserCode());
			super.save(tbComp);
			//userService.saveAuth(empUser);

		}
	}
	/**
	 * 更新状态
	 * @param tbComp
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbComp tbComp) {
		super.updateStatus(tbComp);
	}
	
	/**
	 * 删除数据
	 * @param tbComp
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbComp tbComp) {
		super.delete(tbComp);
	}

	
}