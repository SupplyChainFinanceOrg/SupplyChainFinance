/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contact.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * contractEntity
 * @author hanzl
 * @version 2018-05-09
 */
@Table(name="tb_contract", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="合同名称", queryType=QueryType.LIKE),
		@Column(name="product_id", attrName="productId", label="产品id"),
		@Column(name="sign_state", attrName="signState", label="签署状态id"),
		@Column(name="temp_content", attrName="tempContent", label="合同模板内容"),
	}, orderBy="a.id DESC"
)
public class TbContract extends DataEntity<TbContract> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 合同名称
	private Long productId;		// 产品id
	private Long signState;		// 签署状态id
	private String tempContent;		// 合同模板内容
	
	public TbContract() {
		this(null);
	}

	public TbContract(String id){
		super(id);
	}
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getSignState() {
		return signState;
	}

	public void setSignState(Long signState) {
		this.signState = signState;
	}
	
	public String getTempContent() {
		return tempContent;
	}

	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
	}
	
	@Length(min=0, max=50, message="合同名称长度不能超过 50 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}