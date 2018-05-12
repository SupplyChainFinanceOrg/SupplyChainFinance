/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.apply.web;

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

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.apply.entity.TbLoanApply;
import com.jeesite.modules.apply.service.TbLoanApplyService;

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
		model.addAttribute("tbLoanApply", tbLoanApply);
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
	public String form(TbLoanApply tbLoanApply, Model model) {
		model.addAttribute("tbLoanApply", tbLoanApply);
		return "modules/apply/tbLoanApplyForm";
	}

	/**
	 * 保存借款申请
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbLoanApply tbLoanApply) {
		tbLoanApplyService.save(tbLoanApply);
		return renderResult(Global.TRUE, "保存借款申请成功！");
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