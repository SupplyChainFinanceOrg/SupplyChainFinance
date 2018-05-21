/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.jeesite.modules.apply.service.TbLoanApplyService;
import com.jeesite.modules.contract.dao.TbContractSignDao;
import com.jeesite.modules.contract.entity.TbContractTemp;
import com.jeesite.modules.contract.entity.TbContractApi;
import com.jeesite.modules.contract.entity.TbContractSign;
import com.jeesite.modules.contract.entity.TbContractSginField;
import com.jeesite.modules.contract.service.TbContractApiService;
import com.jeesite.modules.contract.service.TbContractService;
import com.jeesite.modules.contract.utils.ContarctUtils;
import com.jeesite.modules.counter.dao.TbCounterDao;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;

/**
 * tb_contractController
 * @author z
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/contract/tbContract")
public class TbContractController extends BaseController {

	@Value("${contractApi.downloadPath}")
	String downloadPath;
	@Autowired
	private TbContractService tbContractService;
	@Autowired
	private TbContractSignDao tbContractSignDao;
	@Autowired
	private TbLoanApplyService tbLoanApplyService;

	@Autowired
	private RoleService roleService;


	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbContractTemp get(String id, boolean isNewRecord) {
		return tbContractService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbContractTemp tbContract, Model model) {
		model.addAttribute("tbContract", tbContract);
		return "modules/contract/tbContractList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbContractTemp> listData(TbContractTemp tbContract, HttpServletRequest request, HttpServletResponse response) {
		Page<TbContractTemp> page = tbContractService.findPage(new Page<TbContractTemp>(request, response), tbContract); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("contract:tbContract:view")
	@RequestMapping(value = "form")
	public String form(TbContractTemp tbContract, Model model) {
		model.addAttribute("tbContract", tbContract);
		return "modules/contract/tbContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbContractTemp tbContract) {
		tbContractService.save(tbContract);
		return renderResult(Global.TRUE, "保存合同成功！");
	}

	/**
	 * 停用合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbContractTemp tbContract) {
		tbContract.setStatus(TbContractTemp.STATUS_DISABLE);
		tbContractService.updateStatus(tbContract);
		return renderResult(Global.TRUE, "停用合同成功");
	}

	/**
	 * 启用合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbContractTemp tbContract) {
		tbContract.setStatus(TbContractTemp.STATUS_NORMAL);
		tbContractService.updateStatus(tbContract);
		return renderResult(Global.TRUE, "启用合同成功");
	}

	/**
	 * 删除合同
	 */
	@RequiresPermissions("contract:tbContract:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbContractTemp tbContract) {
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
		TbContractSign contractSign=new TbContractSign();
		contractSign.setLoanId(loanId);
		
		TbContractTemp contract=new TbContractTemp();
		contract.setProductId(tbLoanApplyService.get(loanId).getProductId()+"");
		if("three".equals(request.getParameter("waytype"))){
			contractSign.setSignType(3);
			contract.setId("5");
		}
		List<TbContractTemp> contractList=tbContractService.findList(contract);
		List<TbContractSign> contractSignList=tbContractSignDao.findList(contractSign);
		if(contractSignList==null||contractSignList.size()==0){
			contractSignList=tbContractService.contractSetting(loanId,contractList);
		}
		model.addAttribute("contractSignList", contractSignList);
		model.addAttribute("contractList", contractList);
		model.addAttribute("contractSign", contractSign);
		model.addAttribute("tbLoanApply", tbLoanApplyService.get(loanId));	
		if(StringUtils.isNoneBlank(request.getParameter("waytype"))){
			//2为借款企业签署
			return "modules/contract/contractMain"+request.getParameter("waytype");
		}
		return "modules/contract/contractMain";
	}
	@RequestMapping(value = {"contractMainDetail"})
	public String contractMainDetail(HttpServletResponse response,HttpServletRequest request,Model model) {
		String loanId=request.getParameter("loanId");
		TbContractSign contractSign=new TbContractSign();
		contractSign.setLoanId(loanId);
		TbContractTemp contract=new TbContractTemp();
		contract.setProductId(tbLoanApplyService.get(loanId).getProductId()+"");
		User user =UserUtils.getUser();
		Role r=new Role();
		r.setUserCode(user.getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);

		if(rolelist!=null&&rolelist.size()>0&&TbComp.HXQYROLECODE.equals(rolelist.get(0).getRoleCode())){
			contractSign.setSignType(3);
			contract.setId("5");
		}
	
		List<TbContractTemp> contractList=tbContractService.findList(contract);
		List<TbContractSign> contractSignList=tbContractSignDao.findList(contractSign);
		if(contractSignList==null||contractSignList.size()==0){
			contractSignList=tbContractService.contractSetting(loanId,contractList);
		}
		model.addAttribute("contractSignList", contractSignList);
		model.addAttribute("contractList", contractList);
		model.addAttribute("contractSign", contractSign);
		model.addAttribute("tbLoanApply", tbLoanApplyService.get(loanId));		
		return "modules/contract/contractMainDetail";
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
		TbContractTemp contract=new TbContractTemp();
		contract.setProductId(tbLoanApplyService.get(loanId).getProductId()+"");
		List<TbContractTemp> contractList=tbContractService.findList(contract);
		Map<String,Object> map=tbContractService.getSettingData(loanId);
		model.addAttribute("contractList", contractList);
		model.addAttribute("csList", (List<TbContractSign>)map.get("csList"));
		model.addAttribute("scList", (List<TbContractSginField>)map.get("scList"));
		model.addAttribute("ploadId", loanId);
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
		String loanId=request.getParameter("loanId");	
		return "redirect:contractMain?loanId="+loanId; 
	}
	/**
	 * 平台用户一键签约
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"signCountarct"})
	public String signCountarct(HttpServletResponse response,HttpServletRequest request,Model model){
		String loanId=request.getParameter("loanId");
		try {
			boolean sign=tbContractService.signCountarct(loanId);//注册 获取 上上签 并且生成pdf合同模板 进行签约
			
			System.err.println(sign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 借款企业签约
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"signCountarctByLoanComp"})
	public String signCountarctByLoanComp(HttpServletResponse response,HttpServletRequest request,Model model){
		String loanId=request.getParameter("loanId");
		try {
			boolean sign=tbContractService.signCountarctByLoanComp(loanId);
			
		//	boolean sign=tbContractService.signCountarct(state, loanId,compId);
			System.err.println(sign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * 核心企业签署回执
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"signCountarctByCoreComp"})
	public String signCountarctByCoreComp(HttpServletResponse response,HttpServletRequest request,Model model){
		String loanId=request.getParameter("loanId");
		try {
			boolean sign=tbContractService.signCountarctByCoreComp(loanId);
			
		//	boolean sign=tbContractService.signCountarct(state, loanId,compId);
			System.err.println(sign);
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
		String loanId="995224879621619712";
		SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
		Date date=new Date();
		String path=downloadPath+"/"+ sdf.format(date)+"/"+loanId+"/";
		File file=new File(path);
		if(!file.exists()){//创建签约路径
			file.mkdirs();
		}
		path=path+"/hhhhh.pdf";
		try {
			ContarctUtils.downloadNet(response,"https://openapi.bestsign.info/openapi/v3/storage/download/?developerId=1845372398275133986&rtick=1526446535441&dataToken=eyJzZXJ2ZXIiOiJodHRwczovL29wZW5hcGkuYmVzdHNpZ24uaW5mby9vcGVuYXBpL3YzLyIsImZuYW1lIjoi5L%2Bd55CG5ZCI5ZCMXzE1MjYyODExNTcxMDgucGRmIiwiZGhhc2giOiIxOTkzMjA5MDIzNTc4NTA3MjMyIiwiZnR5cGUiOiJwZGYiLCJydGljayI6IjE1MjY0NDY1MzU0NDEiLCJmc2l6ZSI6IjE4NjYzOSIsImZwYWdlcyI6IjEzIiwiZHJpdmVyTmFtZSI6ImNuLmJlc3RzaWduLmFwaS5vcGVuLnN0b3JhZ2UuZHJpdmVycy5GaWxlQ2xpZW50U3RvcmFnZSIsImZoYXNoIjoiMzNmOWY5MzQ2NWZmN2MzMTlkNGUxYzc5MjM3N2JmMmU5ZGE5Yzk1OSIsInVzZXJJZCI6IjE5OTE5MTczNDE2ODM3NDUzMjAifQ%3D%3D.3080dca0004fc5ba7a44a41f4d5347d0&signType=md5&sign=48d91e5d3086bcff3082622c7998bf54",path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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