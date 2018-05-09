/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.tb.web;

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
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;

/**
 * tb_compController
 * @author zheng
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/tb/tbComp")
public class TbCompController extends BaseController {

	@Autowired
	private TbCompService tbCompService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbComp get(String id, boolean isNewRecord) {
		return tbCompService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tb:tbComp:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbComp tbComp, Model model) {
		model.addAttribute("tbComp", tbComp);
		return "modules/tb/tbCompList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tb:tbComp:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbComp> listData(TbComp tbComp, HttpServletRequest request, HttpServletResponse response) {
		Page<TbComp> page = tbCompService.findPage(new Page<TbComp>(request, response), tbComp); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tb:tbComp:view")
	@RequestMapping(value = "form")
	public String form(TbComp tbComp, Model model) {
		model.addAttribute("tbComp", tbComp);
		return "modules/tb/tbCompForm";
	}
	@RequestMapping(value = "regUser")
	public String reguser(TbComp tbComp, Model model) {
		model.addAttribute("tbComp", tbComp);
		return "modules/tb/tbCompForm";
	}
	/**
	 * 保存tb_comp
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbComp tbComp) {
		tbCompService.save(tbComp);
		return renderResult(Global.TRUE, "保存tb_comp成功！");
	}
	
	/**
	 * 停用tb_comp
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbComp tbComp) {
		tbComp.setStatus(TbComp.STATUS_DISABLE);
		tbCompService.updateStatus(tbComp);
		return renderResult(Global.TRUE, "停用tb_comp成功");
	}
	
	/**
	 * 启用tb_comp
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbComp tbComp) {
		tbComp.setStatus(TbComp.STATUS_NORMAL);
		tbCompService.updateStatus(tbComp);
		return renderResult(Global.TRUE, "启用tb_comp成功");
	}
	
	/**
	 * 删除tb_comp
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbComp tbComp) {
		tbCompService.delete(tbComp);
		return renderResult(Global.TRUE, "删除tb_comp成功！");
	}
	
}