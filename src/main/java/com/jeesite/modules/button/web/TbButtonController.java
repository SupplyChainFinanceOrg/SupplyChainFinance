/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.button.web;

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
import com.jeesite.modules.button.entity.TbButton;
import com.jeesite.modules.button.service.TbButtonService;

/**
 * tb_buttonController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/button/tbButton")
public class TbButtonController extends BaseController {

	@Autowired
	private TbButtonService tbButtonService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbButton get(String id, boolean isNewRecord) {
		return tbButtonService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("button:tbButton:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbButton tbButton, Model model) {
		model.addAttribute("tbButton", tbButton);
		return "modules/button/tbButtonList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("button:tbButton:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbButton> listData(TbButton tbButton, HttpServletRequest request, HttpServletResponse response) {
		Page<TbButton> page = tbButtonService.findPage(new Page<TbButton>(request, response), tbButton); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("button:tbButton:view")
	@RequestMapping(value = "form")
	public String form(TbButton tbButton, Model model) {
		model.addAttribute("tbButton", tbButton);
		return "modules/button/tbButtonForm";
	}

	/**
	 * 保存按钮
	 */
	@RequiresPermissions("button:tbButton:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbButton tbButton) {
		tbButtonService.save(tbButton);
		return renderResult(Global.TRUE, "保存按钮成功！");
	}
	
	/**
	 * 停用按钮
	 */
	@RequiresPermissions("button:tbButton:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbButton tbButton) {
		tbButton.setStatus(TbButton.STATUS_DISABLE);
		tbButtonService.updateStatus(tbButton);
		return renderResult(Global.TRUE, "停用按钮成功");
	}
	
	/**
	 * 启用按钮
	 */
	@RequiresPermissions("button:tbButton:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbButton tbButton) {
		tbButton.setStatus(TbButton.STATUS_NORMAL);
		tbButtonService.updateStatus(tbButton);
		return renderResult(Global.TRUE, "启用按钮成功");
	}
	
	/**
	 * 删除按钮
	 */
	@RequiresPermissions("button:tbButton:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbButton tbButton) {
		tbButtonService.delete(tbButton);
		return renderResult(Global.TRUE, "删除按钮成功！");
	}
	
}