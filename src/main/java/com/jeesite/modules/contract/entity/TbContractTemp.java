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
@Table(name="tb_contract_temp", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="合同名称", queryType=QueryType.LIKE),
		@Column(name="product_id", attrName="productId", label="产品id"),
		@Column(name="temp_content", attrName="tempContent", label="合同模板路径"),
		@Column(name="short_name", attrName="shortName", label="简称"),
		@Column(name="sign_type", attrName="signType", label="类型"),
		
	}, orderBy="a.id ASC"
)
public class TbContractTemp extends DataEntity<TbContractTemp> {
	
	private static final long serialVersionUID = 1L;
	private String name;			// 合同名称
	private String productId;		// 产品id
	private String tempContent;		// 合同模板路径
	private String shortName;		// 简称
	private Integer signType;		// 简称

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public TbContractTemp() {
		this(null);
	}

	public TbContractTemp(String id){
		super(id);
	}
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Length(min=0, max=200, message="合同模板路径长度不能超过 200 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTempContent() {
		return tempContent;
	}

	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
	}
	
}