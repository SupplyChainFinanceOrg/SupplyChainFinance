/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.risk.web;

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
import com.jeesite.modules.risk.entity.TbLoanRisk;
import com.jeesite.modules.risk.service.TbLoanRiskService;

/**
 * tb_loan_riskController
 * @author zhengkj
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/risk/tbLoanRisk")
public class TbLoanRiskController extends BaseController {

	@Autowired
	private TbLoanRiskService tbLoanRiskService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbLoanRisk get(String id, boolean isNewRecord) {
		return tbLoanRiskService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("risk:tbLoanRisk:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbLoanRisk tbLoanRisk, Model model) {
		model.addAttribute("tbLoanRisk", tbLoanRisk);
		return "modules/risk/tbLoanRiskList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("risk:tbLoanRisk:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbLoanRisk> listData(TbLoanRisk tbLoanRisk, HttpServletRequest request, HttpServletResponse response) {
		Page<TbLoanRisk> page = tbLoanRiskService.findPage(new Page<TbLoanRisk>(request, response), tbLoanRisk); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("risk:tbLoanRisk:view")
	@RequestMapping(value = "form")
	public String form(TbLoanRisk tbLoanRisk, Model model) {
		model.addAttribute("tbLoanRisk", tbLoanRisk);
		return "modules/risk/tbLoanRiskForm";
	}

	/**
	 * 保存风控
	 */
	@RequiresPermissions("risk:tbLoanRisk:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbLoanRisk tbLoanRisk) {
		tbLoanRiskService.save(tbLoanRisk);
		return renderResult(Global.TRUE, "保存风控成功！");
	}
	
	/**
	 * 停用风控
	 */
	@RequiresPermissions("risk:tbLoanRisk:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbLoanRisk tbLoanRisk) {
		tbLoanRisk.setStatus(TbLoanRisk.STATUS_DISABLE);
		tbLoanRiskService.updateStatus(tbLoanRisk);
		return renderResult(Global.TRUE, "停用风控成功");
	}
	
	/**
	 * 启用风控
	 */
	@RequiresPermissions("risk:tbLoanRisk:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbLoanRisk tbLoanRisk) {
		tbLoanRisk.setStatus(TbLoanRisk.STATUS_NORMAL);
		tbLoanRiskService.updateStatus(tbLoanRisk);
		return renderResult(Global.TRUE, "启用风控成功");
	}
	
	/**
	 * 删除风控
	 */
	@RequiresPermissions("risk:tbLoanRisk:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbLoanRisk tbLoanRisk) {
		tbLoanRiskService.delete(tbLoanRisk);
		return renderResult(Global.TRUE, "删除风控成功！");
	}
	
}