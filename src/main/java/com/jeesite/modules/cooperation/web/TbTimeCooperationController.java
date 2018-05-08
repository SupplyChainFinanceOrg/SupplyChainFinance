/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.cooperation.web;

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
import com.jeesite.modules.cooperation.entity.TbTimeCooperation;
import com.jeesite.modules.cooperation.service.TbTimeCooperationService;

/**
 * tb_time_cooperationController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cooperation/tbTimeCooperation")
public class TbTimeCooperationController extends BaseController {

	@Autowired
	private TbTimeCooperationService tbTimeCooperationService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbTimeCooperation get(String id, boolean isNewRecord) {
		return tbTimeCooperationService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("cooperation:tbTimeCooperation:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbTimeCooperation tbTimeCooperation, Model model) {
		model.addAttribute("tbTimeCooperation", tbTimeCooperation);
		return "modules/cooperation/tbTimeCooperationList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("cooperation:tbTimeCooperation:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbTimeCooperation> listData(TbTimeCooperation tbTimeCooperation, HttpServletRequest request, HttpServletResponse response) {
		Page<TbTimeCooperation> page = tbTimeCooperationService.findPage(new Page<TbTimeCooperation>(request, response), tbTimeCooperation); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("cooperation:tbTimeCooperation:view")
	@RequestMapping(value = "form")
	public String form(TbTimeCooperation tbTimeCooperation, Model model) {
		model.addAttribute("tbTimeCooperation", tbTimeCooperation);
		return "modules/cooperation/tbTimeCooperationForm";
	}

	/**
	 * 保存年限
	 */
	@RequiresPermissions("cooperation:tbTimeCooperation:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbTimeCooperation tbTimeCooperation) {
		tbTimeCooperationService.save(tbTimeCooperation);
		return renderResult(Global.TRUE, "保存年限成功！");
	}
	
	/**
	 * 停用年限
	 */
	@RequiresPermissions("cooperation:tbTimeCooperation:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbTimeCooperation tbTimeCooperation) {
		tbTimeCooperation.setStatus(TbTimeCooperation.STATUS_DISABLE);
		tbTimeCooperationService.updateStatus(tbTimeCooperation);
		return renderResult(Global.TRUE, "停用年限成功");
	}
	
	/**
	 * 启用年限
	 */
	@RequiresPermissions("cooperation:tbTimeCooperation:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbTimeCooperation tbTimeCooperation) {
		tbTimeCooperation.setStatus(TbTimeCooperation.STATUS_NORMAL);
		tbTimeCooperationService.updateStatus(tbTimeCooperation);
		return renderResult(Global.TRUE, "启用年限成功");
	}
	
	/**
	 * 删除年限
	 */
	@RequiresPermissions("cooperation:tbTimeCooperation:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbTimeCooperation tbTimeCooperation) {
		tbTimeCooperationService.delete(tbTimeCooperation);
		return renderResult(Global.TRUE, "删除年限成功！");
	}
	
}