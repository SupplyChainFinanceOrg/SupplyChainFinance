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
 * tb_contractEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_contract", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="contract_name", attrName="contractName", label="合同名称", queryType=QueryType.LIKE),
		@Column(name="product_id", attrName="productId", label="产品id"),
		@Column(name="sign_state", attrName="signState", label="签署状态id"),
		@Column(name="temp_path", attrName="tempPath", label="合同模板路径"),
		@Column(name="contract_name", attrName="contractName", label="合同名称", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class TbContract extends DataEntity<TbContract> {
	
	private static final long serialVersionUID = 1L;
	private String contractName;		// 合同名称
	private String productId;		// 产品id
	private String signState;		// 签署状态id
	private String tempPath;		// 合同模板路径
	
	public TbContract() {
		this(null);
	}

	public TbContract(String id){
		super(id);
	}
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getSignState() {
		return signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}
	
	@Length(min=0, max=200, message="合同模板路径长度不能超过 200 个字符")
	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	
	@Length(min=0, max=50, message="合同名称长度不能超过 50 个字符")
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
}