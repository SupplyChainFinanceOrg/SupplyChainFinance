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
import com.jeesite.modules.contract.entity.TbSginContract;
import com.jeesite.modules.contract.service.TbSginContractService;

/**
 * tb_sgin_contractController
 * @author zhengkj
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/contract/tbSginContract")
public class TbSginContractController extends BaseController {

	@Autowired
	private TbSginContractService tbSginContractService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbSginContract get(String id, boolean isNewRecord) {
		return tbSginContractService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("contract:tbSginContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbSginContract tbSginContract, Model model) {
		model.addAttribute("tbSginContract", tbSginContract);
		return "modules/contract/tbSginContractList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("contract:tbSginContract:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbSginContract> listData(TbSginContract tbSginContract, HttpServletRequest request, HttpServletResponse response) {
		Page<TbSginContract> page = tbSginContractService.findPage(new Page<TbSginContract>(request, response), tbSginContract); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("contract:tbSginContract:view")
	@RequestMapping(value = "form")
	public String form(TbSginContract tbSginContract, Model model) {
		model.addAttribute("tbSginContract", tbSginContract);
		return "modules/contract/tbSginContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbSginContract tbSginContract) {
		tbSginContractService.save(tbSginContract);
		return renderResult(Global.TRUE, "保存合同成功！");
	}
	
	/**
	 * 停用合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbSginContract tbSginContract) {
		tbSginContract.setStatus(TbSginContract.STATUS_DISABLE);
		tbSginContractService.updateStatus(tbSginContract);
		return renderResult(Global.TRUE, "停用合同成功");
	}
	
	/**
	 * 启用合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbSginContract tbSginContract) {
		tbSginContract.setStatus(TbSginContract.STATUS_NORMAL);
		tbSginContractService.updateStatus(tbSginContract);
		return renderResult(Global.TRUE, "启用合同成功");
	}
	
	/**
	 * 删除合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbSginContract tbSginContract) {
		tbSginContractService.delete(tbSginContract);
		return renderResult(Global.TRUE, "删除合同成功！");
	}
	
}