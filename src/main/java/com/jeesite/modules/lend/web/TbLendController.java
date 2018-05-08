/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.lend.web;

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
import com.jeesite.modules.lend.entity.TbLend;
import com.jeesite.modules.lend.service.TbLendService;

/**
 * tb_lendController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/lend/tbLend")
public class TbLendController extends BaseController {

	@Autowired
	private TbLendService tbLendService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbLend get(String id, boolean isNewRecord) {
		return tbLendService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lend:tbLend:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbLend tbLend, Model model) {
		model.addAttribute("tbLend", tbLend);
		return "modules/lend/tbLendList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lend:tbLend:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbLend> listData(TbLend tbLend, HttpServletRequest request, HttpServletResponse response) {
		Page<TbLend> page = tbLendService.findPage(new Page<TbLend>(request, response), tbLend); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lend:tbLend:view")
	@RequestMapping(value = "form")
	public String form(TbLend tbLend, Model model) {
		model.addAttribute("tbLend", tbLend);
		return "modules/lend/tbLendForm";
	}

	/**
	 * 保存放贷
	 */
	@RequiresPermissions("lend:tbLend:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbLend tbLend) {
		tbLendService.save(tbLend);
		return renderResult(Global.TRUE, "保存放贷成功！");
	}
	
	/**
	 * 停用放贷
	 */
	@RequiresPermissions("lend:tbLend:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbLend tbLend) {
		tbLend.setStatus(TbLend.STATUS_DISABLE);
		tbLendService.updateStatus(tbLend);
		return renderResult(Global.TRUE, "停用放贷成功");
	}
	
	/**
	 * 启用放贷
	 */
	@RequiresPermissions("lend:tbLend:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbLend tbLend) {
		tbLend.setStatus(TbLend.STATUS_NORMAL);
		tbLendService.updateStatus(tbLend);
		return renderResult(Global.TRUE, "启用放贷成功");
	}
	
	/**
	 * 删除放贷
	 */
	@RequiresPermissions("lend:tbLend:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbLend tbLend) {
		tbLendService.delete(tbLend);
		return renderResult(Global.TRUE, "删除放贷成功！");
	}
	
}