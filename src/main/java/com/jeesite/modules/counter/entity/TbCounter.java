/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.counter.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_counterEntity
 * @author hanzl
 * @version 2018-05-14
 */
@Table(name="tb_counter", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="value", attrName="value", label="值"),
		@Column(name="code", attrName="code", label="编号"),
	}, orderBy="a.id DESC"
)
public class TbCounter extends DataEntity<TbCounter> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String value;		// 值
	private String code;		// 编号
	
	public TbCounter() {
		this(null);
	}

	public TbCounter(String id){
		super(id);
	}
	
	@Length(min=0, max=20, message="名称长度不能超过 20 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=20, message="值长度不能超过 20 个字符")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=0, max=20, message="编号长度不能超过 20 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}