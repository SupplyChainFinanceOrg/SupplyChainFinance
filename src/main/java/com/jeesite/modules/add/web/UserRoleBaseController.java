/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.add.web;

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
import com.jeesite.modules.add.entity.UserRoleBase;
import com.jeesite.modules.add.service.UserRoleBaseService;

/**
 * 用户与角色关联表Controller
 * @author z
 * @version 2018-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/add/userRoleBase")
public class UserRoleBaseController extends BaseController {

	@Autowired
	private UserRoleBaseService userRoleBaseService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserRoleBase get(String userCode, String roleCode, boolean isNewRecord) {
		return userRoleBaseService.get(new Class<?>[]{String.class, String.class},
				new Object[]{userCode, roleCode}, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("add:userRoleBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserRoleBase userRoleBase, Model model) {
		model.addAttribute("userRoleBase", userRoleBase);
		return "modules/add/userRoleBaseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("add:userRoleBase:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserRoleBase> listData(UserRoleBase userRoleBase, HttpServletRequest request, HttpServletResponse response) {
		Page<UserRoleBase> page = userRoleBaseService.findPage(new Page<UserRoleBase>(request, response), userRoleBase); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("add:userRoleBase:view")
	@RequestMapping(value = "form")
	public String form(UserRoleBase userRoleBase, Model model) {
		model.addAttribute("userRoleBase", userRoleBase);
		return "modules/add/userRoleBaseForm";
	}

	/**
	 * 保存用户与角色关联表后加
	 */
	@RequiresPermissions("add:userRoleBase:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserRoleBase userRoleBase) {
		userRoleBaseService.save(userRoleBase);
		return renderResult(Global.TRUE, "保存用户与角色关联表后加成功！");
	}
	
	/**
	 * 删除用户与角色关联表后加
	 */
	@RequiresPermissions("add:userRoleBase:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserRoleBase userRoleBase) {
		userRoleBaseService.delete(userRoleBase);
		return renderResult(Global.TRUE, "删除用户与角色关联表后加成功！");
	}
	
}