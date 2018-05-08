/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.cooperation.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_time_cooperationEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_time_cooperation", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type", attrName="type", label="合作年限"),
	}, orderBy="a.id DESC"
)
public class TbTimeCooperation extends DataEntity<TbTimeCooperation> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 合作年限
	
	public TbTimeCooperation() {
		this(null);
	}

	public TbTimeCooperation(String id){
		super(id);
	}
	
	@Length(min=0, max=50, message="合作年限长度不能超过 50 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}