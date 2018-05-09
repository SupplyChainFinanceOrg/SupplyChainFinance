/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.control.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_risk_controlEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_risk_control", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="content", attrName="content", label="了解的内容"),
		@Column(name="type", attrName="type", label="分类 0准备的资料 1企业调查 2个人调查 3综合部分"),
	}, orderBy="a.id DESC"
)
public class TbRiskControl extends DataEntity<TbRiskControl> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String content;		// 了解的内容
	private Integer type;		// 分类 0准备的资料 1企业调查 2个人调查 3综合部分
	
	public TbRiskControl() {
		this(null);
	}

	public TbRiskControl(String id){
		super(id);
	}
	
	@Length(min=0, max=200, message="名称长度不能超过 200 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1000, message="了解的内容长度不能超过 1000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}