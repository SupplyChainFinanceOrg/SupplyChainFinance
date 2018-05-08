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
import com.jeesite.modules.contract.entity.TbContractField;
import com.jeesite.modules.contract.service.TbContractFieldService;

/**
 * tb_contract_fieldController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/contract/tbContractField")
public class TbContractFieldController extends BaseController {

	@Autowired
	private TbContractFieldService tbContractFieldService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbContractField get(String id, boolean isNewRecord) {
		return tbContractFieldService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("contract:tbContractField:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbContractField tbContractField, Model model) {
		model.addAttribute("tbContractField", tbContractField);
		return "modules/contract/tbContractFieldList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("contract:tbContractField:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbContractField> listData(TbContractField tbContractField, HttpServletRequest request, HttpServletResponse response) {
		Page<TbContractField> page = tbContractFieldService.findPage(new Page<TbContractField>(request, response), tbContractField); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("contract:tbContractField:view")
	@RequestMapping(value = "form")
	public String form(TbContractField tbContractField, Model model) {
		model.addAttribute("tbContractField", tbContractField);
		return "modules/contract/tbContractFieldForm";
	}

	/**
	 * 保存合同字段
	 */
	@RequiresPermissions("contract:tbContractField:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbContractField tbContractField) {
		tbContractFieldService.save(tbContractField);
		return renderResult(Global.TRUE, "保存合同字段成功！");
	}
	
	/**
	 * 停用合同字段
	 */
	@RequiresPermissions("contract:tbContractField:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbContractField tbContractField) {
		tbContractField.setStatus(TbContractField.STATUS_DISABLE);
		tbContractFieldService.updateStatus(tbContractField);
		return renderResult(Global.TRUE, "停用合同字段成功");
	}
	
	/**
	 * 启用合同字段
	 */
	@RequiresPermissions("contract:tbContractField:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbContractField tbContractField) {
		tbContractField.setStatus(TbContractField.STATUS_NORMAL);
		tbContractFieldService.updateStatus(tbContractField);
		return renderResult(Global.TRUE, "启用合同字段成功");
	}
	
	/**
	 * 删除合同字段
	 */
	@RequiresPermissions("contract:tbContractField:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbContractField tbContractField) {
		tbContractFieldService.delete(tbContractField);
		return renderResult(Global.TRUE, "删除合同字段成功！");
	}
	
}