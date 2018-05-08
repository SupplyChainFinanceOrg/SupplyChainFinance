/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.attachment.web;

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
import com.jeesite.modules.attachment.entity.TbAttApply;
import com.jeesite.modules.attachment.service.TbAttApplyService;

/**
 * tb_att_applyController
 * @author zhengkj
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/attachment/tbAttApply")
public class TbAttApplyController extends BaseController {

	@Autowired
	private TbAttApplyService tbAttApplyService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbAttApply get(String id, boolean isNewRecord) {
		return tbAttApplyService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("attachment:tbAttApply:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbAttApply tbAttApply, Model model) {
		model.addAttribute("tbAttApply", tbAttApply);
		return "modules/attachment/tbAttApplyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("attachment:tbAttApply:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbAttApply> listData(TbAttApply tbAttApply, HttpServletRequest request, HttpServletResponse response) {
		Page<TbAttApply> page = tbAttApplyService.findPage(new Page<TbAttApply>(request, response), tbAttApply); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("attachment:tbAttApply:view")
	@RequestMapping(value = "form")
	public String form(TbAttApply tbAttApply, Model model) {
		model.addAttribute("tbAttApply", tbAttApply);
		return "modules/attachment/tbAttApplyForm";
	}

	/**
	 * 保存附件
	 */
	@RequiresPermissions("attachment:tbAttApply:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbAttApply tbAttApply) {
		tbAttApplyService.save(tbAttApply);
		return renderResult(Global.TRUE, "保存附件成功！");
	}
	
	/**
	 * 停用附件
	 */
	@RequiresPermissions("attachment:tbAttApply:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbAttApply tbAttApply) {
		tbAttApply.setStatus(TbAttApply.STATUS_DISABLE);
		tbAttApplyService.updateStatus(tbAttApply);
		return renderResult(Global.TRUE, "停用附件成功");
	}
	
	/**
	 * 启用附件
	 */
	@RequiresPermissions("attachment:tbAttApply:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbAttApply tbAttApply) {
		tbAttApply.setStatus(TbAttApply.STATUS_NORMAL);
		tbAttApplyService.updateStatus(tbAttApply);
		return renderResult(Global.TRUE, "启用附件成功");
	}
	
	/**
	 * 删除附件
	 */
	@RequiresPermissions("attachment:tbAttApply:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbAttApply tbAttApply) {
		tbAttApplyService.delete(tbAttApply);
		return renderResult(Global.TRUE, "删除附件成功！");
	}
	
}