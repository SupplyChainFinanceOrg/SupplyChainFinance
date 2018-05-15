/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractApi;
import com.jeesite.modules.contract.entity.TbContractApiLog;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;
import com.jeesite.modules.utils.BestSignDemo;
import com.jeesite.modules.contract.dao.TbContractApiDao;

/**
 * tb_contract_apiService
 * @author hanzl
 * @version 2018-05-14
 */
@Service
@Transactional(readOnly=true)
public class TbContractApiService extends CrudService<TbContractApiDao, TbContractApi> {
	@Value("${contractApi.developerId}")
	private  String developerId ;
	@Value("${contractApi.praviteKey}")
	private  String praviteKey;
	@Value("${contractApi.host}")
	private  String host ;
	@Autowired
	private TbCompService tbCompService;
	@Autowired
	private TbContractApiLogService tbContractApiLogService;
	private  BestSignDemo bestSignDemo = null;
	/**
	 * 获取单条数据
	 * @param tbContractApi
	 * @return
	 */
	@Override
	public TbContractApi get(TbContractApi tbContractApi) {
		return super.get(tbContractApi);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContractApi
	 * @return
	 */
	@Override
	public Page<TbContractApi> findPage(Page<TbContractApi> page, TbContractApi tbContractApi) {
		return super.findPage(page, tbContractApi);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tbContractApi
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContractApi tbContractApi) {
		super.save(tbContractApi);
	}

	/**
	 * 更新状态
	 * @param tbContractApi
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbContractApi tbContractApi) {
		super.updateStatus(tbContractApi);
	}

	/**
	 * 删除数据
	 * @param tbContractApi
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContractApi tbContractApi) {
		super.delete(tbContractApi);
	}
	/**
	 * 注册上上签
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean regSSQ(String compId){
		boolean flag=false;
		String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
		TbContractApi api=new TbContractApi();
		api.setCompId(compId);
		api=get(api);
		TbComp comp=tbCompService.get(compId);
		System.out.println("法人电话："+comp.getLegalPersonPhone()); 
		System.out.println("法人："+comp.getCompLegalPerson());
		System.out.println("邮箱："+comp.getCompEmail());
		System.out.println("身份证号："+comp.getCardNo());
		if(api==null){
			bestSignDemo = BestSignDemo.getInstance(developerId, praviteKey, host);
			String resultStr1;
			try {
				resultStr1 = bestSignDemo.getEnterpriseCredential(comp.getLegalPersonPhone()).toJSONString();
				System.err.println("查询企业信息："+resultStr1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return registered(compId,comp,api,addTime);
		}else{
			if(StringUtils.isBlank(api.getCert())){
				
				try {
					String resultStr1 = bestSignDemo.getEnterpriseCredential(comp.getLegalPersonPhone()).toJSONString();
					System.err.println("查询企业信息："+resultStr1);
					String resultStr = bestSignDemo.applyCert(comp.getLegalPersonPhone()).toJSONString();
					return settingCompInfo(resultStr,compId,comp,api,addTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			flag=true;
		}

		return flag;
	}
	/*
	 * 注册
	 */
	@Transactional(readOnly=false)
	private  boolean registered(String compId,TbComp comp,TbContractApi api,String addTime){
		try {
			String resultStr=bestSignDemo.userReg(comp.getLegalPersonPhone(), comp.getCompLegalPerson(), "2",
					comp.getCompEmail(),comp.getLegalPersonPhone()).toJSONString();
			JSONObject jsonObl=new JSONObject(resultStr);
			System.err.println("注册："+jsonObl);
			if(jsonObl.get("errno").toString().equals("0")){
				settingCompInfo(resultStr, compId, comp, api, addTime);
			}else if(jsonObl.get("errno").toString().equals("241208")){
				settingCompInfo(resultStr, compId, comp, api, addTime);
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(compId);
				log.setAddTime(addTime);
				log.setErrCode(jsonObl.get("errno").toString());
				log.setErrMsg(jsonObl.get("errmsg").toString());
				tbContractApiLogService.save(log);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * 设置企业信息
	 */
	@Transactional(readOnly=false)
	private boolean settingCompInfo(String resultStr,String compId,TbComp comp,TbContractApi api,String addTime){
		try {
			if(StringUtil.isBlank(comp.getCompCode())){
				resultStr=bestSignDemo.enterpriseCredential(comp.getLegalPersonPhone(),comp.getRegCode(),comp.getOrgCode()
						,comp.getTaxCode(),comp.getCompName(),comp.getCompLegalPerson(),comp.getCardNo(),"0",
						comp.getLegalPersonPhone(),comp.getContactPhone()).toJSONString();
			}else{
				resultStr=bestSignDemo.enterpriseCredential(comp.getLegalPersonPhone(),comp.getCompCode(),comp.getCompCode()
						,comp.getCompCode(),comp.getCompName(),comp.getCompLegalPerson(),comp.getCardNo(),"0",
						comp.getLegalPersonPhone(),comp.getContactPhone()).toJSONString();
			}
			JSONObject jsonObl=new JSONObject(resultStr);
			System.err.println("设置信息："+jsonObl);
			if(jsonObl.get("errno").toString().equals("0")){
				return applyCert(resultStr, compId, comp, api, addTime);
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(compId);
				log.setAddTime(addTime);
				log.setErrCode(jsonObl.get("errno").toString());
				log.setErrMsg(jsonObl.get("errmsg").toString());
				tbContractApiLogService.save(log);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	/*
	 * 申请数字证书
	 */
	@Transactional(readOnly=false)
	private boolean applyCert(String resultStr,String compId,TbComp comp,TbContractApi api,String addTime){


		try {
			resultStr=bestSignDemo.applyCert(comp.getLegalPersonPhone()).toJSONString();
			JSONObject jsonObl=new JSONObject(resultStr);
			System.err.println("申请证书："+jsonObl);
			if(jsonObl.get("errno").toString().equals("0")){
				return getCert(resultStr, compId, comp, api, addTime);
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(compId);
				log.setAddTime(addTime);
				log.setErrCode(jsonObl.get("errno").toString());
				log.setErrMsg(jsonObl.get("errmsg").toString());
				tbContractApiLogService.save(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * 获取数字证书
	 */
	@Transactional(readOnly=false)
	private boolean getCert(String resultStr,String compId,TbComp comp,TbContractApi api,String addTime){
		try {

			JSONObject jsonOb=new JSONObject(resultStr);
			resultStr=bestSignDemo.createASignatureSeal(comp.getLegalPersonPhone()).toJSONString();
			System.err.println("获取证书："+jsonOb);
			if(jsonOb.get("errno").toString().equals("0")){
				JSONObject jsonOb1=(JSONObject)jsonOb.get("data");
				api=new TbContractApi();
				String cert=jsonOb1.get("certId").toString();
				String certType=jsonOb1.get("certType").toString();
				api.setCert(cert);
				api.setCertType(certType);
				api.setCompId(compId);
				super.save(api);
				return true;
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(compId);
				log.setAddTime(addTime);
				log.setErrCode(jsonOb.get("errno").toString());
				log.setErrMsg(jsonOb.get("errmsg").toString());
				tbContractApiLogService.save(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}