/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractApi;
import com.jeesite.modules.contract.entity.TbContractApiChild;
import com.jeesite.modules.contract.entity.TbContractApiLog;
import com.jeesite.modules.contract.utils.ContarctUtils;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;
import com.jeesite.modules.utils.BestSignDemo;
import com.jeesite.modules.contract.dao.TbContractApiChildDao;
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
	@Value("${contractApi.expireTime}")
	private  String expireTime ;
	@Value("${contractApi.account}")
	private  String account ;
	@Autowired
	private TbCompService tbCompService;
	@Autowired
	private TbContractApiLogService tbContractApiLogService;
	@Autowired
	private TbContractApiDao tbContractApiDao;
	@Autowired
	private TbContractApiChildService tbContractApiChildService;
	@Autowired
	private TbContractApiChildDao tbContractApiChildDao;
	private BestSignDemo bestSignDemo = null;
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
		String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
		TbContractApi api=new TbContractApi();
		api.setCompId(compId);
		api=tbContractApiDao.getByEntity(api);
		TbComp comp=tbCompService.get(compId);
		bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
		if(api==null){
			String resultStr1;
			try {
				resultStr1 = bestSignDemo.getEnterpriseCredential(comp.getLegalPersonPhone()).toJSONString();
				System.err.println("查询企业信息："+resultStr1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return registered(compId,comp,api,addTime);
		}else{
			if(StringUtils.isBlank(api.getCert())){
				try {
					String resultStr = bestSignDemo.applyCert(comp.getLegalPersonPhone()).toJSONString();
					return settingCompInfo(resultStr,compId,comp,api,addTime);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			if(api.getHasSeal()==0){
				try {
					String resultStr = bestSignDemo.createASignatureSeal(comp.getLegalPersonPhone()).toJSONString();
					return createASignatureSeal(resultStr,compId,comp,api,addTime);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			return true;
		}
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

	/*
	 * 创建印章
	 */
	@Transactional(readOnly=false)
	private boolean createASignatureSeal(String resultStr,String compId,TbComp comp,TbContractApi api,String addTime){
		try {

			JSONObject jsonOb=new JSONObject(resultStr);
			resultStr=bestSignDemo.createASignatureSeal(comp.getLegalPersonPhone()).toJSONString();
			System.err.println("创建印章："+jsonOb);
			if(jsonOb.get("errno").toString().equals("0")){
				api=new TbContractApi();
				api.setCompId(compId);
				api=tbContractApiDao.getByEntity(api);
				api.setHasSeal(1);
				tbContractApiDao.update(api);
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

	/*
	 * 上传pdf
	 * 
	 */
	@Transactional(readOnly=false)
	public String uploadPDF(String path,String compId,String contractId){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			TbComp comp=tbCompService.get(compId);
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.uploadContract(comp.getLegalPersonPhone(),path).toJSONString();
			JSONObject jsonOb=new JSONObject(resultStr);
			System.err.println("上传合同模板："+resultStr);
			if(jsonOb.get("errno").toString().equals("0")){
				JSONObject jsonOb1=(JSONObject)jsonOb.get("data");
				String fid=jsonOb1.get("fid").toString();
				TbContractApi api=new TbContractApi();
				api.setCompId(compId);
				api=tbContractApiDao.getByEntity(api);
				TbContractApiChild apiChild=new TbContractApiChild();
				apiChild.setFileId(fid);
				apiChild.setApiId(api.getId());
				apiChild.setContractId(contractId);
				tbContractApiChildService.save(apiChild);
				apiChild=tbContractApiChildService.get(apiChild);
				path=path.substring(path.lastIndexOf("/")).substring(1);
				path=path.substring(0, path.lastIndexOf("."));
				return createContract(comp.getLegalPersonPhone(), compId, apiChild, path);
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
		return null;
	}
	/*
	 * 创建合同
	 */
	@Transactional(readOnly=false)
	public String createContract(String account,String compId,TbContractApiChild apiChild,String contractName){
		try{
			long extime=Long.parseLong(expireTime)+ContarctUtils.getNowTimeStamp();
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.createBargain(account,apiChild.getFileId(),extime+"",contractName,"").toJSONString();
			JSONObject jsonOb=new JSONObject(resultStr);
			System.err.println("创建合同："+resultStr);
			if(jsonOb.get("errno").toString().equals("0")){
				JSONObject jsonOb1=(JSONObject)jsonOb.get("data");
				String contractId=jsonOb1.get("contractId").toString();
				apiChild.setApiContractId(contractId);
				tbContractApiChildService.update(apiChild);
				return contractId;
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
		return null;
	}
	/*
	 * 添加签署者
	 * 
	 */
	@Transactional(readOnly=false)
	public boolean addSigners(String apiContractId,String compAccount,String compId,int type){
		try {
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			TbContractApiChild apiChild=new TbContractApiChild();
			apiChild.setApiContractId(apiContractId);
			apiChild=tbContractApiChildDao.getByEntity(apiChild);
			String resultStr="";
			JSONObject jsonOb=null;
			if(apiChild.getHasAddSigner()==0){
				switch (type) {
				case 0://双方签
					JSONArray jsonArray=new JSONArray();
					jsonArray.add(compAccount);
					jsonArray.add(account);
					resultStr=bestSignDemo.addSignersList(apiContractId,jsonArray).toJSONString();
					jsonOb=new JSONObject(resultStr);
					System.err.println("添加签署者："+resultStr);
					if(jsonOb.get("errno").toString().equals("0")){
						apiChild.setHasAddSigner(1);
						tbContractApiChildDao.update(apiChild);
						signApiContract(compId, apiContractId, account,jsonArray);
						return true;
					}else{
						TbContractApiLog log=new TbContractApiLog();
						log.setCompid(compId);
						log.setAddTime(addTime);
						log.setErrCode(jsonOb.get("errno").toString());
						log.setErrMsg(jsonOb.get("errmsg").toString());
						tbContractApiLogService.save(log);
					}
					break;
				case 1://对方签
					resultStr=bestSignDemo.addSigner(apiContractId,compAccount).toJSONString();
					jsonOb=new JSONObject(resultStr);
					System.err.println("添加签署者："+resultStr);
					if(jsonOb.get("errno").toString().equals("0")){

						return true;
					}else{
						TbContractApiLog log=new TbContractApiLog();
						log.setCompid(compId);
						log.setAddTime(addTime);
						log.setErrCode(jsonOb.get("errno").toString());
						log.setErrMsg(jsonOb.get("errmsg").toString());
						tbContractApiLogService.save(log);
					}
					break;
				case 2://我方签
					resultStr=bestSignDemo.addSigner(apiContractId,account).toJSONString();
					jsonOb=new JSONObject(resultStr);
					System.err.println("添加签署者："+resultStr);
					if(jsonOb.get("errno").toString().equals("0")){

						return true;
					}else{
						TbContractApiLog log=new TbContractApiLog();
						log.setCompid(compId);
						log.setAddTime(addTime);
						log.setErrCode(jsonOb.get("errno").toString());
						log.setErrMsg(jsonOb.get("errmsg").toString());
						tbContractApiLogService.save(log);
					}
					break;
				default:
					break;
				}
			}else{
				switch (type) {
				case 0:
					
					break;
				case 1:
					
					break;
				case 2:
					
					break;

				default:
					break;
				}
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * 签署合同
	 */
	@Transactional(readOnly=false)
	public boolean signApiContract(String compId,String contractId,String signer,JSONArray jsonArray){
		try{
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			JSONArray jsonArray1=new JSONArray();
			JSONObject json=new JSONObject();
			json.put("pageNum", 13);
			json.put("x", 1);
			json.put("y", 3);
			jsonArray1.add(json.toString());
			String resultStr=bestSignDemo.signContract(contractId,signer,jsonArray1).toJSONString();
			JSONObject jsonOb=new JSONObject(resultStr);
			System.err.println("签署合同："+resultStr);
			if(jsonOb.get("errno").toString().equals("0")){
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
	public static void main(String[] args) {
		JSONArray jsonArray=new JSONArray();
		JSONObject json=new JSONObject();
		json.put("pageNum", "pageNum1");
		json.put("x", "x1");
		json.put("y", "y1");
		jsonArray.add(json.toString());
		json.put("pageNum", "pageNum2");
		json.put("x", "x2");
		json.put("y", "y2");
		jsonArray.add(json.toString());
		System.err.println(jsonArray.toJSONString());
	}
}