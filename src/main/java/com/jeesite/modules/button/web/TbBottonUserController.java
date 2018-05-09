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
import com.jeesite.modules.button.entity.TbBottonUser;
import com.jeesite.modules.button.service.TbBottonUserService;

/**
 * tb_botton_userController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/button/tbBottonUser")
public class TbBottonUserController extends BaseController {

	@Autowired
	private TbBottonUserService tbBottonUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbBottonUser get(String borrowId, boolean isNewRecord) {
		return tbBottonUserService.get(borrowId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("button:tbBottonUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbBottonUser tbBottonUser, Model model) {
		model.addAttribute("tbBottonUser", tbBottonUser);
		return "modules/button/tbBottonUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("button:tbBottonUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbBottonUser> listData(TbBottonUser tbBottonUser, HttpServletRequest request, HttpServletResponse response) {
		Page<TbBottonUser> page = tbBottonUserService.findPage(new Page<TbBottonUser>(request, response), tbBottonUser); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("button:tbBottonUser:view")
	@RequestMapping(value = "form")
	public String form(TbBottonUser tbBottonUser, Model model) {
		model.addAttribute("tbBottonUser", tbBottonUser);
		return "modules/button/tbBottonUserForm";
	}

	/**
	 * 保存按钮用户
	 */
	@RequiresPermissions("button:tbBottonUser:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbBottonUser tbBottonUser) {
		tbBottonUserService.save(tbBottonUser);
		return renderResult(Global.TRUE, "保存按钮用户成功！");
	}
	
	/**
	 * 停用按钮用户
	 */
	@RequiresPermissions("button:tbBottonUser:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbBottonUser tbBottonUser) {
		tbBottonUser.setStatus(TbBottonUser.STATUS_DISABLE);
		tbBottonUserService.updateStatus(tbBottonUser);
		return renderResult(Global.TRUE, "停用按钮用户成功");
	}
	
	/**
	 * 启用按钮用户
	 */
	@RequiresPermissions("button:tbBottonUser:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbBottonUser tbBottonUser) {
		tbBottonUser.setStatus(TbBottonUser.STATUS_NORMAL);
		tbBottonUserService.updateStatus(tbBottonUser);
		return renderResult(Global.TRUE, "启用按钮用户成功");
	}
	
	/**
	 * 删除按钮用户
	 */
	@RequiresPermissions("button:tbBottonUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbBottonUser tbBottonUser) {
		tbBottonUserService.delete(tbBottonUser);
		return renderResult(Global.TRUE, "删除按钮用户成功！");
	}
	
}