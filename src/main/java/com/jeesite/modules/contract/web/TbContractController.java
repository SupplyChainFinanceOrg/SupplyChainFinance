/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.web;

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
import com.jeesite.modules.contract.entity.TbContract;
import com.jeesite.modules.contract.service.TbContractService;

/**
 * tb_contractController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/contract/tbContract")
public class TbContractController extends BaseController {

	@Autowired
	private TbContractService tbContractService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbContract get(String id, boolean isNewRecord) {
		return tbContractService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbContract tbContract, Model model) {
		model.addAttribute("tbContract", tbContract);
		return "modules/contract/tbContractList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbContract> listData(TbContract tbContract, HttpServletRequest request, HttpServletResponse response) {
		Page<TbContract> page = tbContractService.findPage(new Page<TbContract>(request, response), tbContract); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = "form")
	public String form(TbContract tbContract, Model model) {
		model.addAttribute("tbContract", tbContract);
		return "modules/contract/tbContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbContract tbContract) {
		tbContractService.save(tbContract);
		return renderResult(Global.TRUE, "保存合同成功！");
	}
	
	/**
	 * 停用合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbContract tbContract) {
		tbContract.setStatus(TbContract.STATUS_DISABLE);
		tbContractService.updateStatus(tbContract);
		return renderResult(Global.TRUE, "停用合同成功");
	}
	
	/**
	 * 启用合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbContract tbContract) {
		tbContract.setStatus(TbContract.STATUS_NORMAL);
		tbContractService.updateStatus(tbContract);
		return renderResult(Global.TRUE, "启用合同成功");
	}
	
	/**
	 * 删除合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbContract tbContract) {
		tbContractService.delete(tbContract);
		return renderResult(Global.TRUE, "删除合同成功！");
	}
	
}