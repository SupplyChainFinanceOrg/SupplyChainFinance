/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.contract.entity.TbContractApi;
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
	@Value("${contract.developerId}")
	private  String developerId ;
	@Value("${contract.praviteKey}")
	private  String praviteKey;
	@Value("${contract.host}")
	private  String host ;
	@Autowired
	private TbCompService tbCompService;
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
	public boolean regSSQ(String compId){
		TbContractApi api=new TbContractApi();
		api.setCompId(compId);
		api=get(api);
		if(api==null){
			TbComp comp=tbCompService.get(compId);
			bestSignDemo = BestSignDemo.getInstance(developerId, praviteKey, host);
			try {
				System.err.println("kong");
				String resultStr=bestSignDemo.userReg(comp.getLegalPersonPhone(), comp.getCompLegalPerson(), "2",
						comp.getCompEmail(),comp.getLegalPersonPhone()).toJSONString();
				JSONObject jsonObl=new JSONObject(resultStr);
				if(jsonObl.getString("errno").equals("0")){
					return true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
}