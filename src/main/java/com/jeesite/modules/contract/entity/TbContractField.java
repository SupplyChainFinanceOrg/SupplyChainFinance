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
 * tb_contract_fieldEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_contract_field", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="contract_id", attrName="contractId", label="合同id"),
		@Column(name="field_name", attrName="fieldName", label="字段名称", queryType=QueryType.LIKE),
		@Column(name="field_code", attrName="fieldCode", label="字段编码"),
		@Column(name="field_default_value", attrName="fieldDefaultValue", label="字段默认值"),
		@Column(name="state", attrName="state", label="借款状态"),
	}, orderBy="a.id ASC"
)
public class TbContractField extends DataEntity<TbContractField> {
	
	private static final long serialVersionUID = 1L;
	private String contractId;		// 合同id
	private String fieldName;		// 字段名称.
	private String fieldCode;       //字段代码
	private String fieldDefaultValue;//字段默认值
	private Integer state;//字段默认值
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public TbContractField() {
		this(null);
	}

	public TbContractField(String id){
		super(id);
	}
	
	@Length(min=0, max=11, message="合同id长度不能超过 11 个字符")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	@Length(min=0, max=200, message="字段名称长度不能超过 200 个字符")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldDefaultValue() {
		return fieldDefaultValue;
	}

	public void setFieldDefaultValue(String fieldDefaultValue) {
		this.fieldDefaultValue = fieldDefaultValue;
	}
	
}