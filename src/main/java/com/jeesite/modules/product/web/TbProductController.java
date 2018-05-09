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
import com.jeesite.modules.product.entity.TbProduct;
import com.jeesite.modules.product.service.TbProductService;

/**
 * tb_productController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/product/tbProduct")
public class TbProductController extends BaseController {

	@Autowired
	private TbProductService tbProductService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbProduct get(String id, boolean isNewRecord) {
		return tbProductService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:tbProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbProduct tbProduct, Model model) {
		model.addAttribute("tbProduct", tbProduct);
		return "modules/product/tbProductList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:tbProduct:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbProduct> listData(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
		Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:tbProduct:view")
	@RequestMapping(value = "form")
	public String form(TbProduct tbProduct, Model model) {
		model.addAttribute("tbProduct", tbProduct);
		return "modules/product/tbProductForm";
	}

	/**
	 * 保存产品
	 */
	@RequiresPermissions("product:tbProduct:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbProduct tbProduct) {
		tbProductService.save(tbProduct);
		return renderResult(Global.TRUE, "保存产品成功！");
	}
	
	/**
	 * 停用产品
	 */
	@RequiresPermissions("product:tbProduct:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbProduct tbProduct) {
		tbProduct.setStatus(TbProduct.STATUS_DISABLE);
		tbProductService.updateStatus(tbProduct);
		return renderResult(Global.TRUE, "停用产品成功");
	}
	
	/**
	 * 启用产品
	 */
	@RequiresPermissions("product:tbProduct:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbProduct tbProduct) {
		tbProduct.setStatus(TbProduct.STATUS_NORMAL);
		tbProductService.updateStatus(tbProduct);
		return renderResult(Global.TRUE, "启用产品成功");
	}
	
	/**
	 * 删除产品
	 */
	@RequiresPermissions("product:tbProduct:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbProduct tbProduct) {
		tbProductService.delete(tbProduct);
		return renderResult(Global.TRUE, "删除产品成功！");
	}
	
}