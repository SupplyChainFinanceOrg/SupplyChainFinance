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
 * tb_contract_sign_pointEntity
 * @author hanzl
 * @version 2018-05-19
 */
@Table(name="tb_contract_sign_point", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="contract_temp_id", attrName="contractTempId", label="contract_id"),
		@Column(name="page_num", attrName="pageNum", label="page_num"),
		@Column(name="page_xpoint", attrName="pageXpoint", label="page_xpoint"),
		@Column(name="page_ypoint", attrName="pageYpoint", label="page_ypoint"),
		@Column(name="account_type", attrName="accountType", label="account"),//用户类型1平台2借款企业3核心企业
	}, orderBy="a.id ASC"
)
public class TbContractSignPoint extends DataEntity<TbContractSignPoint> {
	
	private static final long serialVersionUID = 1L;
	private Long contractTempId;		// contract_id
	private String pageNum;		// page_num
	private String pageXpoint;		// page_xpoint
	private String pageYpoint;		// page_ypoint
	private Long accountType;		// account
	
	public TbContractSignPoint() {
		this(null);
	}

	public TbContractSignPoint(String id){
		super(id);
	}
	
	
	@Length(min=0, max=20, message="page_num长度不能超过 20 个字符")
	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	
	@Length(min=0, max=20, message="page_xpoint长度不能超过 20 个字符")
	public String getPageXpoint() {
		return pageXpoint;
	}

	public void setPageXpoint(String pageXpoint) {
		this.pageXpoint = pageXpoint;
	}
	
	@Length(min=0, max=20, message="page_ypoint长度不能超过 20 个字符")
	public String getPageYpoint() {
		return pageYpoint;
	}

	public void setPageYpoint(String pageYpoint) {
		this.pageYpoint = pageYpoint;
	}

	public Long getContractTempId() {
		return contractTempId;
	}

	public void setContractTempId(Long contractTempId) {
		this.contractTempId = contractTempId;
	}

	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}
	
	
}