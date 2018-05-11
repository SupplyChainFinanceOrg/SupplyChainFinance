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
		@Column(name="name", attrName="name", label="合同名称", queryType=QueryType.LIKE),
		@Column(name="product_id", attrName="productId", label="产品id"),
		@Column(name="sign_state", attrName="signState", label="签署状态id"),
		@Column(name="temp_content", attrName="tempContent", label="合同模板路径"),
		@Column(name="short_name", attrName="shortName", label="简称"),
	}, orderBy="a.id ASC"
)
public class TbContract extends DataEntity<TbContract> {
	
	private static final long serialVersionUID = 1L;
	private String name;			// 合同名称
	private String productId;		// 产品id
	private String signState;		// 签署状态id
	private String tempContent;		// 合同模板路径
	private String shortName;		// 简称
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

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