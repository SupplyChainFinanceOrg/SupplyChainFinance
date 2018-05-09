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
 * tb_sgin_contractEntity
 * @author zhengkj
 * @version 2018-05-08
 */
@Table(name="tb_sgin_contract", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
		@Column(name="contract_field_id", attrName="contractFieldId", label="合同字段id"),
		@Column(name="contract_value", attrName="contractValue", label="签署字段的值"),
		@Column(name="contract_id", attrName="contractId", label="合同id"),
	}, orderBy="a.id DESC"
)
public class TbSginContract extends DataEntity<TbSginContract> {
	
	private static final long serialVersionUID = 1L;
	private String loanId;		// 借款id
	private Long contractFieldId;		// 合同字段id
	private String contractValue;		// 签署字段的值
	private Long contractId;		// 合同id
	
	public TbSginContract() {
		this(null);
	}

	public TbSginContract(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="借款id长度不能超过 64 个字符")
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	public Long getContractFieldId() {
		return contractFieldId;
	}

	public void setContractFieldId(Long contractFieldId) {
		this.contractFieldId = contractFieldId;
	}
	
	@Length(min=0, max=200, message="签署字段的值长度不能超过 200 个字符")
	public String getContractValue() {
		return contractValue;
	}

	public void setContractValue(String contractValue) {
		this.contractValue = contractValue;
	}
	
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	
}