/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_sgin_contractEntity
 * @author zhengkj
 * @version 2018-05-08
 */
@Table(name="tb_contract_sgin_field", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
		@Column(name="contract_field_id", attrName="contractFieldId", label="合同字段id"),
		@Column(name="contract_value", attrName="contractValue", label="签署字段的值"),
		@Column(name="contract_id", attrName="contractId", label="合同id"),
		@Column(name="field_name", attrName="fieldName", label="字段名称"),
		@Column(name="field_code", attrName="fieldCode", label="字段代码"),
		@Column(name="remark", attrName="remark", label="说明"),
		@Column(name="is_edite", attrName="isEdite", label="是否可以编辑"),
	}, 
orderBy="a.id asc"
)
public class TbContractSginField extends DataEntity<TbContractSginField> {
	
	private static final long serialVersionUID = 1L;
	private String loanId;		// 借款id
	private Long contractFieldId;		// 合同字段id
	private String contractValue;		// 签署字段的值
	private Long contractId;		// 合同id
	private String fieldName;
	private String fieldCode;		// 签署字段的值
	private Integer isEdite;//签署状态
	private String remark;//说明
	
	public Integer getIsEdite() {
		return isEdite;
	}

	public void setIsEdite(Integer isEdite) {
		this.isEdite = isEdite;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public TbContractSginField() {
		this(null);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public TbContractSginField(String id){
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