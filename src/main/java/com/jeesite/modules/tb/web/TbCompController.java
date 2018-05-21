/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.tb.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.shiro.realm.BaseAuthorizingRealm;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.UserAgentUtils;
import com.jeesite.modules.button.entity.TbButton;
import com.jeesite.modules.button.service.TbButtonService;
import com.jeesite.modules.state.entity.TbProcess;
import com.jeesite.modules.state.entity.TbProcessLog;
import com.jeesite.modules.state.entity.TbState;
import com.jeesite.modules.state.service.TbProcessLogService;
import com.jeesite.modules.state.service.TbProcessService;
import com.jeesite.modules.state.service.TbStateService;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.TbUser;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.TbUserService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;

/**
 * tb_compController
 * @author zheng
 * @version 2018-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/tb/tbComp")
public class TbCompController extends BaseController {

	@Autowired
	private TbCompService tbCompService;
	@Autowired
	private TbStateService tbStateService;
	@Autowired
	private TbUserService tbUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private TbProcessService tbProcessService;
	@Autowired
	private TbProcessLogService tbProcessLogService;
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
	public String list(TbComp tbComp, Model model,HttpServletRequest request, HttpServletResponse response) {		
		User user =UserUtils.getUser();
		Role r=new Role();
		r.setUserCode(user.getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		List<TbState> bottonlist=new ArrayList<>();
		model.addAttribute("userrolecode", null);
		if(rolelist!=null&&rolelist.size()>0){
			bottonlist=tbStateService.findList(new TbState(TbState.COMPANYTYPE, "1", rolelist.get(0).getRoleCode()));
			model.addAttribute("userrolecode", rolelist.get(0).getRoleCode());
		}
		model.addAttribute("bottonlist", bottonlist);
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
		Page<TbComp> page = new Page<TbComp>(request, response);
		String userrolecode= request.getParameter("userrolecode");
		if(!StringUtils.isEmpty(userrolecode)){
			if(userrolecode.equals(TbComp.JKQYROLECODE)||userrolecode.equals(TbComp.JRQYROLECODE)||userrolecode.equals(TbComp.HXQYROLECODE)){
				tbComp.setUserId(UserUtils.getUser().getUserCode());
			}
		}
		page = tbCompService.findPage(page, tbComp); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tb:tbComp:view")
	@RequestMapping(value = "form")
	public String form(TbComp tbComp, Model model,HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("tbComp", tbComp);
		//查询审核情况
		TbProcessLog tbpl=new TbProcessLog();
		tbpl.setCompId(tbComp.getId());
		tbpl.getSqlMap().getWhere().and("loan_id", QueryType.IS_NULL, "");
		List<TbProcessLog> loglist=tbProcessLogService.findList(tbpl);	
		if(loglist!=null&&loglist.size()>0){
			tbpl=loglist.get(0);		
		}
		model.addAttribute("tbpl", tbpl);		
		if("1".equals(request.getParameter("looktype"))){
			//流程
			//获取角色，状态
			List<TbProcess> prolist=tbProcessService.buttunList(tbComp.getApplyState()+"",TbProcess.COMPANYTYPE);							
			model.addAttribute("prolist", prolist);	
			if(tbComp.getApplyState()==1||tbComp.getApplyState()==3||tbComp.getApplyState()==5){
				return "modules/tb/tbCompForm";
			}
			return "modules/tb/tbCompLiu";
		}else if("2".equals(request.getParameter("looktype"))){
			return "modules/tb/tbCompDetail";
		}
		
		return "modules/tb/tbCompForm";
	}

	/**
	 * 保存企业
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(TbComp tbComp,HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isEmpty(request.getParameter("nextstatus"))){
			tbCompService.save(tbComp);
			tbProcessLogService.saveLog(Integer.parseInt(tbComp.getApplyState()+""), TbProcessLog.APPLY_TYPE,tbComp.getId(),null, null, request.getParameter("operationRemark"), 1, 1, 1,tbComp.getApplyState()+"",tbComp.getCompName());
		}else{
			//查询老的数据
			TbComp oldtbComp=tbCompService.get(tbComp.getId());
			String oldstatus=oldtbComp.getApplyState()+"";
			if(0==oldtbComp.getApplyState()){
				oldtbComp.setApplyState(Long.parseLong(request.getParameter("nextstatus")));
				tbCompService.saveAndCreate(oldtbComp,true);
			}else{
				oldtbComp.setApplyState(Long.parseLong(request.getParameter("nextstatus")));
				tbCompService.save(oldtbComp);
			}
			
			tbProcessLogService.saveLog(Integer.parseInt(tbComp.getApplyState()+""), TbProcessLog.APPLY_TYPE,tbComp.getId(),null, null, request.getParameter("operationRemark"), 1, 1, 1,oldstatus+"-"+oldtbComp.getApplyState()+"",tbComp.getCompName());
			if("2".equals(request.getParameter("nextstatus"))){
				tbCompService.delete(oldtbComp);
			}
		}	

		return renderResult(Global.TRUE, "保存企业成功！");
	}
	
	/**
	 * 停用企业
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbComp tbComp) {
		tbComp.setStatus(TbComp.STATUS_DISABLE);
		tbCompService.updateStatus(tbComp);
		return renderResult(Global.TRUE, "停用企业成功");
	}
	
	/**
	 * 启用企业
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbComp tbComp) {
		tbComp.setStatus(TbComp.STATUS_NORMAL);
		tbCompService.updateStatus(tbComp);
		return renderResult(Global.TRUE, "启用企业成功");
	}
	
	/**
	 * 删除企业
	 */
	@RequiresPermissions("tb:tbComp:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbComp tbComp) {
		tbCompService.delete(tbComp);
		return renderResult(Global.TRUE, "删除企业成功！");
	}
}
