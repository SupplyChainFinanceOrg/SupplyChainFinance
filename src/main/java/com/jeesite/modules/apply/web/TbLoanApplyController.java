/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.apply.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.shiro.realm.BaseAuthorizingRealm;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.apply.entity.TbLoanApply;
import com.jeesite.modules.apply.service.TbLoanApplyService;
import com.jeesite.modules.attachment.entity.TbLoanAttachment;
import com.jeesite.modules.attachment.service.TbLoanAttachmentService;
import com.jeesite.modules.contract.dao.TbContractSignDao;
import com.jeesite.modules.contract.entity.TbContractSign;
import com.jeesite.modules.contract.entity.TbContractTemp;
import com.jeesite.modules.contract.service.TbContractService;
import com.jeesite.modules.control.entity.TbRiskControl;
import com.jeesite.modules.control.service.TbRiskControlService;
import com.jeesite.modules.distribution.entity.TbMoneyDistribution;
import com.jeesite.modules.distribution.service.TbMoneyDistributionService;
import com.jeesite.modules.lend.entity.TbLend;
import com.jeesite.modules.lend.service.TbLendService;
import com.jeesite.modules.product.entity.TbProduct;
import com.jeesite.modules.product.entity.TbProductBorrowType;
import com.jeesite.modules.product.service.TbProductBorrowTypeService;
import com.jeesite.modules.product.service.TbProductService;
import com.jeesite.modules.risk.entity.TbLoanRisk;
import com.jeesite.modules.risk.service.TbLoanRiskService;
import com.jeesite.modules.state.entity.TbProcess;
import com.jeesite.modules.state.entity.TbProcessLog;
import com.jeesite.modules.state.entity.TbState;
import com.jeesite.modules.state.service.TbProcessLogService;
import com.jeesite.modules.state.service.TbProcessService;
import com.jeesite.modules.state.service.TbStateService;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.TbUserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;
import com.jeesite.modules.utils.ZipfileUtile;

/**
 * tb_loan_applyController
 * @author z
 * @version 2018-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/tbLoanApply")
public class TbLoanApplyController extends BaseController {
	@Autowired
	private TbContractSignDao tbContractSignDao;
	@Autowired
	private TbLoanApplyService tbLoanApplyService;
	@Autowired
	private TbCompService tbCompService;
	@Autowired
	private TbProductService   tbProductService;
	@Autowired
	private TbProductBorrowTypeService tbProductBorrowTypeService;
	@Autowired
	private TbLoanAttachmentService tbLoanAttachmentService;
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

	@Autowired
	private EmpUserService empUserService;
	@Autowired
	private TbRiskControlService tbRiskControlService;
	@Autowired
	private TbLoanRiskService tbLoanRiskService;
	@Autowired
	private TbMoneyDistributionService tbMoneyDistributionService;
	@Autowired
	private TbLendService tbLendService;
	@Autowired
	private TbContractService tbContractService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbLoanApply get(String id, boolean isNewRecord) {
		return tbLoanApplyService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbLoanApply tbLoanApply, Model model,HttpServletRequest req) {
		User user =UserUtils.getUser();


		Role r=new Role();
		r.setUserCode(user.getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		List<TbState> bottonlist=new ArrayList<>();
		model.addAttribute("userrolecode", null);
		//当前用户的企业
		model.addAttribute("noapply", 0);
		
		if(rolelist!=null&&rolelist.size()>0){
			bottonlist=tbStateService.findList(new TbState(TbState.APPLYTYPE, tbLoanApply.getProductId()+"", rolelist.get(0).getRoleCode()));
			model.addAttribute("userrolecode", rolelist.get(0).getRoleCode());
			if(TbComp.JKQYROLECODE.equals(rolelist.get(0).getRoleCode())){
				TbComp mycorp=new TbComp();
				mycorp.setUserId(UserUtils.getUser().getUserCode());
				mycorp.setApplyState(1l);
				List<TbComp> mylist=tbCompService.findList(mycorp);
				if(mylist!=null&&mylist.size()>0){
					model.addAttribute("noapply", 1);
				}
			}
		}
		
		model.addAttribute("bottonlist", bottonlist);
		model.addAttribute("tbLoanApply", tbLoanApply);
		 List<TbProduct>	tbProductlist =new ArrayList<TbProduct>();
		 tbProductlist.add(new TbProduct());
		 tbProductlist.addAll(tbProductService.findList(new TbProduct()));
		model.addAttribute("tbProductlist", tbProductlist);
		//企业
		TbComp tbComp= new TbComp();
		tbComp.setCompType((int)TbComp.HXQYTYPE);
		tbComp.setStatus("1");
		List<TbComp > comlist=new ArrayList<TbComp>();
		comlist.add(new TbComp());
		comlist.addAll(tbCompService.findList(tbComp));
		model.addAttribute("comlist", comlist);
		model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		model.addAttribute("type", req.getParameter("type"));
		
		
		
		//判断密码是否为123456
		boolean ischange=BaseAuthorizingRealm.validatePassword("123456", user.getPassword());
		if(ischange){
			TbLoanApply tbl=new TbLoanApply();
			tbl.setUserId(user.getUserCode());
			long num=tbLoanApplyService.findCount(tbl);
			if(num==0&&TbComp.HXQYROLECODE.equals(rolelist.get(0).getRoleCode())){				
				model.addAttribute("userrolecode", null);
			}else{
				ischange=false;
			}
		}
		model.addAttribute("ischange", ischange);
		
		if("0".equals(req.getParameter("type"))){
			return "modules/apply/tbLoanApplyListAll";
		}
		//model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		return "modules/apply/tbLoanApplyList";
	}
	/**
	 * 申请列表
	 * */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = {"listapply", ""})
	public String listapply(TbLoanApply tbLoanApply, Model model,HttpServletRequest req) {
		User user =UserUtils.getUser();
		Role r=new Role();
		r.setUserCode(user.getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		List<TbState> bottonlist=new ArrayList<>();
		model.addAttribute("userrolecode", null);
		if(rolelist!=null&&rolelist.size()>0){
			bottonlist=tbStateService.findList(new TbState(TbState.APPLYTYPE, tbLoanApply.getProductId()+"", rolelist.get(0).getRoleCode()));
			model.addAttribute("userrolecode", rolelist.get(0).getRoleCode());
		}
		model.addAttribute("bottonlist", bottonlist);
		model.addAttribute("tbLoanApply", tbLoanApply);
		 List<TbProduct>	tbProductlist =new ArrayList<TbProduct>();
		 tbProductlist.add(new TbProduct());
		 tbProductlist.addAll(tbProductService.findList(new TbProduct()));
		model.addAttribute("tbProductlist", tbProductlist);
		//企业
		TbComp tbComp= new TbComp();
		//tbComp.setCompType((int)TbComp.HXQYTYPE);
		//tbComp.setStatus("1");
		List<TbComp > comlist=new ArrayList<TbComp>();
		comlist.add(new TbComp());
		comlist.addAll(tbCompService.findList(tbComp));
		model.addAttribute("comlist", comlist);
		model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		model.addAttribute("type", req.getParameter("type"));
		if("0".equals(req.getParameter("type"))){
			return "modules/apply/tbLoanApplyListAll";
		}
		//model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		return "modules/apply/tbLoanApplyListapply";
	}
	/**
	 * 放款列表
	 * */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = {"listload", ""})
	public String listload(TbLoanApply tbLoanApply, Model model,HttpServletRequest req) {
		User user =UserUtils.getUser();
		Role r=new Role();
		r.setUserCode(user.getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		List<TbState> bottonlist=new ArrayList<>();
		model.addAttribute("userrolecode", null);
		if(rolelist!=null&&rolelist.size()>0){
			bottonlist=tbStateService.findList(new TbState(TbState.APPLYTYPE, tbLoanApply.getProductId()+"", rolelist.get(0).getRoleCode()));
			model.addAttribute("userrolecode", rolelist.get(0).getRoleCode());
		}
		model.addAttribute("bottonlist", bottonlist);
		model.addAttribute("tbLoanApply", tbLoanApply);
		 List<TbProduct>	tbProductlist =new ArrayList<TbProduct>();
		 tbProductlist.add(new TbProduct());
		 tbProductlist.addAll(tbProductService.findList(new TbProduct()));
		model.addAttribute("tbProductlist", tbProductlist);
		//企业
		TbComp tbComp= new TbComp();
		//tbComp.setCompType((int)TbComp.HXQYTYPE);
		//tbComp.setStatus("1");
		List<TbComp > comlist=new ArrayList<TbComp>();
		comlist.add(new TbComp());
		comlist.addAll(tbCompService.findList(tbComp));
		model.addAttribute("comlist", comlist);
		model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		model.addAttribute("type", req.getParameter("type"));
		if("0".equals(req.getParameter("type"))){
			return "modules/apply/tbLoanApplyListAll";
		}
		//model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		return "modules/apply/tbLoanApplyListLoad";
	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbLoanApply> listData(TbLoanApply tbLoanApply, HttpServletRequest request, HttpServletResponse response) {
		String type=request.getParameter("type");
		if(!StringUtils.isEmpty(type)){
			if("1".equals(type)){
				String []status={"1","3","4","5","6","8"};
				tbLoanApply.getSqlMap().getWhere().and("apply_state", QueryType.IN,status);
			}else if("2".equals(type)){
				String []status={"12","13"};
				tbLoanApply.getSqlMap().getWhere().and("apply_state", QueryType.IN,status);
			}else if("3".equals(type)){
				String []status={"9","10","11"};
				tbLoanApply.getSqlMap().getWhere().and("apply_state", QueryType.IN,status);			
			}else if("4".equals(type)){
				String []status={"7","14","15","16"};
				tbLoanApply.getSqlMap().getWhere().and("apply_state", QueryType.IN,status);	
			}else if("0".equals(type)){
				//String []status={"1"};
				//tbLoanApply.getSqlMap().getWhere().and("apply_state", QueryType.IN,status);
			}
		}
		User user =UserUtils.getUser();
		Role r=new Role();
		r.setUserCode(user.getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		if(rolelist!=null&&rolelist.size()>0){
			String rolecode=rolelist.get(0).getRoleCode();
			TbComp tbcomp=new TbComp();
			tbcomp.setUserId(UserUtils.getUser().getUserCode());
			List<TbComp> list=tbCompService.findList(tbcomp);
			if(TbComp.JRQYROLECODE.equals(rolecode)){
				TbMoneyDistribution tbMoneyDistribution=new TbMoneyDistribution();
				if(list!=null&&list.size()>0){
					tbMoneyDistribution.setBankCompId(list.get(0).getId());
				}else{
					tbMoneyDistribution.setBankCompId("没有");
				}
				tbLoanApply.setTbMoneyDistribution(tbMoneyDistribution);
				
			}else if(TbComp.HXQYROLECODE.equals(rolecode)){
				if(list!=null&&list.size()>0){
					tbLoanApply.setCoreCompId(list.get(0).getId());
				}else{
					tbLoanApply.setCoreCompId("没有");
				}
				
			}else if(TbComp.JKQYROLECODE.equals(rolecode)){
				if(list!=null&&list.size()>0){
					tbLoanApply.setCompId(list.get(0).getId());
				}else{
					tbLoanApply.setCompId("没有");
				}				
			}
			
			if(!TbComp.JKQYROLECODE.equals(rolecode)){
				tbLoanApply.getSqlMap().getWhere().and("apply_state", QueryType.NE, 0);		
			}
		}
		
		Page<TbLoanApply> page = tbLoanApplyService.findPage(new Page<TbLoanApply>(request, response), tbLoanApply); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "form")
	public String form(TbLoanApply tbLoanApply, Model model,HttpServletRequest request) {
		if(!StringUtils.isEmpty(tbLoanApply.getId())){
			//审核情况
			TbProcessLog tbpl=new TbProcessLog();
			tbpl.setType(1);
			tbpl.setLoanId(tbLoanApply.getId());
			String [] status={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
			tbpl.getSqlMap().getWhere().and("logState", QueryType.IN,status);
			tbpl.getSqlMap().getWhere().and("operation_remark", QueryType.IS_NOT_NULL,"");
			List<TbProcessLog> tbpllist=tbProcessLogService.findList(tbpl);
			//状态的名字
			TbState tbs=new TbState();
			tbs.setType(1);
			tbs.getSqlMap().getWhere().and("nowstatus", QueryType.IS_NOT_NULL, null);
			List<TbState> tbslist=tbStateService.findList(tbs);
			model.addAttribute("tbpllist", tbpllist);
			model.addAttribute("tbslist", tbslist);
		}
		
		
		//金融机构列表
		TbComp corp=new TbComp();
		corp.setApplyState((long)1);
		corp.setCompType((int)TbComp.JRQYTYPE);
		List<TbComp> jrcorplist=new ArrayList<TbComp>();
		jrcorplist.add(corp);
		jrcorplist.addAll(tbCompService.findList(corp));
		model.addAttribute("jrcorplist", jrcorplist);
		
		//核心机构列表
		TbComp comp=new TbComp();
		comp.setApplyState((long)1);
		comp.setCompType((int)TbComp.HXQYTYPE);
		List<TbComp> hxcorplist=new ArrayList<TbComp>();
		hxcorplist.add(comp);
		hxcorplist.addAll(tbCompService.findList(comp));
		model.addAttribute("hxcorplist", hxcorplist);
		//查询产品表
		model.addAttribute("tbLoanApply", tbLoanApply);
		model.addAttribute("tbProductlist", tbProductService.findList(new TbProduct()));
		model.addAttribute("tbProductBorrowTypelist", tbProductBorrowTypeService.findList(new TbProductBorrowType()));
		//附件单
	
		Role r=new Role();
		r.setUserCode(UserUtils.getUser().getUserCode());
		List<Role> rolelist=roleService.findListByUserCode(r);
		String userrolecode="";
		if(rolelist!=null&&rolelist.size()>0){
			userrolecode=rolelist.get(0).getRoleCode();
		}
		//企业附件
		TbLoanAttachment tbatt=new TbLoanAttachment();
		tbatt.setAttachmentType(0);
		if(TbComp.HXQYROLECODE.equals(userrolecode)){
			tbatt.setIsCoreVisible(0);
		}
		if(TbComp.JRQYROLECODE.equals(userrolecode)){
			tbatt.setIsBankVisible(0);	
		}
		tbatt.setAttachmentType(0);
		tbatt.setIsdel(0);
		model.addAttribute("corptbLoanAttachmentlist", tbLoanAttachmentService.findList(tbatt));
		//个人附件
		tbatt=new TbLoanAttachment();
		tbatt.setAttachmentType(1);
		if(TbComp.HXQYROLECODE.equals(userrolecode)){
			tbatt.setIsCoreVisible(0);
		}
		if(TbComp.JRQYROLECODE.equals(userrolecode)){
			tbatt.setIsBankVisible(0);	
		}
		tbatt.setIsdel(0);
		model.addAttribute("peopletbLoanAttachmentlist", tbLoanAttachmentService.findList(tbatt));
	//核心企业
		TbComp querytbComp= new TbComp();
		querytbComp.setCompType((int)TbComp.HXQYTYPE);
		querytbComp.setStatus("1");
		List<TbComp > comlist=new ArrayList<TbComp>();
		comlist.add(new TbComp());
		comlist.addAll(tbCompService.findList(querytbComp));
		model.addAttribute("comlist", comlist);
		TbComp tbComp=new TbComp();
		if(tbLoanApply==null||StringUtils.isEmpty(tbLoanApply.getId())){
			//查询关联企业
			tbComp.setUserId(UserUtils.getUser().getUserCode());
			List<TbComp> tbComplist=tbCompService.findList(tbComp);
			if(tbComplist!=null&&tbComplist.size()>0){
				//新加申请
				tbComp=tbComplist.get(0);
				tbLoanApply.setCompName(tbComp.getCompName());
				tbLoanApply.setCompCode(tbComp.getCompCode());
				tbLoanApply.setRegisterDate(tbComp.getRegisterDate());
				tbLoanApply.setRegisterAddress(tbComp.getRegisterAddress());
				tbLoanApply.setCompLegalPerson(tbComp.getCompLegalPerson());
				tbLoanApply.setLegalPersonPhone(tbComp.getLegalPersonPhone());
				tbLoanApply.setCompContact(tbComp.getCompContact());
				tbLoanApply.setContactPhone(tbComp.getContactPhone());
				tbLoanApply.setNatureId(tbComp.getNatureId());				
				tbLoanApply.setIndustry(tbComp.getIndustry());
				tbLoanApply.setMainBusiness(tbComp.getMainBusiness());
				tbLoanApply.setCompProfile(tbComp.getCompProfile());
				tbLoanApply.setSpeciaIndustry(tbComp.getSpeciaIndustry());
				tbLoanApply.setSpecialTradeLicense(tbComp.getSpecialTradeLicense());
				tbLoanApply.setBusinessLicense(tbComp.getBusinessLicense());
				tbLoanApply.setCompPhone(tbComp.getCompPhone());
				tbLoanApply.setCompEmail(tbComp.getCompEmail());
				tbLoanApply.setEmployeesCount(tbComp.getEmployeesCount());
				tbLoanApply.setCompId(tbComp.getId());
				tbLoanApply.setRegCode(tbComp.getRegCode());
				tbLoanApply.setOrgCode(tbComp.getOrgCode());
				tbLoanApply.setTaxCode(tbComp.getTaxCode());
				tbLoanApply.setCardNo(tbComp.getCardNo());
		
		}
		}
		model.addAttribute("tbComp", tbComp);
		//流程
		//获取角色，状态
		List<TbProcess> prolist=tbProcessService.buttunList(tbLoanApply.getApplyState()+"",TbProcess.APPALYTYPE);							
		model.addAttribute("prolist", prolist);		
		if("1".equals(request.getParameter("looktype"))){
			if(0==tbLoanApply.getApplyState()||2==tbLoanApply.getApplyState()){
				return "modules/apply/tbLoanApplyForm";
			}
			if(3==tbLoanApply.getApplyState()){
				//风控评分
				for(int i=0;i<4;i++){
					TbRiskControl tbrc=new TbRiskControl();
					tbrc.setType(i);
					List<TbRiskControl> tbrclist=tbRiskControlService.findList(tbrc);
					model.addAttribute("tbrclist_"+i, tbrclist);
				}
				return "modules/apply/tbLoanApplyLiuaddScore";
			}
			if(4==tbLoanApply.getApplyState()||5==tbLoanApply.getApplyState()||6==tbLoanApply.getApplyState()||8==tbLoanApply.getApplyState()){
				//风控评分
				for(int i=0;i<4;i++){
					TbLoanRisk tblr=new TbLoanRisk();
					tblr.setLoanId(tbLoanApply.getId());
					TbRiskControl tbrc=new TbRiskControl();
					tbrc.setType(i);
					tblr.setTbRiskControl(tbrc);
					List<TbLoanRisk> tblrlist=tbLoanRiskService.findList(tblr);
					model.addAttribute("tblrlist_"+i, tblrlist);
				}
				return "modules/apply/tbLoanApplyLiuaddScoreed";
			}
			if(13==tbLoanApply.getApplyState()){
				TbProcessLog tbpl=new TbProcessLog();
				tbpl.setType(1);
				tbpl.setLoanId(tbLoanApply.getId());
				String [] status={"12"};
				tbpl.getSqlMap().getWhere().and("logState", QueryType.IN,status);
				tbpl.getSqlMap().getWhere().and("operation_remark", QueryType.IS_NOT_NULL,"");
				List<TbProcessLog> tbpllist=tbProcessLogService.findList(tbpl);
				model.addAttribute("tbpllist", tbpllist);
				model.addAttribute("titlename", null);
				if(tbpllist!=null&&tbpllist.size()>0){					
					model.addAttribute("titlename", "确权审核情况");
				}
			}
			return "modules/apply/tbLoanApplyLiu";
		}else if("3".equals(request.getParameter("looktype"))){
			//查看审核情况
			TbProcessLog tbpl=new TbProcessLog();
			tbpl.setLoanId(tbLoanApply.getId());
			model.addAttribute("tbProcessLog", tbpl);
			return "modules/apply/tbLoanApplying";
		}
		if(StringUtils.isEmpty(tbLoanApply.getId())){
			return "modules/apply/tbLoanApplyForm";
		}

		return "modules/apply/tbLoanApplyDetail";

	}
	/**
	 * 复审
	 * */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "againaduit")
	public String againaduit(TbLoanApply tbLoanApply, Model model,HttpServletRequest request) {	
		//审核情况
		TbProcessLog tbpl=new TbProcessLog();
		tbpl.setType(1);
		tbpl.setLoanId(tbLoanApply.getId());
		String [] status={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
		tbpl.getSqlMap().getWhere().and("logState", QueryType.IN,status);
		tbpl.getSqlMap().getWhere().and("operation_remark", QueryType.IS_NOT_NULL,"");
		List<TbProcessLog> tbpllist=tbProcessLogService.findList(tbpl);
		//状态的名字
		TbState tbs=new TbState();
		tbs.setType(1);
		tbs.getSqlMap().getWhere().and("nowstatus", QueryType.IS_NOT_NULL, null);
		List<TbState> tbslist=tbStateService.findList(tbs);
		model.addAttribute("tbpllist", tbpllist);
		model.addAttribute("tbslist", tbslist);
		//核心机构列表
		TbComp corp=new TbComp();
		corp.setApplyState((long)1);
		corp.setCompType((int)TbComp.HXQYTYPE);
		List<TbComp> hxcorplist=new ArrayList<TbComp>();
		hxcorplist.add(corp);
		hxcorplist.addAll(tbCompService.findList(corp));
		model.addAttribute("hxcorplist", hxcorplist);
		
		//金融机构列表
	    corp=new TbComp();
		corp.setApplyState((long)1);
		corp.setCompType((int)TbComp.JRQYTYPE);
		List<TbComp> jrcorplist=new ArrayList<TbComp>();
		jrcorplist.add(corp);
		jrcorplist.addAll(tbCompService.findList(corp));
		model.addAttribute("jrcorplist", jrcorplist);
		
		model.addAttribute("tbLoanApply", tbLoanApply);	
		//风控评分
		for(int i=0;i<4;i++){
			TbLoanRisk tblr=new TbLoanRisk();
			tblr.setLoanId(tbLoanApply.getId());
			TbRiskControl tbrc=new TbRiskControl();
			tbrc.setType(i);
			tblr.setTbRiskControl(tbrc);
			List<TbLoanRisk> tblrlist=tbLoanRiskService.findList(tblr);
			model.addAttribute("tblrlist_"+i, tblrlist);
		}
		//获取角色，状态
		List<TbProcess> prolist=tbProcessService.buttunList(tbLoanApply.getApplyState()+"",TbProcess.APPALYTYPE);							
		model.addAttribute("prolist", prolist);		
		return "modules/apply/tbLoanApplyLiuagainaduit";

	}
	/**
	 * 确权
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "rightSure")
	public String rightSure(TbLoanApply tbLoanApply, Model model,HttpServletRequest request) {	
		TbProcessLog tbpl=new TbProcessLog();
		tbpl.setType(1);
		tbpl.setLoanId(tbLoanApply.getId());
		String [] status={"12","13"};
		tbpl.getSqlMap().getWhere().and("logState", QueryType.IN,status);
		tbpl.getSqlMap().getWhere().and("operation_remark", QueryType.IS_NOT_NULL,"");
		List<TbProcessLog> tbpllist=tbProcessLogService.findList(tbpl);
		//核心机构列表
		TbComp corp=new TbComp();
		corp.setApplyState((long)1);
		corp.setCompType((int)TbComp.HXQYTYPE);
		List<TbComp> hxcorplist=new ArrayList<TbComp>();
		hxcorplist.add(corp);
		hxcorplist.addAll(tbCompService.findList(corp));
		model.addAttribute("hxcorplist", hxcorplist);
		model.addAttribute("tbLoanApply", tbLoanApply);	
		//获取角色，状态
		List<TbProcess> prolist=tbProcessService.buttunList(tbLoanApply.getApplyState()+"",TbProcess.APPALYTYPE);							
		model.addAttribute("prolist", prolist);		
		return "modules/apply/tbLoanApplyLiurightSure";

	}
	/**
	 * 申请放款
	 */
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "amount")
	public String amount(TbLoanApply tbLoanApply, Model model,HttpServletRequest request) {	
		//核心机构列表
		TbComp corp=new TbComp();
		corp.setApplyState((long)1);
		corp.setCompType((int)TbComp.HXQYTYPE);
		List<TbComp> hxcorplist=new ArrayList<TbComp>();
		hxcorplist.add(corp);
		hxcorplist.addAll(tbCompService.findList(corp));
		model.addAttribute("hxcorplist", hxcorplist);
		model.addAttribute("tbLoanApply", tbLoanApply);	
		//获取角色，状态
		List<TbProcess> prolist=tbProcessService.buttunList(tbLoanApply.getApplyState()+"",TbProcess.APPALYTYPE);							
		model.addAttribute("prolist", prolist);		
		return "modules/apply/tbLoanApplyLiuamount";

	}
	/*
	 * 放款
	 * **/
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "load")
	public String load(TbLoanApply tbLoanApply, Model model,HttpServletRequest request) {	
		//核心机构列表
		TbComp corp=new TbComp();
		corp.setApplyState((long)1);
		corp.setCompType((int)TbComp.HXQYTYPE);
		List<TbComp> hxcorplist=new ArrayList<TbComp>();
		hxcorplist.add(corp);
		hxcorplist.addAll(tbCompService.findList(corp));
		model.addAttribute("hxcorplist", hxcorplist);
		model.addAttribute("tbLoanApply", tbLoanApply);	
		//获取角色，状态
		List<TbProcess> prolist=tbProcessService.buttunList(tbLoanApply.getApplyState()+"",TbProcess.APPALYTYPE);							
		model.addAttribute("prolist", prolist);		
		return "modules/apply/tbLoanApplyLiuload";

	}
	
	/*
	 * 收款
	 * **/
	@RequiresPermissions("apply:tbLoanApply:view")
	@RequestMapping(value = "loaded")
	public String loaded(TbLoanApply tbLoanApply, Model model,HttpServletRequest request) {	
		//核心机构列表
		TbComp corp=new TbComp();
		corp.setApplyState((long)1);
		corp.setCompType((int)TbComp.HXQYTYPE);
		List<TbComp> hxcorplist=new ArrayList<TbComp>();
		hxcorplist.add(corp);
		hxcorplist.addAll(tbCompService.findList(corp));
		model.addAttribute("hxcorplist", hxcorplist);
		model.addAttribute("tbLoanApply", tbLoanApply);	
		//获取角色，状态
		List<TbProcess> prolist=tbProcessService.buttunList(tbLoanApply.getApplyState()+"",TbProcess.APPALYTYPE);							
		model.addAttribute("prolist", prolist);		
		return "modules/apply/tbLoanApplyLiuloaded";

	}
	/**
	 * 保存借款申请
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(TbLoanApply tbLoanApply,HttpServletRequest request) {
		if(StringUtils.isEmpty(tbLoanApply.getId())){
			tbLoanApply.setApplyTime(new Date());
			tbLoanApply.setUserId(UserUtils.getUser().getUserCode());
			if(tbLoanApply.getApplyState()==null){
				tbLoanApply.setApplyState(0l);
			}
		}
		if(StringUtils.isEmpty(request.getParameter("nextstatus"))||StringUtils.isEmpty(tbLoanApply.getId())){
			if(tbLoanApply.getProductId()==null){
				tbLoanApply.setProductId(0l);
			}
			tbLoanApplyService.save(tbLoanApply);
			//日志
			tbProcessLogService.saveLog(Integer.parseInt(tbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, tbLoanApply.getProductId()+"", tbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,tbLoanApply.getApplyState()+"",tbLoanApply.getCompName());
			
		}else{
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			if(nextstatus==4){
				//保存风控分 //保存总分
				int total=0;
				TbRiskControl tbrc=new TbRiskControl();
				List<TbRiskControl> tbrclist=tbRiskControlService.findList(tbrc);
				for(int i=0;i<tbrclist.size();i++){
					long raskid=Integer.parseInt(tbrclist.get(i).getId());
					String complete=request.getParameter("complete_"+raskid);
					String remark=request.getParameter("remark_"+raskid);
					int score=Integer.parseInt(request.getParameter("score_"+raskid));
					total=total+score;
					TbLoanRisk tbLoanRisk=new TbLoanRisk();
					tbLoanRisk.setId(tbLoanApply.getId()+"_"+raskid);
					tbLoanRisk.setComplete(complete);
					tbLoanRisk.setRiskId(raskid);
					tbLoanRisk.setTotalScore(Long.parseLong(tbrclist.get(i).getInitScore()+""));
					tbLoanRisk.setLoanId(tbLoanApply.getId());
					tbLoanRisk.setRemark(remark);
					tbLoanRisk.setScore((long)score);
					tbLoanRisk.setTbRiskControl(tbrclist.get(i));
					TbLoanRisk oldtbLoanRisk=tbLoanRiskService.get(tbLoanRisk);
					if(oldtbLoanRisk==null){
						tbLoanRiskService.insert(tbLoanRisk);
					}else{						
						tbLoanRiskService.save(tbLoanRisk);
					}
				}
				oldtbLoanApply.setRiskScore(total+"");
			}	
			if(nextstatus==5){
				//提交审核，选择金融机构
				if(tbLoanApply.getTbMoneyDistribution()!=null){
					if(oldtbLoanApply.getTbMoneyDistribution()==null||StringUtils.isEmpty(oldtbLoanApply.getTbMoneyDistribution().getId())){
						TbMoneyDistribution tbmd=new TbMoneyDistribution();
						tbmd.setId(tbLoanApply.getId());
						tbmd.setBankCompId(tbLoanApply.getTbMoneyDistribution().getBankCompId());
						tbmd.setLoanId(tbLoanApply.getId());
						try {
							tbMoneyDistributionService.insert(tbmd);
						} catch (Exception e) {
							//可能已经存在
							tbMoneyDistributionService.save(tbmd);
						}
					}else{
						tbLoanApply.getTbMoneyDistribution().setId(tbLoanApply.getId());;
						tbLoanApply.getTbMoneyDistribution().setLoanId(tbLoanApply.getId());
						tbMoneyDistributionService.save(tbLoanApply.getTbMoneyDistribution());
					}
				}
			}
			if(nextstatus==0||nextstatus==1){
				//暂存或者提交
				tbLoanApply.setApplyState(nextstatus);
				tbLoanApplyService.save(tbLoanApply);			
			}else{		
				tbLoanApplyService.save(oldtbLoanApply);
			}
			tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());			
		}
		
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 确权保存
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveApply")
	@ResponseBody
	public String saveApply(TbLoanApply tbLoanApply,HttpServletRequest request) {
		
		if(StringUtils.isEmpty(tbLoanApply.getCompId())&&StringUtils.isEmpty(tbLoanApply.getId())){
			TbComp tbComp=new TbComp();
			if(tbLoanApply==null||StringUtils.isEmpty(tbLoanApply.getId())){
				//查询关联企业
				tbComp.setUserId(UserUtils.getUser().getUserCode());
				List<TbComp> tbComplist=tbCompService.findList(tbComp);
				if(tbComplist!=null&&tbComplist.size()>0){
					//新加申请
					tbComp=tbComplist.get(0);
					tbLoanApply.setCompName(tbComp.getCompName());
					tbLoanApply.setCompCode(tbComp.getCompCode());
					tbLoanApply.setRegisterDate(tbComp.getRegisterDate());
					tbLoanApply.setRegisterAddress(tbComp.getRegisterAddress());
					tbLoanApply.setCompLegalPerson(tbComp.getCompLegalPerson());
					tbLoanApply.setLegalPersonPhone(tbComp.getLegalPersonPhone());
					tbLoanApply.setCompContact(tbComp.getCompContact());
					tbLoanApply.setContactPhone(tbComp.getContactPhone());
					tbLoanApply.setNatureId(tbComp.getNatureId());				
					tbLoanApply.setIndustry(tbComp.getIndustry());
					tbLoanApply.setMainBusiness(tbComp.getMainBusiness());
					tbLoanApply.setCompProfile(tbComp.getCompProfile());
					tbLoanApply.setSpeciaIndustry(tbComp.getSpeciaIndustry());
					tbLoanApply.setSpecialTradeLicense(tbComp.getSpecialTradeLicense());
					tbLoanApply.setBusinessLicense(tbComp.getBusinessLicense());
					tbLoanApply.setCompPhone(tbComp.getCompPhone());
					tbLoanApply.setCompEmail(tbComp.getCompEmail());
					tbLoanApply.setEmployeesCount(tbComp.getEmployeesCount());
					tbLoanApply.setCompId(tbComp.getId());
					tbLoanApply.setRegCode(tbComp.getRegCode());
					tbLoanApply.setOrgCode(tbComp.getOrgCode());
					tbLoanApply.setTaxCode(tbComp.getTaxCode());
					tbLoanApply.setCardNo(tbComp.getCardNo());
			
			}
			}
		}
		
		
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			oldtbLoanApply.setCoreCompId(tbLoanApply.getCoreCompId());
			tbLoanApplyService.save(oldtbLoanApply);
			tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());			
		
		
		return renderResult(Global.TRUE, "操作成功！");
	}
	
	/**
	 * 复审
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveLetters")
	@ResponseBody
	public String saveLetters(TbLoanApply tbLoanApply,HttpServletRequest request) {
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			  //授信企业，授信额度
			oldtbLoanApply.setCoreCompId(tbLoanApply.getCoreCompId());
			oldtbLoanApply.setLineCredit(tbLoanApply.getLineCredit());
			tbLoanApplyService.save(oldtbLoanApply);
			tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());			
		
		
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 确权保存
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveRightSure")
	@ResponseBody
	public String saveRightSure(TbLoanApply tbLoanApply,HttpServletRequest request) {
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			///oldtbLoanApply.setCoreCompId(tbLoanApply.getCoreCompId());
			tbLoanApplyService.save(oldtbLoanApply);
			tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());			
		
		
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 额度保存
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveamount")
	@ResponseBody
	public String saveamount(TbLoanApply tbLoanApply,HttpServletRequest request) {
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			oldtbLoanApply.getTbMoneyDistribution().setApplyAmount(tbLoanApply.getTbMoneyDistribution().getApplyAmount());
			tbMoneyDistributionService.save(oldtbLoanApply.getTbMoneyDistribution());
			tbLoanApplyService.save(oldtbLoanApply);
			tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());			
		
		
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 放款额度保持
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveLoad")
	@ResponseBody
	public String saveLoad(TbLoanApply tbLoanApply,HttpServletRequest request) {
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			TbLend tblend=new TbLend();
			tblend.setId(oldtbLoanApply.getId());
			tblend.setLendAmount(tbLoanApply.getTbLend().getLendAmount());
			tblend.setUserId(UserUtils.getUser().getId());
			tblend.setLoanId(oldtbLoanApply.getId());
			tblend.setLendTime(new Date());
			try {
				tbLendService.insert(tblend);
			} catch (Exception e) {
				tbLendService.save(tblend);
			}		
			//oldtbLoanApply.getTbMoneyDistribution().setApplyAmount(tbLoanApply.getTbMoneyDistribution().getApplyAmount());
			//tbMoneyDistributionService.save(oldtbLoanApply.getTbMoneyDistribution());
			tbLoanApplyService.save(oldtbLoanApply);
			tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());			
		
		
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 平台签
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveOneSign")
	@ResponseBody
	public String saveOneSign(TbLoanApply tbLoanApply,HttpServletRequest request) {
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			//10为合同平台签成功
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			///oldtbLoanApply.setCoreCompId(tbLoanApply.getCoreCompId());
			boolean sign  =tbContractService.signCountarct(tbLoanApply.getId());//注册 获取 上上签 并且生成pdf合同模板 进行签约
			
			if(sign){
				tbLoanApplyService.save(oldtbLoanApply);
				tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());						
			}else{
				return renderResult(Global.FALSE, "异常！");
			}
			
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 借款企业签
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveTwoSign")
	@ResponseBody
	public String saveTwoSign(TbLoanApply tbLoanApply,HttpServletRequest request) {
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			boolean sign=false;
			if(nextstatus==11){
				sign=true;
			}else{				
				sign=tbContractService.signCountarctByLoanComp(tbLoanApply.getId());//注册 获取 上上签 并且生成pdf合同模板 进行签约
			}			
			if(sign){
				tbLoanApplyService.save(oldtbLoanApply);
				tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());						
			}else{
				return renderResult(Global.FALSE, "异常！");
			}		
		
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 核心企业签
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@PostMapping(value = "saveThreeSign")
	@ResponseBody
	public String saveThreeSign(TbLoanApply tbLoanApply,HttpServletRequest request) {
			TbLoanApply	oldtbLoanApply=tbLoanApplyService.get(tbLoanApply.getId());
			//日志
			long nextstatus=Long.parseLong(request.getParameter("nextstatus"));
			String oldstatus=oldtbLoanApply.getApplyState()+"";
			oldtbLoanApply.setApplyState(nextstatus);
			String statusstring= oldstatus+"-"+oldtbLoanApply.getApplyState();
			boolean sign=tbContractService.signCountarctByCoreComp(tbLoanApply.getId());//注册 获取 上上签 并且生成pdf合同模板 进行签约
			if(sign){
				tbLoanApplyService.save(oldtbLoanApply);
				tbProcessLogService.saveLog(Integer.parseInt(oldtbLoanApply.getApplyState()+""), TbProcessLog.APPLY_TYPE,null, oldtbLoanApply.getProductId()+"", oldtbLoanApply.getId(), request.getParameter("operationRemark"), 1, 1, 1,statusstring,tbLoanApply.getCompName());						
			}else{
				return renderResult(Global.FALSE, "异常！");
			}
		return renderResult(Global.TRUE, "操作成功！");
	}
	/**
	 * 删除借款申请
	 */
	@RequiresPermissions("apply:tbLoanApply:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbLoanApply tbLoanApply) {
		tbLoanApplyService.delete(tbLoanApply);
		return renderResult(Global.TRUE, "删除借款申请成功！");
	}
	@RequestMapping(value = "download")
	public void download(TbLoanApply tbLoanApply, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {	    	
			if(StringUtils.isNotBlank(tbLoanApply.getId())){
				String date=new SimpleDateFormat("YYYYMMdd").format(new Date());
				//获取合同
				User user =UserUtils.getUser();
				Role r=new Role();
				r.setUserCode(user.getUserCode());
				List<Role> rolelist=roleService.findListByUserCode(r);
				TbContractSign contractSign=new TbContractSign();
				contractSign.setLoanId(tbLoanApply.getId());				

				if(rolelist!=null&&rolelist.size()>0&&rolelist.get(0).getRoleCode().equals(TbComp.HXQYROLECODE)){
					contractSign.setSignType(3);
				}
				List<TbContractSign> contractSignList=tbContractSignDao.findList(contractSign);

				String [] filespath=new String[contractSignList.size()*2];
				for(int i=0;i<contractSignList.size();i++){
					if(StringUtils.isNotBlank(contractSignList.get(i).getDownPdfpath())){
						filespath[i]=contractSignList.get(i).getDownPdfpath();
					}
					if(StringUtils.isNotBlank(contractSignList.get(i).getDownAttpath())){
						filespath[i+contractSignList.size()]=contractSignList.get(i).getDownAttpath();
					}
				}			
				response=ZipfileUtile.downzips(filespath,tbLoanApply.getCompName()+date+"合同", request, response);
			}
												    
	}
}