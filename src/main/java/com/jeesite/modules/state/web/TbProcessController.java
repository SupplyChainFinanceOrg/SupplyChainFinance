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
import com.jeesite.modules.state.entity.TbProcess;
import com.jeesite.modules.state.service.TbProcessService;

/**
 * tb_processController
 * @author z
 * @version 2018-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/state/tbProcess")
public class TbProcessController extends BaseController {

	@Autowired
	private TbProcessService tbProcessService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbProcess get(String id, boolean isNewRecord) {
		return tbProcessService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("state:tbProcess:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbProcess tbProcess, Model model) {
		model.addAttribute("tbProcess", tbProcess);
		return "modules/state/tbProcessList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("state:tbProcess:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbProcess> listData(TbProcess tbProcess, HttpServletRequest request, HttpServletResponse response) {
		Page<TbProcess> page = new Page<TbProcess>(request, response);
		page = tbProcessService.findPage(page, tbProcess); 
		
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("state:tbProcess:view")
	@RequestMapping(value = "form")
	public String form(TbProcess tbProcess, Model model) {
		model.addAttribute("tbProcess", tbProcess);
		return "modules/state/tbProcessForm";
	}

	/**
	 * 保存流程
	 */
	@RequiresPermissions("state:tbProcess:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbProcess tbProcess) {
		tbProcessService.save(tbProcess);
		return renderResult(Global.TRUE, "保存流程成功！");
	}
	
	/**
	 * 停用流程
	 */
	@RequiresPermissions("state:tbProcess:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbProcess tbProcess) {
		tbProcess.setStatus(TbProcess.STATUS_DISABLE);
		tbProcessService.updateStatus(tbProcess);
		return renderResult(Global.TRUE, "停用流程成功");
	}
	
	/**
	 * 启用流程
	 */
	@RequiresPermissions("state:tbProcess:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbProcess tbProcess) {
		tbProcess.setStatus(TbProcess.STATUS_NORMAL);
		tbProcessService.updateStatus(tbProcess);
		return renderResult(Global.TRUE, "启用流程成功");
	}
	
	/**
	 * 删除流程
	 */
	@RequiresPermissions("state:tbProcess:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbProcess tbProcess) {
		tbProcessService.delete(tbProcess);
		return renderResult(Global.TRUE, "删除流程成功！");
	}
	
}