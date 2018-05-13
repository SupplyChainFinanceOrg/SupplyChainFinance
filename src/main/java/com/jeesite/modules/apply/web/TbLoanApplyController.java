/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.apply.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.apply.entity.TbLoanApply;
import com.jeesite.modules.apply.service.TbLoanApplyService;
import com.jeesite.modules.attachment.entity.TbLoanAttachment;
import com.jeesite.modules.attachment.service.TbLoanAttachmentService;
import com.jeesite.modules.product.entity.TbProduct;
import com.jeesite.modules.product.entity.TbProductBorrowType;
import com.jeesite.modules.product.service.TbProductBorrowTypeService;
import com.jeesite.modules.product.service.TbProductService;
import com.jeesite.modules.state.entity.TbProcess;
import com.jeesite.modules.state.entity.TbState;
import com.jeesite.modules.state.service.TbProcessService;
import com.jeesite.modules.state.service.TbStateService;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.TbUserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;

/**
 * tb_loan_applyController
 * @author z
 * @version 2018-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/tbLoanApply")
public class TbLoanApplyController extends BaseController {

	@Autowired
	private TbLoanApplyService tbLoanApplyService;
	@Autowired
	private TbCompService tbCompService;
	@Autowired
	private TbProductService   tbProductService;
	@Autowired
	private TbProductBorrowTypeService tbProductBorrowTypeService;
	@Autowired
	private TbLoanAttachmentService tbLoanAttachmentService;
	@Autowired
	private TbStateService tbStateService;
	@Autowired
	private TbUserService tbUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private TbProcessService tbProcessService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbLoanApply get(String id, boolean isNewRecord) {
		return tbLoanApplyService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbLoanApply tbLoanApply, Model model) {
		User user =UserUtils.getUser();
		Role r=new Role();
		r.setUserCode(user.getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		List<TbState> bottonlist=new ArrayList<>();
		model.addAttribute("userrolecode", null);
		if(rolelist!=null&&rolelist.size()>0){
			bottonlist=tbStateService.findList(new TbState(TbState.APPLYTYPE, "1", rolelist.get(0).getRoleCode()));
			model.addAttribute("userrolecode", rolelist.get(0).getRoleCode());
		}
		model.addAttribute("bottonlist", bottonlist);
		model.addAttribute("tbLoanApply", tbLoanApply);
		 List<TbProduct>	tbProductlist =new ArrayList<TbProduct>();
		 tbProductlist.add(new TbProduct());
		 tbProductlist.addAll(tbProductService.findList(new TbProduct()));
		model.addAttribute("tbProductlist", tbProductlist);
		//企业
		TbComp tbComp= new TbComp();
		tbComp.setCompType((int)TbComp.HXQYTYPE);
		tbComp.setStatus("1");
		List<TbComp > comlist=new ArrayList<TbComp>();
		comlist.add(new TbComp());
		comlist.addAll(tbCompService.findList(tbComp));
		model.addAttribute("comlist", comlist);
		model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));

		//model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		return "modules/apply/tbLoanApplyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbLoanApply> listData(TbLoanApply tbLoanApply, HttpServletRequest request, HttpServletResponse response) {
		Page<TbLoanApply> page = tbLoanApplyService.findPage(new Page<TbLoanApply>(request, response), tbLoanApply); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "form")
	public String form(TbLoanApply tbLoanApply, Model model,HttpServletRequest request) {
		//查询产品表
		model.addAttribute("tbLoanApply", tbLoanApply);
		model.addAttribute("tbProductlist", tbProductService.findList(new TbProduct()));
		model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		//附件单
		model.addAttribute("tbLoanAttachmentlist", tbLoanAttachmentService.findList(new TbLoanAttachment()));
	//核心企业
		TbComp querytbComp= new TbComp();
		querytbComp.setCompType((int)TbComp.HXQYTYPE);
		querytbComp.setStatus("1");
		List<TbComp > comlist=new ArrayList<TbComp>();
		comlist.add(new TbComp());
		comlist.addAll(tbCompService.findList(querytbComp));
		model.addAttribute("comlist", comlist);
		TbComp tbComp=new TbComp();
		if(tbLoanApply==null||StringUtils.isEmpty(tbLoanApply.getId())){
			//查询关联企业
			tbComp.setUserId(UserUtils.getUser().getUserCode());
			List<TbComp> tbComplist=tbCompService.findList(tbComp);
			if(tbComplist!=null&&tbComplist.size()>0){
				//新加申请
				tbComp=tbComplist.get(0);
				tbLoanApply.setCompName(tbComp.getCompName());
				tbLoanApply.setCompCode(tbComp.getCompCode());
				tbLoanApply.setRegisterDate(tbComp.getRegisterDate());
				tbLoanApply.setRegisterAddress(tbComp.getRegisterAddress());
				tbLoanApply.setCompLegalPerson(tbComp.getCompLegalPerson());
				tbLoanApply.setLegalPersonPhone(tbComp.getLegalPersonPhone());
				tbLoanApply.setCompContact(tbComp.getCompContact());
				tbLoanApply.setContactPhone(tbComp.getContactPhone());
				tbLoanApply.setNatureId(tbComp.getNatureId());				
				tbLoanApply.setIndustry(tbComp.getIndustry());
				tbLoanApply.setMainBusiness(tbComp.getMainBusiness());
				tbLoanApply.setCompProfile(tbComp.getCompProfile());
				tbLoanApply.setSpeciaIndustry(tbComp.getSpeciaIndustry());
				tbLoanApply.setSpecialTradeLicense(tbComp.getSpecialTradeLicense());
				tbLoanApply.setBusinessLicense(tbComp.getBusinessLicense());
				tbLoanApply.setCompPhone(tbComp.getCompPhone());
				tbLoanApply.setCompEmail(tbComp.getCompEmail());
				tbLoanApply.setEmployeesCount(tbComp.getEmployeesCount());
				tbLoanApply.setCompId(tbComp.getId());
		}
		}
		model.addAttribute("tbComp", tbComp);
		//流程
		//获取角色，状态
		List<TbProcess> prolist=tbProcessService.buttunList(tbLoanApply.getApplyState()+"",TbProcess.APPALYTYPE);							
		model.addAttribute("prolist", prolist);		
		if("1".equals(request.getParameter("looktype"))){
	
			return "modules/apply/tbLoanApplyLiu";
		}
		if(StringUtils.isEmpty(tbLoanApply.getId())){
			return "modules/apply/tbLoanApplyForm";
		}
		return "modules/apply/tbLoanApplyDetail";
	}

	/**
	 * 保存借款申请
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(TbLoanApply tbLoanApply,HttpServletRequest request) {
		if(StringUtils.isEmpty(tbLoanApply.getId())){
			tbLoanApply.setApplyTime(new Date());
			tbLoanApply.setUserId(UserUtils.getUser().getUserCode());
			if(tbLoanApply.getApplyState()==null){
				tbLoanApply.setApplyState(0l);
			}
		}
		if(StringUtils.isEmpty(request.getParameter("nextstatus"))||StringUtils.isEmpty(tbLoanApply.getId())){
			tbLoanApplyService.save(tbLoanApply);
		}else{
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			oldtbLoanApply.setApplyState(Long.parseLong(request.getParameter("nextstatus")));
			tbLoanApplyService.save(oldtbLoanApply);
		}
		return renderResult(Global.TRUE, "操作成功！");
	}
	
	/**
	 * 删除借款申请
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbLoanApply tbLoanApply) {
		tbLoanApplyService.delete(tbLoanApply);
		return renderResult(Global.TRUE, "删除借款申请成功！");
	}
	
}