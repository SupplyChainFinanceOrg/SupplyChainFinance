/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_stateEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_state", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type", attrName="type", label="0注册 1流程"),
		@Column(name="product_id", attrName="productId", label="产品id  注册不写"),
		@Column(name="state_name", attrName="stateName", label="对应状态", queryType=QueryType.LIKE),
		@Column(name="operation_name", attrName="operationName", label="操作名称", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class TbState extends DataEntity<TbState> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 0注册 1流程
	private String productId;		// 产品id  注册不写
	private String stateName;		// 对应状态
	private String operationName;		// 操作名称
	
	public TbState() {
		this(null);
	}

	public TbState(String id){
		super(id);
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=11, message="产品id  注册不写长度不能超过 11 个字符")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=50, message="对应状态长度不能超过 50 个字符")
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	@Length(min=0, max=50, message="操作名称长度不能超过 50 个字符")
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
}