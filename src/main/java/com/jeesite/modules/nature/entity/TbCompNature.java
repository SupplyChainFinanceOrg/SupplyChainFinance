/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.nature.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_comp_natureEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_comp_nature", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="nature", attrName="nature", label="公司性质"),
	}, orderBy="a.id DESC"
)
public class TbCompNature extends DataEntity<TbCompNature> {
	
	private static final long serialVersionUID = 1L;
	private String nature;		// 公司性质
	
	public TbCompNature() {
		this(null);
	}

	public TbCompNature(String id){
		super(id);
	}
	
	@Length(min=0, max=20, message="公司性质长度不能超过 20 个字符")
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}
	
}