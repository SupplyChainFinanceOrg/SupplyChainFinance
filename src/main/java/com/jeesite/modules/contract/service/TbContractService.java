/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;


import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractTemp;
import com.jeesite.modules.contract.entity.TbContractApi;
import com.jeesite.modules.contract.entity.TbContractApiChild;
import com.jeesite.modules.contract.entity.TbContractField;
import com.jeesite.modules.contract.entity.TbContractSign;
import com.jeesite.modules.contract.entity.TbContractSginField;
import com.jeesite.modules.contract.utils.ContarctUtils;
import com.jeesite.modules.counter.dao.TbCounterDao;
import com.jeesite.modules.counter.entity.TbCounter;
import com.jeesite.modules.product.entity.TbProductBorrowType;
import com.jeesite.modules.product.service.TbProductBorrowTypeService;
import com.jeesite.modules.tb.service.TbCompService;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.apply.entity.TbLoanApply;
import com.jeesite.modules.apply.service.TbLoanApplyService;
import com.jeesite.modules.contract.dao.TbContractApiChildDao;
import com.jeesite.modules.contract.dao.TbContractApiDao;
import com.jeesite.modules.contract.dao.TbContractTempDao;
import com.jeesite.modules.contract.dao.TbContractSignDao;
import com.jeesite.modules.contract.dao.TbContractSginFieldDao;
import com.jeesite.modules.utils.Html2PdfUtils;
import com.jeesite.modules.utils.NumberToCN;
import com.jeesite.modules.utils.Utils;

/**
 * tb_contractService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbContractService extends CrudService<TbContractTempDao, TbContractTemp> {


	@Value("${contractApi.code}")
	private String contarctCode;
	@Value("${contractApi.fontPath}")
	private  String fontPath;
	@Value("${contractApi.savePath}")
	private String savePath;

	@Value("${contractApi.downloadPath}")
	String downloadPath;
	//	@Autowired
	//	private TbContractFieldService tbContractFieldService;
	@Autowired
	private TbContractSignDao tbContractSignDao;
	@Autowired
	private TbContractFieldService tbContractFieldService;
	@Autowired
	private TbContractSginFieldService tbSginContractService;
	@Autowired
	private TbContractSginFieldDao tbSginContractDao;
	@Autowired
	private TbCounterDao tbCounterDao;
	@Autowired
	private TbLoanApplyService tbLoanApplyService;
	@Autowired
	private TbProductBorrowTypeService tbProductBorrowTypeService;
	@Autowired
	private TbCompService tbCompService;
	@Autowired
	private TbContractApiService tbContractApiService;
	@Autowired
	private TbContractApiDao tbContractApiDao;
	@Autowired
	private TbContractApiChildDao tbContractApiChildDao;
	@Autowired
	private HttpServletResponse response;



	/**
	 * 获取单条数据
	 * @param tbContract
	 * @return
	 */
	@Override
	public TbContractTemp get(TbContractTemp tbContract) {
		return super.get(tbContract);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContract
	 * @return
	 */
	@Override
	public Page<TbContractTemp> findPage(Page<TbContractTemp> page, TbContractTemp tbContract) {
		return super.findPage(page, tbContract);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContractTemp tbContract) {
		super.save(tbContract);
		// 保存上传图片
		//		FileUploadUtils.saveFileUpload(tbContract.getId(), "tbContract_image");
		//		// 保存上传附件
		//		FileUploadUtils.saveFileUpload(tbContract.getId(), "tbContract_file");
	}

	/**
	 * 更新状态
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbContractTemp tbContract) {
		super.updateStatus(tbContract);
	}

	/**
	 * 删除数据
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContractTemp tbContract) {
		super.delete(tbContract);
	}
	/**
	 * 生成合同模板
	 * @param state 状态
	 * @param loanId 借款id
	 * @param signContractList 字段列表
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<TbContractSign> contractSetting(String loanId,List<TbContractTemp> contractList){
		try {
			List<TbContractSign> contractSignList=new ArrayList<TbContractSign>();
			for(TbContractTemp c:contractList){//循环合同
				String tmp="";
				TbContractField contractField=new TbContractField();
				contractField.setContractId(c.getId());
				tmp=c.getTempContent();
				SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
				Date date=new Date();
				//真实的pdf保存路径为合同目录 pdf文件夹时间文件夹借款id文件夹
				String realSavePath=savePath+"/"+ sdf.format(date)+"/"+loanId+"/";
				String fileName=realSavePath+c.getShortName()+"_"+System.currentTimeMillis()+".pdf";
				TbContractSign contractSign=new TbContractSign();//查询实体
				contractSign.setLoanId(loanId);
				contractSign.setContractTempId(Long.parseLong(c.getId()));
				TbContractSign contractSign1=tbContractSignDao.getByEntity(contractSign);//判断数据库是否存在
				contractSign.setContractContent(tmp);
				contractSign.setUploadPdfpath(fileName);
				contractSign.setShortName(c.getShortName());
				contractSign.setSignType(c.getSignType());
				if(contractSign1==null){
					tbContractSignDao.saveSign(contractSign);//插入签约表
				}else{
					System.err.println(contractSign1.getUploadPdfpath());
					Utils.deleteFile(contractSign1.getUploadPdfpath());
					contractSign.setOperationTime(new Date());
					tbContractSignDao.updateByEntity(contractSign,contractSign1);//修改签约表
				}
				contractSignList.add(contractSign);
			}
			initField(contractSignList,loanId);
			return contractSignList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 基础数据转字段数据
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean initField(List<TbContractSign> contractList,String loanId) {
		try {
			TbContractSginField sc=null;
			TbCounter counter=new TbCounter();
			counter.setCode("contact_code");
			counter=tbCounterDao.getByEntity(counter);
			System.err.println(counter.getValue());
			int num=Integer.parseInt(counter.getValue())+1;
			TbCounter counterNew=new TbCounter();
			counterNew.setValue(num+"");
			tbCounterDao.updateByEntity(counterNew,counter);
			String value=ContarctUtils.createContarctsCode(contarctCode,num+"");
			for(TbContractSign c:contractList){
				TbContractField cf=new TbContractField();
				cf.setContractId(c.getContractTempId()+"");
				List<TbContractField> cfList=tbContractFieldService.findList(cf);
				for(TbContractField cfield:cfList){
					sc=new TbContractSginField();
					sc.setLoanId(loanId);
					sc.setContractId(Long.parseLong(c.getId()));
					sc.setContractFieldId(Long.parseLong(cfield.getId()));
					sc.setFieldName(cfield.getFieldName());
					sc.setFieldCode(cfield.getFieldCode());
					sc.setRemark(cfield.getRemark());
					sc.setIsEdite(cfield.getIsEdite());
					if(cfield.getFieldDefaultValue()!=null&&!"".equals(cfield.getFieldDefaultValue())){
						sc.setContractValue(cfield.getFieldDefaultValue());
						String tmp=c.getContractContent();
						tmp=tmp.replaceAll(sc.getFieldCode(), sc.getContractValue());
						c.setContractContent(tmp);
						tbContractSignDao.update(c);//修改签约表

					}
					Map<String,Object> map=initContarctMap(value,loanId);
					if(map.get(cfield.getFieldCode())!=null){
						sc.setContractValue(map.get(cfield.getFieldCode()).toString());
						String tmp=c.getContractContent();
						tmp=tmp.replaceAll(sc.getFieldCode(), sc.getContractValue());
						c.setContractContent(tmp);
						tbContractSignDao.update(c);//修改签约表
					}
					tbSginContractDao.saveByXml(sc);
				}
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 获取合同字段设置列表
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> getSettingData(String loanId) {
		Map<String,Object> map=new HashMap<String,Object>();
		TbContractSign cs=new TbContractSign();
		cs.setLoanId(loanId);
		TbContractSginField sc=new TbContractSginField();
		sc.setLoanId(loanId);
		List<TbContractSign> csList=tbContractSignDao.findList(cs);
		List<TbContractSginField> scList=tbSginContractService.findList(sc);
		map.put("csList", csList);
		map.put("scList", scList);
		return  map;
	}
	/**
	 * 提交参数表单
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void settingParm(String[] ids,String[] values) {
		TbContractSginField sc=null;
		TbContractSign cs=null;
		TbContractTemp c=null;
		List<String> strArray=new ArrayList<String>();
		for(int i=0;i<values.length;i++) {
			String value=values[i];
			if(StringUtils.isNotBlank(value)) {
				sc=new TbContractSginField();
				sc.setId(ids[i]);
				sc=tbSginContractDao.get(sc);
				sc.setContractValue(value);
				tbSginContractDao.update(sc);
				strArray.add(sc.getContractId()+"");
			}
		}
		HashSet<String> hs = new HashSet<String>(strArray);
		Iterator<String> iterator=hs.iterator();
		while(iterator.hasNext()){
			String id=iterator.next().toString();
			System.err.println(id);
			cs=new TbContractSign();
			cs.setId(id);
			cs=tbContractSignDao.get(cs);

			c=new TbContractTemp();
			c.setId(cs.getContractTempId()+"");
			c=get(c);
			String tmp=c.getTempContent();
			sc=new  TbContractSginField();
			sc.setContractId(Long.parseLong(id));
			List<TbContractSginField> scList=tbSginContractDao.findList(sc);

			System.err.println(scList.size());
			for(TbContractSginField sc1:scList) {
				if(StringUtils.isNotBlank(sc1.getContractValue())) {
					tmp=tmp.replaceAll(sc1.getFieldCode(), sc1.getContractValue());
				}

			}
			cs.setContractContent(tmp);
			tbContractSignDao.update(cs);
		}
		sc=null;
	}
	/**
	 * 首次加载合同信息
	 * @param contractNum
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> initContarctMap(String contractNum,String loanId) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY年MM月dd日");
		Date date=new Date();
		TbLoanApply loanApply=tbLoanApplyService.get(loanId);
		Map<String,Object> map=new HashMap<String,Object>();
		TbComp coreComp=tbCompService.get(loanApply.getCoreCompId());
		TbProductBorrowType productBorrowType=tbProductBorrowTypeService.get(loanApply.getLoanTime()+"");
		if(coreComp!=null&&loanApply!=null) {
			map.put("jd_bl_htbh_", contractNum);
			map.put("jd_bl_mf_", loanApply.getCompName()!=null?loanApply.getCompName():"");
			map.put("jd_bl_mffr_", loanApply.getCompLegalPerson()!=null?loanApply.getCompLegalPerson():"");
			map.put("jd_bl_mffddz_", loanApply.getRegisterAddress()!=null?loanApply.getRegisterAddress():"");
			map.put("jd_bl_mfzysm_", loanApply.getMainBusiness()!=null?loanApply.getMainBusiness():"");
			map.put("jd_bl_hxqymc_", coreComp.getCompName()!=null?coreComp.getCompName():"");
			map.put("jd_bl_gxhtbh_", loanApply.getContarctCode()!=null?loanApply.getContarctCode():"");
			map.put("jd_bl_ckrq_", loanApply.getUrgeDateEnd()!=null?loanApply.getUrgeDateEnd():"");
			map.put("jd_bl_rzje_",NumberToCN.number2CNMontrayUnit(new BigDecimal(loanApply.getLoanAmount())));
			map.put("jd_bl_ll_", productBorrowType.getBorrowRate()+"%");
			map.put("jd_bl_yszkyxqs_", loanApply.getUrgeDateStart()!=null?loanApply.getUrgeDateStart():"");
			map.put("jd_bl_yszkyxqz_", loanApply.getUrgeDateEnd()!=null?loanApply.getUrgeDateEnd():"");
			String loanDateStart=sdf.format(date);
			String loanDateEnd=sdf.format(ContarctUtils.addDate(date, Long.parseLong(productBorrowType.getBorrowTime())));
			map.put("jd_bl_htyxqs_", loanDateStart);
			map.put("jd_bl_htyxqz_", loanDateEnd);
			map.put("jd_bl_blgsqsrq_", loanDateStart);
			map.put("jd_bl_mfzjhlkhh_", loanApply.getMoneyCollectingBank()!=null?loanApply.getMoneyCollectingBank():"");
			map.put("jd_bl_mfzjhlyhm_", loanApply.getMoneyCollectingName()!=null?loanApply.getMoneyCollectingName():"");
			map.put("jd_bl_mfzjhlzh_", loanApply.getMoneyCollectingAccount()!=null?loanApply.getMoneyCollectingAccount():"");
			map.put("jd_bl_zmf_", loanApply.getCompName()!=null?loanApply.getCompName():"");
			map.put("jd_bl_zmftxdz_", loanApply.getRegisterAddress()!=null?loanApply.getRegisterAddress():"");
			map.put("jd_bl_zmfdh_", loanApply.getCompPhone()!=null?loanApply.getCompPhone():"");
			map.put("jd_bl_zmfcz_", "");
			map.put("jd_bl_zmflxr_", loanApply.getCompLegalPerson()!=null?loanApply.getCompLegalPerson():"");
			map.put("jd_bl_zmfyx_", loanApply.getCompEmail()!=null?loanApply.getCompEmail():"");


			map.put("jd_zr_htbh_", contractNum);
			map.put("jd_zr_crr_", loanApply.getCompName()!=null?loanApply.getCompName():"");
			map.put("jd_zr_blhtmcjbh_", contractNum);
			map.put("jd_zr_sfzjhm_", loanApply.getCardNo()!=null?loanApply.getCardNo():"");
			map.put("jd_zr_qsrq_", loanDateStart);
			map.put("jd_zr_crfr_", loanApply.getCompLegalPerson()!=null?loanApply.getCompLegalPerson():"");

			map.put("jd_db_htbh_", contractNum);
			map.put("jd_db_zwr_", loanApply.getCompName()!=null?loanApply.getCompName():"");
			map.put("jd_db_blhtmcjbh_", contractNum);
			map.put("jd_db_cnrzz_", loanApply.getActualLivingAddress()!=null?loanApply.getActualLivingAddress():"");
			map.put("jd_db_cnrsdh_", loanApply.getLegalPersonPhone()!=null?loanApply.getLegalPersonPhone():"");
			map.put("jd_db_blhtqsrq_", loanDateStart);
			map.put("jd_db_cnrq_", loanDateStart);
			map.put("jd_db_cnr_", loanApply.getCompLegalPerson()!=null?loanApply.getCompLegalPerson():"");
			map.put("jd_db_cnrsfz_", loanApply.getCardNo()!=null?loanApply.getCardNo():"");

			map.put("jd_qd_htbh_", contractNum);
			map.put("jd_qd_crq_", loanApply.getCompName()!=null?loanApply.getCompName():"");
			map.put("jd_qd_blhtmcjbh_", contractNum);
			map.put("jd_qd_mfmc1_", coreComp.getCompName()!=null?coreComp.getCompName():"");
			map.put("jd_qd_gxht1_", loanApply.getContarctCode()!=null?loanApply.getContarctCode():"");
			map.put("jd_qd_htje1_", loanApply.getContactAmount()!=null?loanApply.getContactAmount():"");
			map.put("jd_qd_fr_", loanApply.getCompLegalPerson()!=null?loanApply.getCompLegalPerson():"");
			map.put("jd_qd_qsrq_", loanDateStart);

			map.put("jd_qq_htmcjbh_", contractNum);
			map.put("jd_qq_hxqymc_", coreComp.getCompName()!=null?coreComp.getCompName():"");
			map.put("jd_qq_jkqymc_", loanApply.getCompName()!=null?loanApply.getCompName():"");
			//		map.put("jd_qq_khh_", loanApply.getContarctCode()!=null?loanApply.getContarctCode():"");
			//		map.put("jd_qq_hm_", loanApply.getContactAmount()!=null?loanApply.getContactAmount():"");
			//		map.put("jd_qq_zh_", loanApply.getCompLegalPerson()!=null?loanApply.getCompLegalPerson():"");
			map.put("jd_qq_qsrq_", loanDateStart);
			map.put("jd_qq_blhtqsrq_", loanDateStart);
		}
		return map; 

	}

	/**
	 * 一键签约
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public boolean signCountarct(String loanId){
		boolean flag=false;
		try {
			TbContractSign cs=new TbContractSign();
			cs.setLoanId(loanId);
			cs.setIsSign(0);
			TbLoanApply loan=tbLoanApplyService.get(loanId);
			if(loan!=null&&tbContractApiService.regSSQ(loanId,loan.getCompId(),loan.getCoreCompId())){//注册企业 并且申请证书  创建印章
				List<TbContractSign> list=tbContractSignDao.findList(cs);//获取待签约合同列表
				System.out.println("list.size()"+list.size());
				for(TbContractSign tbCs:list){
					String tmp=tbCs.getContractContent();
					TbContractSginField sc=new TbContractSginField();
					sc.setContractId(Long.parseLong(tbCs.getId()));
					List<TbContractSginField> scList=tbSginContractService.findList(sc);//获取签约字段
					for(TbContractSginField scObj:scList){
						if(StringUtils.isNoneBlank(scObj.getFieldCode())&&StringUtils.isBlank(scObj.getContractValue())){
							tmp=tmp.replaceAll(scObj.getFieldCode(), "");//替换签约代码为 ""
						}
					}
					File file=new File(tbCs.getUploadPdfpath().substring(0, tbCs.getUploadPdfpath().lastIndexOf("/")+1));
					if(!file.exists()){//创建签约路径
						file.mkdirs();
					}
					flag=Html2PdfUtils.htmlToPdfString(tmp,tbCs.getUploadPdfpath(),fontPath);
					if(flag){//生成签约PDF模板
						tbCs.setContractContent(tmp);
						tbContractSignDao.update(tbCs);
						String apiContarctId="";
						TbContractApiChild contractApiChild=new TbContractApiChild();
						contractApiChild.setContractId(tbCs.getId());
						contractApiChild=tbContractApiChildDao.getByEntity(contractApiChild);
						if(contractApiChild==null){
							apiContarctId=tbContractApiService.uploadPDF(loanId,tbCs.getUploadPdfpath(),tbCs.getId());
						}else{
							apiContarctId=contractApiChild.getApiContractId();
						}
						System.err.println(apiContarctId);
						if(StringUtils.isNoneBlank(apiContarctId)){
							flag=tbContractApiService.addSigners(apiContarctId, tbCs, loanId);

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}
	/**
	 * 借款企业签约
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public boolean signCountarctByLoanComp(String loanId){
		boolean flag=false;
		// 签署类型 0甲乙双方盖章 1甲方盖章  2甲方签字3甲乙丙三方盖章
		String compAccount="";
		String singleAccount="";
		TbLoanApply loan=null;

		try {
			TbContractSign cs=new TbContractSign();
			cs.setLoanId(loanId);
			cs.setIsSign(0);

			loan=tbLoanApplyService.get(loanId);
			TbContractApi api=new TbContractApi();
			api.setCompId(loan!=null?loan.getCompId():null);
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
			List<TbContractSign> list=tbContractSignDao.findList(cs);//获取待签约合同列表
			if(list!=null&&list.size()>0){
				for(TbContractSign tbCs:list){
					TbContractApiChild apiChild=new TbContractApiChild();
					apiChild.setContractId(tbCs.getId());
					apiChild=tbContractApiChildDao.getByEntity(apiChild);
					//tbContractApiService.getSignerStatus(compId,apiChild.getApiContractId());
					System.err.println(apiChild.getApiContractId());
					if(tbCs.getSignType()==0||tbCs.getSignType()==1||tbCs.getSignType()==3){//签约状态是0 1 3 的时候盖章
						flag=tbContractApiService.signApiContract(loanId,apiChild.getApiContractId(),compAccount,tbCs);
					}
					if(tbCs.getSignType()==2){//签约状态是2 的时候签字
						flag=tbContractApiService.signApiContract(loanId,apiChild.getApiContractId(),singleAccount,tbCs);
					}
					if(tbCs.getSignType()!=3){//签约状态是不是3的时候结束合同
						if(flag&&tbContractApiService.overContract(loanId, apiChild.getApiContractId())
								&&tbContractApiService.getDownloadURLs(loanId, apiChild.getApiContractId())) {
							apiChild=tbContractApiChildDao.getByEntity(apiChild);
							if(StringUtils.isNoneBlank(apiChild.getApiContractAttUrl())&&
									StringUtils.isNoneBlank(apiChild.getApiContractUrl())) {
								apiChild=tbContractApiChildDao.getByEntity(apiChild);
								SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
								Date date=new Date();
								String path=downloadPath+"/"+ sdf.format(date)+"/"+loanId+"/";
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
								tbCs.setIsSign(1);
								tbContractSignDao.update(tbCs);

							}
							flag= true;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	/**
	 * 核心企业签约
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public boolean signCountarctByCoreComp(String loanId){
		boolean flag=false;
		// 签署类型 0甲乙双方盖章 1甲方盖章  2甲方签字3甲乙丙三方盖章
		String account="";
		TbLoanApply loan=null;

		try {
			TbContractSign cs=new TbContractSign();
			cs.setLoanId(loanId);
			cs.setIsSign(0);

			loan=tbLoanApplyService.get(loanId);
			TbContractApi api=new TbContractApi();
			api.setCompId(loan!=null?loan.getCoreCompId():null);
			api.setSsqType(2);
			api=tbContractApiDao.getByEntity(api);
			account=api.getSsqId();
			List<TbContractSign> list=tbContractSignDao.findList(cs);//获取待签约合同列表
			if(list!=null&&list.size()>0){
				for(TbContractSign tbCs:list){
					TbContractApiChild apiChild=new TbContractApiChild();
					apiChild.setContractId(tbCs.getId());
					apiChild=tbContractApiChildDao.getByEntity(apiChild);
					flag=tbContractApiService.signApiContract(loanId,apiChild.getApiContractId(),account,tbCs);
					if(flag&&tbContractApiService.overContract(loanId, apiChild.getApiContractId())
							&&tbContractApiService.getDownloadURLs(loanId, apiChild.getApiContractId())) {
						apiChild=tbContractApiChildDao.getByEntity(apiChild);
						if(StringUtils.isNoneBlank(apiChild.getApiContractAttUrl())&&
								StringUtils.isNoneBlank(apiChild.getApiContractUrl())) {
							apiChild=tbContractApiChildDao.getByEntity(apiChild);
							SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
							Date date=new Date();
							String path=downloadPath+"/"+ sdf.format(date)+"/"+loanId+"/";
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
							tbCs.setIsSign(1);
							tbContractSignDao.update(tbCs);

						}
						flag= true;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}


	class CreatePDFThead implements Callable<Boolean>{
		private String tmp;
		private String fileName;
		private String fontPath;

		public CreatePDFThead(String tmp, String fileName, String fontPath) {
			super();
			this.tmp = tmp;
			this.fileName = fileName;
			this.fontPath = fontPath;
		}

		@Override
		public Boolean call() throws Exception {
			try {

				File file=new File(fileName.substring(0, fileName.lastIndexOf("/")+1));
				if(!file.exists()){
					file.mkdirs();
				}
				System.out.println("fontPath==============="+fontPath);
				if(Html2PdfUtils.htmlToPdfString(tmp,fileName,fontPath)){
					//上传 savePath 到上上签
				} 
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

	}
	public static void main(String[] args) {
		String s="d:/contract/uploadpdf/20180514/1/保理合同_1526272033101.pdf";
		System.err.println(s.substring(0, s.lastIndexOf("/")+1));
	}
}