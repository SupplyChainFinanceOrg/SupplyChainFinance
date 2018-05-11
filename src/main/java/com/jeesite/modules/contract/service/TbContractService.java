/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContract;
import com.jeesite.modules.contract.entity.TbContractField;
import com.jeesite.modules.contract.entity.TbContractSign;
import com.jeesite.modules.contract.entity.TbSginContract;
import com.jeesite.modules.contract.dao.TbContractDao;
import com.jeesite.modules.contract.dao.TbContractSignDao;
import com.jeesite.modules.contract.dao.TbSginContractDao;
import com.jeesite.modules.utils.Html2PdfUtils;
import com.jeesite.modules.utils.Utils;

/**
 * tb_contractService
 * @author z
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class TbContractService extends CrudService<TbContractDao, TbContract> {


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
			initField(contractSignList,"1");
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
			List<TbSginContract> scList= new ArrayList<TbSginContract>();//获取当前状态下需要签约的合同 
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
					}
					tbSginContractDao.saveByXml(sc);
					scList.add(sc);
				}
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 生成待签约的PDF
	 * @param tmp
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	public boolean sign(String tmp, String fileName,String filePath){
		boolean flag=false;
		CreatePDFThead createPDF=new CreatePDFThead(tmp,fileName,filePath);
		try {
			flag=createPDF.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	class CreatePDFThead implements Callable<Boolean>{
		private String tmp;
		private String fileName;
		private String filePath;

		public CreatePDFThead(String tmp, String fileName,String filePath) {
			super();
			this.tmp = tmp;
			this.fileName = fileName;
			this.filePath = filePath;
		}

		@Override
		public Boolean call() throws Exception {
			try {

				File file=new File(filePath);
				if(!file.exists()){
					file.mkdirs();
				}
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

}