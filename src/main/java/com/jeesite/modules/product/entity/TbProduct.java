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
 * tb_productEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_product", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="prodct_name", attrName="prodctName", label="应付账款融资", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class TbProduct extends DataEntity<TbProduct> {
	
	private static final long serialVersionUID = 1L;
	private String prodctName;		// 应付账款融资
	
	public TbProduct() {
		this(null);
	}

	public TbProduct(String id){
		super(id);
	}
	
	@Length(min=0, max=50, message="应付账款融资长度不能超过 50 个字符")
	public String getProdctName() {
		return prodctName;
	}

	public void setProdctName(String prodctName) {
		this.prodctName = prodctName;
	}
	
}