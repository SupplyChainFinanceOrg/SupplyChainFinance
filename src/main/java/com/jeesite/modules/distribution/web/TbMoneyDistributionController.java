/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.web;

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
import com.jeesite.modules.distribution.entity.TbMoneyDistribution;
import com.jeesite.modules.distribution.service.TbMoneyDistributionService;

/**
 * tb_money_distributionController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/distribution/tbMoneyDistribution")
public class TbMoneyDistributionController extends BaseController {

	@Autowired
	private TbMoneyDistributionService tbMoneyDistributionService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbMoneyDistribution get(String id, boolean isNewRecord) {
		return tbMoneyDistributionService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("distribution:tbMoneyDistribution:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbMoneyDistribution tbMoneyDistribution, Model model) {
		model.addAttribute("tbMoneyDistribution", tbMoneyDistribution);
		return "modules/distribution/tbMoneyDistributionList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("distribution:tbMoneyDistribution:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbMoneyDistribution> listData(TbMoneyDistribution tbMoneyDistribution, HttpServletRequest request, HttpServletResponse response) {
		Page<TbMoneyDistribution> page = tbMoneyDistributionService.findPage(new Page<TbMoneyDistribution>(request, response), tbMoneyDistribution); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("distribution:tbMoneyDistribution:view")
	@RequestMapping(value = "form")
	public String form(TbMoneyDistribution tbMoneyDistribution, Model model) {
		model.addAttribute("tbMoneyDistribution", tbMoneyDistribution);
		return "modules/distribution/tbMoneyDistributionForm";
	}

	/**
	 * 保存申请放款表
	 */
	@RequiresPermissions("distribution:tbMoneyDistribution:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbMoneyDistribution tbMoneyDistribution) {
		tbMoneyDistributionService.save(tbMoneyDistribution);
		return renderResult(Global.TRUE, "保存申请放款表成功！");
	}
	
	/**
	 * 停用申请放款表
	 */
	@RequiresPermissions("distribution:tbMoneyDistribution:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbMoneyDistribution tbMoneyDistribution) {
		tbMoneyDistribution.setStatus(TbMoneyDistribution.STATUS_DISABLE);
		tbMoneyDistributionService.updateStatus(tbMoneyDistribution);
		return renderResult(Global.TRUE, "停用申请放款表成功");
	}
	
	/**
	 * 启用申请放款表
	 */
	@RequiresPermissions("distribution:tbMoneyDistribution:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbMoneyDistribution tbMoneyDistribution) {
		tbMoneyDistribution.setStatus(TbMoneyDistribution.STATUS_NORMAL);
		tbMoneyDistributionService.updateStatus(tbMoneyDistribution);
		return renderResult(Global.TRUE, "启用申请放款表成功");
	}
	
	/**
	 * 删除申请放款表
	 */
	@RequiresPermissions("distribution:tbMoneyDistribution:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbMoneyDistribution tbMoneyDistribution) {
		tbMoneyDistributionService.delete(tbMoneyDistribution);
		return renderResult(Global.TRUE, "删除申请放款表成功！");
	}
	
}