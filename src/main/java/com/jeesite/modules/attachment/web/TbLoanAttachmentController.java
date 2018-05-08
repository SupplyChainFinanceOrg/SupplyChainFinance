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
import com.jeesite.modules.attachment.entity.TbLoanAttachment;
import com.jeesite.modules.attachment.service.TbLoanAttachmentService;

/**
 * tb_loan_attachmentController
 * @author zhengkj
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/attachment/tbLoanAttachment")
public class TbLoanAttachmentController extends BaseController {

	@Autowired
	private TbLoanAttachmentService tbLoanAttachmentService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbLoanAttachment get(String id, boolean isNewRecord) {
		return tbLoanAttachmentService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("attachment:tbLoanAttachment:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbLoanAttachment tbLoanAttachment, Model model) {
		model.addAttribute("tbLoanAttachment", tbLoanAttachment);
		return "modules/attachment/tbLoanAttachmentList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("attachment:tbLoanAttachment:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbLoanAttachment> listData(TbLoanAttachment tbLoanAttachment, HttpServletRequest request, HttpServletResponse response) {
		Page<TbLoanAttachment> page = tbLoanAttachmentService.findPage(new Page<TbLoanAttachment>(request, response), tbLoanAttachment); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("attachment:tbLoanAttachment:view")
	@RequestMapping(value = "form")
	public String form(TbLoanAttachment tbLoanAttachment, Model model) {
		model.addAttribute("tbLoanAttachment", tbLoanAttachment);
		return "modules/attachment/tbLoanAttachmentForm";
	}

	/**
	 * 保存附件
	 */
	@RequiresPermissions("attachment:tbLoanAttachment:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbLoanAttachment tbLoanAttachment) {
		tbLoanAttachmentService.save(tbLoanAttachment);
		return renderResult(Global.TRUE, "保存附件成功！");
	}
	
	/**
	 * 停用附件
	 */
	@RequiresPermissions("attachment:tbLoanAttachment:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbLoanAttachment tbLoanAttachment) {
		tbLoanAttachment.setStatus(TbLoanAttachment.STATUS_DISABLE);
		tbLoanAttachmentService.updateStatus(tbLoanAttachment);
		return renderResult(Global.TRUE, "停用附件成功");
	}
	
	/**
	 * 启用附件
	 */
	@RequiresPermissions("attachment:tbLoanAttachment:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbLoanAttachment tbLoanAttachment) {
		tbLoanAttachment.setStatus(TbLoanAttachment.STATUS_NORMAL);
		tbLoanAttachmentService.updateStatus(tbLoanAttachment);
		return renderResult(Global.TRUE, "启用附件成功");
	}
	
	/**
	 * 删除附件
	 */
	@RequiresPermissions("attachment:tbLoanAttachment:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbLoanAttachment tbLoanAttachment) {
		tbLoanAttachmentService.delete(tbLoanAttachment);
		return renderResult(Global.TRUE, "删除附件成功！");
	}
	
}