/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.web;

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
import com.jeesite.modules.state.entity.TbState;
import com.jeesite.modules.state.service.TbStateService;

/**
 * tb_stateController
 * @author z
 * @version 2018-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/state/tbState")
public class TbStateController extends BaseController {

	@Autowired
	private TbStateService tbStateService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbState get(String id, boolean isNewRecord) {
		return tbStateService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("state:tbState:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbState tbState, Model model) {
		model.addAttribute("tbState", tbState);
		return "modules/state/tbStateList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("state:tbState:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbState> listData(TbState tbState, HttpServletRequest request, HttpServletResponse response) {
		Page<TbState> page = tbStateService.findPage(new Page<TbState>(request, response), tbState); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("state:tbState:view")
	@RequestMapping(value = "form")
	public String form(TbState tbState, Model model) {
		model.addAttribute("tbState", tbState);
		return "modules/state/tbStateForm";
	}

	/**
	 * 保存状态
	 */
	@RequiresPermissions("state:tbState:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbState tbState) {
		tbStateService.save(tbState);
		return renderResult(Global.TRUE, "保存状态成功！");
	}
	
	/**
	 * 停用状态
	 */
	@RequiresPermissions("state:tbState:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbState tbState) {
		tbState.setStatus(TbState.STATUS_DISABLE);
		tbStateService.updateStatus(tbState);
		return renderResult(Global.TRUE, "停用状态成功");
	}
	
	/**
	 * 启用状态
	 */
	@RequiresPermissions("state:tbState:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbState tbState) {
		tbState.setStatus(TbState.STATUS_NORMAL);
		tbStateService.updateStatus(tbState);
		return renderResult(Global.TRUE, "启用状态成功");
	}
	
	/**
	 * 删除状态
	 */
	@RequiresPermissions("state:tbState:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbState tbState) {
		tbStateService.delete(tbState);
		return renderResult(Global.TRUE, "删除状态成功！");
	}
	
}