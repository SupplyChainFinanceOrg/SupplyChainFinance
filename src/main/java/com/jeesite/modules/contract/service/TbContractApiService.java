/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractApi;
import com.jeesite.modules.contract.entity.TbContractApiChild;
import com.jeesite.modules.contract.entity.TbContractApiLog;
import com.jeesite.modules.contract.entity.TbContractSign;
import com.jeesite.modules.contract.entity.TbContractSignPoint;
import com.jeesite.modules.contract.utils.ContarctUtils;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;
import com.jeesite.modules.utils.BestSignDemo;
import com.jeesite.modules.contract.dao.TbContractApiChildDao;
import com.jeesite.modules.contract.dao.TbContractApiDao;
import com.jeesite.modules.contract.dao.TbContractSignDao;

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
	@Value("${contractApi.certType}")
	private  String certType ;
	@Value("${contractApi.account}")
	private  String account ;

	@Value("${contractApi.downloadPath}")
	private String downloadPath;
	@Autowired
	private TbCompService tbCompService;
	@Autowired
	private TbContractApiLogService tbContractApiLogService;
	@Autowired
	private TbContractApiDao tbContractApiDao;
	@Autowired
	private TbContractApiChildService tbContractApiChildService;
	@Autowired
	private TbContractSignPointService tbContractSignPointService;
	@Autowired
	private TbContractSignDao tbContractSignDao;
	@Autowired
	private TbContractApiChildDao tbContractApiChildDao;
	private BestSignDemo bestSignDemo = null;
	@Autowired
	private HttpServletResponse response;
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
		TbComp comp=tbCompService.get(compId);
		TbContractApi api=new TbContractApi();
		api.setCompId(compId);
		api.setSsqType(1);
		api=tbContractApiDao.getByEntity(api);
		if(api==null&&registered(comp,1)){//注册个人用户并且签名
			flag=createASignatureSeal(comp,1);
		}else{
			flag=true;
			if(StringUtils.isBlank(api.getCert())){
				flag=applyCert(comp,1);
			}
			if(api.getHasSeal()==0){
				flag=createASignatureSeal(comp,1);
			}
		}
		api=new TbContractApi();
		api.setCompId(compId);
		api.setSsqType(2);
		api=tbContractApiDao.getByEntity(api);
		if(api==null&&registered(comp,2)){//注册企业用户并且签名
			flag=createASignatureSeal(comp,2);
		}else{
			flag=true;
			if(StringUtils.isBlank(api.getCert())){
				flag=applyCert(comp,2);
			}
			if(api.getHasSeal()==0){
				flag=createASignatureSeal(comp,2);
			}
		}
		return flag;
	}
	/*
	 * 注册
	 */
	@Transactional(readOnly=false)
	private  boolean registered(TbComp comp,int type){
		try {
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String resultStr="";
			if(type==1){
				resultStr=bestSignDemo.userReg(comp.getLegalPersonPhone(), comp.getCompLegalPerson(), type+"",
						comp.getCompEmail(),comp.getLegalPersonPhone()).toJSONString();
			}else{
				resultStr=bestSignDemo.userReg(comp.getCompEmail(), comp.getCompLegalPerson(), type+"",
						comp.getCompEmail(),comp.getLegalPersonPhone()).toJSONString();
			}
			JSONObject jsonObl=JSONObject.parseObject(resultStr);
			System.err.println("注册："+jsonObl);
			if(jsonObl.get("errno").toString().equals("0")||jsonObl.get("errno").toString().equals("241208")){
				return settingCompInfo(comp,type);
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(comp.getId());
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
	private boolean settingCompInfo(TbComp comp,int type){
		try {
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String resultStr="";
			if(type==1){
				resultStr=bestSignDemo.personalCredential(comp.getLegalPersonPhone(),comp.getCardNo(),comp.getCompLegalPerson()).toJSONString();
			}else{
				if(StringUtil.isBlank(comp.getCompCode())){
					resultStr=bestSignDemo.enterpriseCredential(comp.getCompEmail(),comp.getRegCode(),comp.getOrgCode()
							,comp.getTaxCode(),comp.getCompName(),comp.getCompLegalPerson(),comp.getCardNo(),"0",
							comp.getLegalPersonPhone(),comp.getContactPhone()).toJSONString();
				}else{
					resultStr=bestSignDemo.enterpriseCredential(comp.getCompEmail(),comp.getCompCode(),comp.getCompCode()
							,comp.getCompCode(),comp.getCompName(),comp.getCompLegalPerson(),comp.getCardNo(),"0",
							comp.getLegalPersonPhone(),comp.getContactPhone()).toJSONString();
				}
			}

			JSONObject jsonObl=JSONObject.parseObject(resultStr);
			System.err.println("设置信息："+jsonObl);
			if(jsonObl.get("errno").toString().equals("0")||jsonObl.get("errno").toString().equals("241308")){
				return applyCert(comp,type);
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(comp.getId());
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
	private boolean applyCert(TbComp comp,int type){
		try {
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String account=type==1?comp.getLegalPersonPhone():comp.getCompEmail();
			String resultStr=bestSignDemo.applyCert(account,certType).toJSONString();
			JSONObject jsonObl=JSONObject.parseObject(resultStr);
			System.err.println("申请证书："+jsonObl);
			if(jsonObl.get("errno").toString().equals("0")||jsonObl.get("errno").toString().equals("241308")){
				return getCert(comp,type);
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(comp.getId());
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
	private boolean getCert(TbComp comp,int type){
		try {
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String account=type==1?comp.getLegalPersonPhone():comp.getCompEmail();
			String resultStr=bestSignDemo.getCert(account,certType).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("获取证书："+jsonOb);
			if(jsonOb.get("errno").toString().equals("0")){
				JSONObject jsonOb1=(JSONObject)jsonOb.get("data");
				TbContractApi api=new TbContractApi();
				String cert=jsonOb1.get("certId").toString();
				api.setCert(cert);
				api.setCertType(certType);
				api.setCompId(comp.getId());
				if(type==1){
					api.setSsqType(1);
					api.setSsqId(comp.getLegalPersonPhone());
				}
				if(type==2){
					api.setSsqType(2);
					api.setSsqId(comp.getCompEmail());
				}
				super.save(api);
				return true;

			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(comp.getId());
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
	private boolean createASignatureSeal(TbComp comp,int type){
		try {
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String resultStr=bestSignDemo.createASignatureSeal(type==1?comp.getLegalPersonPhone():comp.getCompEmail()).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("创建印章："+jsonOb);
			if(jsonOb.get("errno").toString().equals("0")){
				TbContractApi api=new TbContractApi();
				api.setCompId(comp.getId());
				api.setSsqType(type);
				api=tbContractApiDao.getByEntity(api);
				if(api!=null){
					api.setHasSeal(1);//设置已签名
					tbContractApiDao.update(api);
				}
				return true;
			}else{
				TbContractApiLog log=new TbContractApiLog();
				log.setCompid(comp.getId());
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
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.uploadContract(account,path).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("上传合同模板："+resultStr);
			if(jsonOb.get("errno").toString().equals("0")){
				JSONObject jsonOb1=(JSONObject)jsonOb.get("data");
				String fid=jsonOb1.get("fid").toString();
				String fileName=path.substring(path.lastIndexOf("/")).substring(1);
				fileName=path.substring(0, path.lastIndexOf("."));
				return createContract(contractId, compId, fid, fileName);
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
	public String createContract(String contarctId,String compId,String fid,String contractName){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			long extime=ContarctUtils.getNowTimeStamp(Long.parseLong(expireTime));
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.createBargain(account,fid,extime+"",contractName,"").toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("创建合同："+resultStr);
			if(jsonOb.get("errno").toString().equals("0")){
				JSONObject jsonOb1=(JSONObject)jsonOb.get("data");
				String apiContractId=jsonOb1.get("contractId").toString();
				TbContractApiChild apiChild=new TbContractApiChild();
				apiChild.setApiContractId(apiContractId);
				apiChild.setContractId(contarctId);
				apiChild.setFileId(fid);
				tbContractApiChildService.save(apiChild);
				return apiContractId;
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
	public boolean addSigners(String apiContractId,TbContractSign cs,String compId){
		boolean flag=false;
		try {
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			TbContractApiChild apiChild=new TbContractApiChild();
			apiChild.setApiContractId(apiContractId);
			apiChild=tbContractApiChildDao.getByEntity(apiChild);
			String resultStr="";
			JSONObject jsonOb=null;
			if(apiChild.getHasAddSigner()==0){//判断是否添加过签约者
				JSONArray jsonArray=getSignersJSONArray(cs,compId);
				if(jsonArray!=null&&jsonArray.size()>0){
					resultStr=bestSignDemo.addSignersList(apiContractId,jsonArray).toJSONString();
					jsonOb=JSONObject.parseObject(resultStr);
					System.err.println("添加签署者："+resultStr);
					if(jsonOb.get("errno").toString().equals("0")){
						flag=true;
						apiChild.setHasAddSigner(1);
						tbContractApiChildDao.update(apiChild);
						if(cs.getSignType()!=1&&cs.getSignType()!=2){
							flag= setSignerConfig(compId, apiContractId, account,cs);
						}
					}else{
						TbContractApiLog log=new TbContractApiLog();
						log.setCompid(compId);
						log.setAddTime(addTime);
						log.setErrCode(jsonOb.get("errno").toString());
						log.setErrMsg(jsonOb.get("errmsg").toString());
						tbContractApiLogService.save(log);
					}
				}
			}else{
				flag=true;
				if(cs.getSignType()!=1&&cs.getSignType()!=2){//当该合同不是对方单独签字和盖章的时候  走签约方法 否则return
					flag= setSignerConfig(compId, apiContractId, account,cs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 签署合同
	 */
	@Transactional(readOnly=false)
	public boolean signApiContract(String compId,String contractId,String signer,TbContractSign cs){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			JSONArray jsonArray=getPointsJSONArrayBySinger(cs,signer);
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.signContract(contractId,signer,jsonArray).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("签署合同："+resultStr);
			System.err.println(signer);
			if(jsonOb.get("errno").toString().equals("0")||jsonOb.get("errno").toString().equals("241212")){
				if(cs.getSignType()==3) {
					TbContractApiChild apiChild=new TbContractApiChild();
					apiChild.setApiContractId(contractId);
					apiChild=tbContractApiChildDao.getByEntity(apiChild);
					if(overContract(compId, apiChild.getApiContractId())&&apiChild!=null){
						TbContractSign tbCs=new TbContractSign();
						tbCs.setId(apiChild.getContractId());
						tbCs=tbContractSignDao.getByEntity(tbCs);
						if(getDownloadURLs(compId, apiChild.getApiContractId())) {
							apiChild=tbContractApiChildDao.getByEntity(apiChild);
							if(StringUtils.isNoneBlank(apiChild.getApiContractAttUrl())&&
									StringUtils.isNoneBlank(apiChild.getApiContractUrl())) {
								SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
								Date date=new Date();
								String path=downloadPath+"/"+ sdf.format(date)+"/"+tbCs.getLoanId()+"/";
								File file=new File(path);
								if(!file.exists()){//创建签约路径
									file.mkdirs();
								}
								String path1=path+"/"+tbCs.getShortName()+"_"+System.currentTimeMillis()+"con.pdf";
								ContarctUtils.downloadNet(response,apiChild.getApiContractUrl(),path1);
								tbCs.setDownPdfpath(path1);
								path1=path+"/"+tbCs.getShortName()+"_"+System.currentTimeMillis()+"att.pdf";
								ContarctUtils.downloadNet(response,apiChild.getApiContractAttUrl(),path1);
								tbCs.setDownAttpath(path1);
								tbContractSignDao.update(tbCs);
								return true;
							}
						}
					}
				}else{
					return true;
				}
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
	 * 设置合同参数
	 */
	@Transactional(readOnly=false)
	public boolean setSignerConfig(String compId,String contractId,String signer,TbContractSign cs){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			JSONArray jsonArray=getSignerPointsJSONArray(cs);
			String resultStr=bestSignDemo.setSignerConfig(contractId,signer,jsonArray,"","","","",certType).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println(signer);
			System.err.println("设置合同参数："+resultStr);
			if(jsonOb.get("errno").toString().equals("0")){
				return signApiContract(compId, contractId, signer,cs);
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
	 * 查看合同信息
	 */
	@Transactional(readOnly=false)
	public boolean getContractInfo(String compId,String contractId){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.getContractInfo(contractId).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("获取合同信息："+resultStr);
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

	/*
	 * 结束合同
	 */
	@Transactional(readOnly=false)
	public boolean overContract(String compId,String contractId){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.overContract(contractId).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("结束合同："+resultStr);
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
	/*
	 * 获取下载地址
	 */
	@Transactional(readOnly=false)
	public boolean getDownloadURLs(String compId,String contractId){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.getDownloadURLs(contractId).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("获取下载地址："+resultStr);
			if(jsonOb.get("errno").toString().equals("0")){
				JSONObject data=JSONObject.parseObject(jsonOb.get("data").toString());
				JSONArray attachmentList=JSONArray.parseArray(data.get("attachmentList").toString());
				JSONArray contractList=JSONArray.parseArray(data.get("contractList").toString());
				JSONObject contractObj=contractList.getJSONObject(0);
				JSONObject contractAtt=attachmentList.getJSONObject(0);
				String contractUrl=contractObj.get("url").toString();
				String contractName=contractObj.get("name").toString();
				String attUrl=contractAtt.get("url").toString();
				String attName=contractAtt.get("name").toString();
				TbContractApiChild csc=new TbContractApiChild();
				csc.setApiContractId(contractId);
				csc=tbContractApiChildDao.getByEntity(csc);
				csc.setApiContractAttName(attName);
				csc.setApiContractName(contractName);
				csc.setApiContractAttUrl(attUrl);
				csc.setApiContractUrl(contractUrl);
				tbContractApiChildDao.update(csc);
				TbContractSign cs=new TbContractSign();
				cs.setId(csc.getContractId());
				cs=tbContractSignDao.getByEntity(cs);
				cs.setIsSign(1);
				tbContractSignDao.update(cs);
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
	 * 查看合同签署状态
	 */
	@Transactional(readOnly=false)
	public boolean getSignerStatus(String compId,String contractId){
		try{
			bestSignDemo=BestSignDemo.getInstance(developerId, praviteKey, host);
			String addTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			String resultStr=bestSignDemo.getSignerStatus(contractId).toJSONString();
			JSONObject jsonOb=JSONObject.parseObject(resultStr);
			System.err.println("获取合同签署状态："+resultStr);
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
	private JSONArray getSignerPointsJSONArray(TbContractSign cs){
		JSONArray jsonArray=new JSONArray();
		JSONObject json=new JSONObject();
		TbContractSignPoint csp=new TbContractSignPoint();
		csp.setContractId(cs.getContractId());
		List<TbContractSignPoint> cspList=tbContractSignPointService.findList(csp);
		for(TbContractSignPoint cspItem:cspList) {
			json.put("pageNum", cspItem.getPageNum());
			json.put("x", cspItem.getPageXpoint());
			json.put("y", cspItem.getPageYpoint());
			jsonArray.add(json);
		}
		return jsonArray;
	}
	private JSONArray getPointsJSONArrayBySinger(TbContractSign cs,String signer){


//
		JSONArray jsonArray=new JSONArray();
//		JSONObject json=new JSONObject();
//		if(account.endsWith(signer)){
//			json.put("pageNum", cs.getSingPagea());
//			json.put("x", cs.getSingXa());
//			json.put("y", cs.getSingYa());
//			jsonArray.add(json);
//		}else{
//			json.put("pageNum", cs.getSingPageb());
//			json.put("x", cs.getSingXb());
//			json.put("y", cs.getSingYb());
//			jsonArray.add(json);
//		}

		return jsonArray;
	}
	private JSONArray getSignersJSONArray(TbContractSign cs,String compId){
		// 0双方章 1对方章 2对方签字 3我方章 4对方签字 我方章
		String compAccount="";
		String singleAccount="";
		TbContractApi api=new TbContractApi();
		api.setCompId(compId);
		List<TbContractApi> apiList=tbContractApiDao.findList(api);
		if(apiList!=null&&apiList.size()>0){
			for(TbContractApi apiItem:apiList){
				if(apiItem.getSsqType()==1){
					singleAccount=apiItem.getSsqId();
				}
				if(apiItem.getSsqType()==2){
					compAccount=apiItem.getSsqId();

				}
			}
		}
		JSONArray jsonArray=new JSONArray();
		switch (cs.getSignType()) {
		case 0:
			if(StringUtils.isNoneBlank(compAccount)){
				jsonArray.add(account);
				jsonArray.add(compAccount);
			}
			break;
		case 1:
			if(StringUtils.isNoneBlank(compAccount)){
				jsonArray.add(compAccount);
			}
			break;
		case 2:
			if(StringUtils.isNoneBlank(singleAccount)){
				jsonArray.add(singleAccount);
			}
			break;
		case 3:
			jsonArray.add(account);
			break;
		default:
			break;
		}
		System.err.println(jsonArray.toJSONString());
		return jsonArray;
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