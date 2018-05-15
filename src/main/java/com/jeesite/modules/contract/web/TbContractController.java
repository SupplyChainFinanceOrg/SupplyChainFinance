/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

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
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.contract.dao.TbContractSignDao;
import com.jeesite.modules.contract.entity.TbContract;
import com.jeesite.modules.contract.entity.TbContractApi;
import com.jeesite.modules.contract.entity.TbContractSign;
import com.jeesite.modules.contract.entity.TbSginContract;
import com.jeesite.modules.contract.service.TbContractApiService;
import com.jeesite.modules.contract.service.TbContractService;
import com.jeesite.modules.counter.dao.TbCounterDao;

/**
 * tb_contractController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/contract/tbContract")
public class TbContractController extends BaseController {

	@Autowired
	private TbContractService tbContractService;
	@Autowired
	private TbContractSignDao tbContractSignDao;
//	@Autowired 
//	private TbContractFieldService tbContractFieldService;
	@Autowired
	private TbContractApiService tbContractApiService;




	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbContract get(String id, boolean isNewRecord) {
		return tbContractService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbContract tbContract, Model model) {
		model.addAttribute("tbContract", tbContract);
		return "modules/contract/tbContractList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbContract> listData(TbContract tbContract, HttpServletRequest request, HttpServletResponse response) {
		Page<TbContract> page = tbContractService.findPage(new Page<TbContract>(request, response), tbContract); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = "form")
	public String form(TbContract tbContract, Model model) {
		model.addAttribute("tbContract", tbContract);
		return "modules/contract/tbContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbContract tbContract) {
		tbContractService.save(tbContract);
		return renderResult(Global.TRUE, "保存合同成功！");
	}

	/**
	 * 停用合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbContract tbContract) {
		tbContract.setStatus(TbContract.STATUS_DISABLE);
		tbContractService.updateStatus(tbContract);
		return renderResult(Global.TRUE, "停用合同成功");
	}

	/**
	 * 启用合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbContract tbContract) {
		tbContract.setStatus(TbContract.STATUS_NORMAL);
		tbContractService.updateStatus(tbContract);
		return renderResult(Global.TRUE, "启用合同成功");
	}

	/**
	 * 删除合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbContract tbContract) {
		tbContractService.delete(tbContract);
		return renderResult(Global.TRUE, "删除合同成功！");
	}
	/**
	 * 创建合同主页面
	 * @param response
	 * @param model
	 * @return
	 */

	@RequestMapping(value = {"contractMain"})
	public String contractMain(HttpServletResponse response,HttpServletRequest request,Model model) {
		String loanId=request.getParameter("loanId");
		String state=request.getParameter("state");
		TbContractSign contractSign=new TbContractSign();
		contractSign.setLoanId(loanId);
		TbContract contract=new TbContract();
		contract.setProductId(1+"");
		contract.setSignState(state);
		List<TbContract> contractList=tbContractService.findList(contract);
		List<TbContractSign> contractSignList=tbContractSignDao.findList(contractSign);
		if(contractSignList==null||contractSignList.size()==0){
			contractSignList=tbContractService.contractSetting(state,loanId,contractList);
		}
		model.addAttribute("contractSignList", contractSignList);
		model.addAttribute("contractList", contractList);
		return "modules/contract/contractMain";
	}
	/**
	 * 参数设定页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"parmSetting"})
	public String parmSetting(HttpServletResponse response,HttpServletRequest request,Model model){
		String loanId=request.getParameter("loanId");
		String state=request.getParameter("state");
		TbContract contract=new TbContract();
		contract.setProductId(loanId);
		contract.setSignState(state);
		List<TbContract> contractList=tbContractService.findList(contract);
		Map<String,Object> map=tbContractService.getSettingData(state, loanId);
		model.addAttribute("contractList", contractList);
		model.addAttribute("csList", (List<TbContractSign>)map.get("csList"));
		model.addAttribute("scList", (List<TbSginContract>)map.get("scList"));
		return "modules/contract/tbContractFieldForm";

	}
	/**
	 * 参数设定提交
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"submitSettingParm"})
	public String submitSettingParm(HttpServletResponse response,HttpServletRequest request,Model model){
		String[] ids =request.getParameterValues("ids");
		String[] values =request.getParameterValues("values");
		tbContractService.settingParm(ids,values);
		return "redirect:contractMain"; 
	}
	/**
	 * 一键签约
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"signCountarct"})
	public String signCountarct(HttpServletResponse response,HttpServletRequest request,Model model){
		String loanId=request.getParameter("loanId");
		String state=request.getParameter("state");
		String compId=request.getParameter("compId");
		try {
			tbContractService.signCountarct(state, loanId,compId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	@Autowired
	TbCounterDao tbCounterDao;
	@RequestMapping(value = {"counterTest"})
	public String counterTest(HttpServletResponse response,HttpServletRequest request,Model model){
		TbContractApi api=new TbContractApi();
		api.setCompId("995160567265361920");
		api=tbContractApiService.get(api);
		System.err.println(api.getCert());
		return "counter";
	}
	public static void main(String[] args) {
		String s="";
		System.err.println(StringUtils.isNotBlank(s));
	}
	public static boolean WriteStringToFile(String filePath,String str) {
		try {
			File file = new File(filePath);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(str);// 往文件里写入字符串
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


}