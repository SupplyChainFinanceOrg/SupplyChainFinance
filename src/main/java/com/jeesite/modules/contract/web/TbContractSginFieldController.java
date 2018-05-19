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
import com.jeesite.modules.contract.entity.TbContractSginField;
import com.jeesite.modules.contract.service.TbContractSginFieldService;

/**
 * tb_sgin_contractController
 * @author zhengkj
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/contract/tbContractSginField")
public class TbContractSginFieldController extends BaseController {

	@Autowired
	private TbContractSginFieldService tbSginContractService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbContractSginField get(String id, boolean isNewRecord) {
		return tbSginContractService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("contract:tbSginContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbContractSginField tbSginContract, Model model) {
		model.addAttribute("tbSginContract", tbSginContract);
		return "modules/contract/tbSginContractList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("contract:tbSginContract:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbContractSginField> listData(TbContractSginField tbSginContract, HttpServletRequest request, HttpServletResponse response) {
		Page<TbContractSginField> page = tbSginContractService.findPage(new Page<TbContractSginField>(request, response), tbSginContract); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("contract:tbSginContract:view")
	@RequestMapping(value = "form")
	public String form(TbContractSginField tbSginContract, Model model) {
		model.addAttribute("tbSginContract", tbSginContract);
		return "modules/contract/tbSginContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbContractSginField tbSginContract) {
		tbSginContractService.save(tbSginContract);
		return renderResult(Global.TRUE, "保存合同成功！");
	}
	
	/**
	 * 停用合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbContractSginField tbSginContract) {
		tbSginContract.setStatus(TbContractSginField.STATUS_DISABLE);
		tbSginContractService.updateStatus(tbSginContract);
		return renderResult(Global.TRUE, "停用合同成功");
	}
	
	/**
	 * 启用合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbContractSginField tbSginContract) {
		tbSginContract.setStatus(TbContractSginField.STATUS_NORMAL);
		tbSginContractService.updateStatus(tbSginContract);
		return renderResult(Global.TRUE, "启用合同成功");
	}
	
	/**
	 * 删除合同
	 */
	@RequiresPermissions("contract:tbSginContract:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbContractSginField tbSginContract) {
		tbSginContractService.delete(tbSginContract);
		return renderResult(Global.TRUE, "删除合同成功！");
	}
	
}