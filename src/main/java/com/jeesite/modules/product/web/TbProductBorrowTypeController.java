/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.web;

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
import com.jeesite.modules.product.entity.TbProductBorrowType;
import com.jeesite.modules.product.service.TbProductBorrowTypeService;

/**
 * tb_product_borrow_typeController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/product/tbProductBorrowType")
public class TbProductBorrowTypeController extends BaseController {

	@Autowired
	private TbProductBorrowTypeService tbProductBorrowTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbProductBorrowType get(String id, boolean isNewRecord) {
		return tbProductBorrowTypeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:tbProductBorrowType:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbProductBorrowType tbProductBorrowType, Model model) {
		model.addAttribute("tbProductBorrowType", tbProductBorrowType);
		return "modules/product/tbProductBorrowTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:tbProductBorrowType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbProductBorrowType> listData(TbProductBorrowType tbProductBorrowType, HttpServletRequest request, HttpServletResponse response) {
		Page<TbProductBorrowType> page = tbProductBorrowTypeService.findPage(new Page<TbProductBorrowType>(request, response), tbProductBorrowType); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:tbProductBorrowType:view")
	@RequestMapping(value = "form")
	public String form(TbProductBorrowType tbProductBorrowType, Model model) {
		model.addAttribute("tbProductBorrowType", tbProductBorrowType);
		return "modules/product/tbProductBorrowTypeForm";
	}

	/**
	 * 保存产品类型
	 */
	@RequiresPermissions("product:tbProductBorrowType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbProductBorrowType tbProductBorrowType) {
		tbProductBorrowTypeService.save(tbProductBorrowType);
		return renderResult(Global.TRUE, "保存产品类型成功！");
	}
	
	/**
	 * 停用产品类型
	 */
	@RequiresPermissions("product:tbProductBorrowType:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbProductBorrowType tbProductBorrowType) {
		tbProductBorrowType.setStatus(TbProductBorrowType.STATUS_DISABLE);
		tbProductBorrowTypeService.updateStatus(tbProductBorrowType);
		return renderResult(Global.TRUE, "停用产品类型成功");
	}
	
	/**
	 * 启用产品类型
	 */
	@RequiresPermissions("product:tbProductBorrowType:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbProductBorrowType tbProductBorrowType) {
		tbProductBorrowType.setStatus(TbProductBorrowType.STATUS_NORMAL);
		tbProductBorrowTypeService.updateStatus(tbProductBorrowType);
		return renderResult(Global.TRUE, "启用产品类型成功");
	}
	
	/**
	 * 删除产品类型
	 */
	@RequiresPermissions("product:tbProductBorrowType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbProductBorrowType tbProductBorrowType) {
		tbProductBorrowTypeService.delete(tbProductBorrowType);
		return renderResult(Global.TRUE, "删除产品类型成功！");
	}
	
}