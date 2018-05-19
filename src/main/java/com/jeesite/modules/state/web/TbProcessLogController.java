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
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.state.entity.TbProcess;
import com.jeesite.modules.state.entity.TbProcessLog;
import com.jeesite.modules.state.service.TbProcessLogService;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * tb_process_logController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/state/tbProcessLog")
public class TbProcessLogController extends BaseController {

	@Autowired
	private TbProcessLogService tbProcessLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbProcessLog get(String id, boolean isNewRecord) {
		return tbProcessLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value = {"list", ""})
	public String list(TbProcessLog tbProcessLog, Model model) {
		model.addAttribute("tbProcessLog", tbProcessLog);
		return "modules/state/tbProcessLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbProcessLog> listData(TbProcessLog tbProcessLog, HttpServletRequest request, HttpServletResponse response) {
		Page<TbProcessLog> page = new Page<TbProcessLog>(request, response);
		//tbProcessLog.getSqlMap().getWhere().and("operation_remark", QueryType.IS_NOT_NULL,"");
		//tbProcessLog.getSqlMap().getWhere().and("operation_remark", QueryType.NE," ");
		page = tbProcessLogService.findPage(page, tbProcessLog); 
		if(page!=null&&page.getList()!=null){
			for(TbProcessLog tb:page.getList()){
				tb.setUserId(UserUtils.get(tb.getUserId()).getUserName());
			}
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("state:tbProcessLog:view")
	@RequestMapping(value = "form")
	public String form(TbProcessLog tbProcessLog, Model model) {
		model.addAttribute("tbProcessLog", tbProcessLog);
		return "modules/state/tbProcessLogForm";
	}

	/**
	 * 保存流程日志
	 */
	@RequiresPermissions("state:tbProcessLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbProcessLog tbProcessLog) {
		tbProcessLogService.save(tbProcessLog);
		return renderResult(Global.TRUE, "保存流程日志成功！");
	}
	
	/**
	 * 删除流程日志
	 */
	@RequiresPermissions("state:tbProcessLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbProcessLog tbProcessLog) {
		tbProcessLogService.delete(tbProcessLog);
		return renderResult(Global.TRUE, "删除流程日志成功！");
	}
	
}