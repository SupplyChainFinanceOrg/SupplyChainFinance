/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.control.web;

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
import com.jeesite.modules.control.entity.TbRiskControl;
import com.jeesite.modules.control.service.TbRiskControlService;

/**
 * tb_risk_controlController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/control/tbRiskControl")
public class TbRiskControlController extends BaseController {

	@Autowired
	private TbRiskControlService tbRiskControlService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbRiskControl get(String id, boolean isNewRecord) {
		return tbRiskControlService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("control:tbRiskControl:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbRiskControl tbRiskControl, Model model) {
		model.addAttribute("tbRiskControl", tbRiskControl);
		return "modules/control/tbRiskControlList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("control:tbRiskControl:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbRiskControl> listData(TbRiskControl tbRiskControl, HttpServletRequest request, HttpServletResponse response) {
		Page<TbRiskControl> page = tbRiskControlService.findPage(new Page<TbRiskControl>(request, response), tbRiskControl); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("control:tbRiskControl:view")
	@RequestMapping(value = "form")
	public String form(TbRiskControl tbRiskControl, Model model) {
		model.addAttribute("tbRiskControl", tbRiskControl);
		return "modules/control/tbRiskControlForm";
	}

	/**
	 * 保存风控内容表
	 */
	@RequiresPermissions("control:tbRiskControl:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbRiskControl tbRiskControl) {
		tbRiskControlService.save(tbRiskControl);
		return renderResult(Global.TRUE, "保存风控内容表成功！");
	}
	
	/**
	 * 停用风控内容表
	 */
	@RequiresPermissions("control:tbRiskControl:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbRiskControl tbRiskControl) {
		tbRiskControl.setStatus(TbRiskControl.STATUS_DISABLE);
		tbRiskControlService.updateStatus(tbRiskControl);
		return renderResult(Global.TRUE, "停用风控内容表成功");
	}
	
	/**
	 * 启用风控内容表
	 */
	@RequiresPermissions("control:tbRiskControl:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbRiskControl tbRiskControl) {
		tbRiskControl.setStatus(TbRiskControl.STATUS_NORMAL);
		tbRiskControlService.updateStatus(tbRiskControl);
		return renderResult(Global.TRUE, "启用风控内容表成功");
	}
	
	/**
	 * 删除风控内容表
	 */
	@RequiresPermissions("control:tbRiskControl:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbRiskControl tbRiskControl) {
		tbRiskControlService.delete(tbRiskControl);
		return renderResult(Global.TRUE, "删除风控内容表成功！");
	}
	
}