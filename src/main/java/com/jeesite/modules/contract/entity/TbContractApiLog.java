/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_contract_api_logEntity
 * @author hanzl
 * @version 2018-05-14
 */
@Table(name="tb_contract_api_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
		@Column(name="api_name", attrName="apiName", label="接口名称", queryType=QueryType.LIKE),
		@Column(name="err_code", attrName="errCode", label="错误代码"),
		@Column(name="err_msg", attrName="errMsg", label="错误消息"),
		@Column(name="add_time", attrName="addTime", label="错误消息"),
	}, orderBy="a.id DESC"
)
public class TbContractApiLog extends DataEntity<TbContractApiLog> {
	
	private static final long serialVersionUID = 1L;
	private String loanId;		// 企业id
	private String apiName;		// 接口名称
	private String errCode;		// 错误代码
	private String errMsg;		// 错误消息
	private String addTime;
	
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public TbContractApiLog() {
		this(null);
	}

	public TbContractApiLog(String id){
		super(id);
	}
	
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	@Length(min=0, max=200, message="接口名称长度不能超过 200 个字符")
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
	@Length(min=0, max=20, message="错误代码长度不能超过 20 个字符")
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	@Length(min=0, max=500, message="错误消息长度不能超过 500 个字符")
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}