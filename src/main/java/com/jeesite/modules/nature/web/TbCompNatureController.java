/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.nature.web;

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
import com.jeesite.modules.nature.entity.TbCompNature;
import com.jeesite.modules.nature.service.TbCompNatureService;

/**
 * tb_comp_natureController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/nature/tbCompNature")
public class TbCompNatureController extends BaseController {

	@Autowired
	private TbCompNatureService tbCompNatureService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbCompNature get(String id, boolean isNewRecord) {
		return tbCompNatureService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("nature:tbCompNature:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbCompNature tbCompNature, Model model) {
		model.addAttribute("tbCompNature", tbCompNature);
		return "modules/nature/tbCompNatureList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("nature:tbCompNature:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbCompNature> listData(TbCompNature tbCompNature, HttpServletRequest request, HttpServletResponse response) {
		Page<TbCompNature> page = tbCompNatureService.findPage(new Page<TbCompNature>(request, response), tbCompNature); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("nature:tbCompNature:view")
	@RequestMapping(value = "form")
	public String form(TbCompNature tbCompNature, Model model) {
		model.addAttribute("tbCompNature", tbCompNature);
		return "modules/nature/tbCompNatureForm";
	}

	/**
	 * 保存公司性质表
	 */
	@RequiresPermissions("nature:tbCompNature:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbCompNature tbCompNature) {
		tbCompNatureService.save(tbCompNature);
		return renderResult(Global.TRUE, "保存公司性质表成功！");
	}
	
	/**
	 * 停用公司性质表
	 */
	@RequiresPermissions("nature:tbCompNature:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbCompNature tbCompNature) {
		tbCompNature.setStatus(TbCompNature.STATUS_DISABLE);
		tbCompNatureService.updateStatus(tbCompNature);
		return renderResult(Global.TRUE, "停用公司性质表成功");
	}
	
	/**
	 * 启用公司性质表
	 */
	@RequiresPermissions("nature:tbCompNature:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbCompNature tbCompNature) {
		tbCompNature.setStatus(TbCompNature.STATUS_NORMAL);
		tbCompNatureService.updateStatus(tbCompNature);
		return renderResult(Global.TRUE, "启用公司性质表成功");
	}
	
	/**
	 * 删除公司性质表
	 */
	@RequiresPermissions("nature:tbCompNature:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbCompNature tbCompNature) {
		tbCompNatureService.delete(tbCompNature);
		return renderResult(Global.TRUE, "删除公司性质表成功！");
	}
	
}