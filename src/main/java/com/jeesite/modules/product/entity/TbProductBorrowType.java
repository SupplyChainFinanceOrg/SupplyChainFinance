/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_product_borrow_typeEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_product_borrow_type", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="product_id", attrName="productId", label="产品id"),
		@Column(name="borrow_time", attrName="borrowTime", label="产品期限"),
		@Column(name="borrow_rate", attrName="borrowRate", label="产品费率"),
	}, orderBy="a.id DESC"
)
public class TbProductBorrowType extends DataEntity<TbProductBorrowType> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品id
	private String borrowTime;		// 产品期限
	private Double borrowRate;		// 产品费率
	
	public TbProductBorrowType() {
		this(null);
	}

	public TbProductBorrowType(String id){
		super(id);
	}
	
	@Length(min=0, max=11, message="产品id长度不能超过 11 个字符")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=50, message="产品期限长度不能超过 50 个字符")
	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}
	
	public Double getBorrowRate() {
		return borrowRate;
	}

	public void setBorrowRate(Double borrowRate) {
		this.borrowRate = borrowRate;
	}
	
}