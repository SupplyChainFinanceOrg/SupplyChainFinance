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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContract;
import com.jeesite.modules.contract.entity.TbContractApiChild;
import com.jeesite.modules.contract.entity.TbContractField;
import com.jeesite.modules.contract.entity.TbContractSign;
import com.jeesite.modules.contract.entity.TbSginContract;
import com.jeesite.modules.contract.utils.ContarctUtils;
import com.jeesite.modules.counter.dao.TbCounterDao;
import com.jeesite.modules.counter.entity.TbCounter;
import com.jeesite.modules.product.entity.TbProductBorrowType;
import com.jeesite.modules.product.service.TbProductBorrowTypeService;
import com.jeesite.modules.tb.entity.TbComp;
import com.jeesite.modules.tb.service.TbCompService;
import com.jeesite.modules.apply.entity.TbLoanApply;
import com.jeesite.modules.apply.service.TbLoanApplyService;
import com.jeesite.modules.contract.dao.TbContractApiChildDao;
import com.jeesite.modules.contract.dao.TbContractDao;
import com.jeesite.modules.contract.dao.TbContractSignDao;
import com.jeesite.modules.contract.dao.TbSginContractDao;
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
public class TbContractService extends CrudService<TbContractDao, TbContract> {


	@Value("${contractApi.code}")
	private String contarctCode;
	@Value("${contractApi.fontPath}")
	private  String fontPath;
	@Value("${contractApi.savePath}")
	private String savePath;
	//	@Autowired
	//	private TbContractFieldService tbContractFieldService;
	@Autowired
	private TbContractSignDao tbContractSignDao;
	@Autowired
	private TbContractFieldService tbContractFieldService;
	@Autowired
	private TbSginContractService tbSginContractService;
	@Autowired
	private TbSginContractDao tbSginContractDao;
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
	private TbContractApiChildDao tbContractApiChildDao;



	/**
	 * 获取单条数据
	 * @param tbContract
	 * @return
	 */
	@Override
	public TbContract get(TbContract tbContract) {
		return super.get(tbContract);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbContract
	 * @return
	 */
	@Override
	public Page<TbContract> findPage(Page<TbContract> page, TbContract tbContract) {
		return super.findPage(page, tbContract);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbContract tbContract) {
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
	public void updateStatus(TbContract tbContract) {
		super.updateStatus(tbContract);
	}

	/**
	 * 删除数据
	 * @param tbContract
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbContract tbContract) {
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
	public List<TbContractSign> contractSetting(String state,String loanId,List<TbContract> contractList){
		try {
			List<TbContractSign> contractSignList=new ArrayList<TbContractSign>();
			for(TbContract c:contractList){//循环合同
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
				contractSign.setState(state);
				contractSign.setContractId(Long.parseLong(c.getId()));
				TbContractSign contractSign1=tbContractSignDao.getByEntity(contractSign);//判断数据库是否存在
				contractSign.setContractContent(tmp);
				contractSign.setUploadPdfpath(fileName);
				contractSign.setShortName(c.getShortName());
				contractSign.setType(c.getType());
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
			TbSginContract sc=null;
			TbCounter counter=new TbCounter();
			counter.setCode("contact_code");
			counter=tbCounterDao.getByEntity(counter);
			System.err.println(counter.getValue());
			int num=Integer.parseInt(counter.getValue())+1;
			System.err.println("num:"+num);
			TbCounter counterNew=new TbCounter();
			counterNew.setValue(num+"");
			tbCounterDao.updateByEntity(counterNew,counter);
			String value=ContarctUtils.createContarctsCode(contarctCode,num+"");
			for(TbContractSign c:contractList){
				TbContractField cf=new TbContractField();
				cf.setContractId(c.getContractId()+"");
				List<TbContractField> cfList=tbContractFieldService.findList(cf);
				for(TbContractField cfield:cfList){
					sc=new TbSginContract();
					sc.setLoanId(loanId);
					sc.setContractId(Long.parseLong(c.getId()));
					sc.setContractFieldId(Long.parseLong(cfield.getId()));
					sc.setFieldName(cfield.getFieldName());
					sc.setFieldCode(cfield.getFieldCode());
					sc.setState(cfield.getState()+"");
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
	public Map<String,Object> getSettingData(String state,String loanId) {
		Map<String,Object> map=new HashMap<String,Object>();
		TbContractSign cs=new TbContractSign();
		cs.setState(state);
		cs.setLoanId(loanId);
		TbSginContract sc=new TbSginContract();
		sc.setState(state);
		sc.setLoanId(loanId);
		List<TbContractSign> csList=tbContractSignDao.findList(cs);
		List<TbSginContract> scList=tbSginContractService.findList(sc);
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
		TbSginContract sc=null;
		TbContractSign cs=null;
		TbContract c=null;
		List<String> strArray=new ArrayList<String>();
		for(int i=0;i<values.length;i++) {
			String value=values[i];
			if(StringUtils.isNotBlank(value)) {
				sc=new TbSginContract();
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

			c=new TbContract();
			c.setId(cs.getContractId()+"");
			c=get(c);
			String tmp=c.getTempContent();
			sc=new  TbSginContract();
			sc.setContractId(Long.parseLong(id));
			List<TbSginContract> scList=tbSginContractDao.findList(sc);

			System.err.println(scList.size());
			for(TbSginContract sc1:scList) {
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


		return map; 

	}

	/**
	 * 一键签约
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public synchronized boolean signCountarct(String state,String loanId,String compId){
		boolean flag=false;
		try {
			TbContractSign cs=new TbContractSign();
			cs.setLoanId(loanId);
			cs.setState(state);
			TbComp comp=tbCompService.get(compId);
			List<TbContractSign> list=tbContractSignDao.findList(cs);//获取待签约合同列表
			for(TbContractSign tbCs:list){
				String tmp=tbCs.getContractContent();
				TbSginContract sc=new TbSginContract();
				sc.setContractId(Long.parseLong(tbCs.getId()));
				List<TbSginContract> scList=tbSginContractService.findList(sc);//获取签约字段
				for(TbSginContract scObj:scList){
					if(StringUtils.isNoneBlank(scObj.getFieldCode())&&StringUtils.isBlank(scObj.getContractValue())){
						tmp=tmp.replaceAll(scObj.getFieldCode(), "");//替换签约代码为 ""
					}
				}
				File file=new File(tbCs.getUploadPdfpath().substring(0, tbCs.getUploadPdfpath().lastIndexOf("/")+1));
				if(!file.exists()){//创建签约路径
					file.mkdirs();
				}
				if(Html2PdfUtils.htmlToPdfString(tmp,tbCs.getUploadPdfpath(),fontPath)){//生成签约PDF模板
					if(tbContractApiService.regSSQ(compId)){//返回注册信息
						String apiContarctId="";
						TbContractApiChild contractApiChild=new TbContractApiChild();
						contractApiChild.setContractId(tbCs.getId());
						contractApiChild=tbContractApiChildDao.getByEntity(contractApiChild);
						if(contractApiChild==null){
							apiContarctId=tbContractApiService.uploadPDF(tbCs.getUploadPdfpath(), compId,tbCs.getId());
						}else{
							apiContarctId=contractApiChild.getApiContractId();
						}
						if(StringUtils.isNoneBlank(apiContarctId)){
							tbContractApiService.addSigners(apiContarctId, comp.getLegalPersonPhone(), compId, tbCs.getType());

						}
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