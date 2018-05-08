/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.button.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_buttonEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_button", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="url", attrName="url", label="挑战链接"),
		@Column(name="role_id", attrName="roleId", label="可见角色"),
		@Column(name="button_type", attrName="buttonType", label="按钮类型 0列表 1表单"),
	}, orderBy="a.id DESC"
)
public class TbButton extends DataEntity<TbButton> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String url;		// 挑战链接
	private String roleId;		// 可见角色
	private Integer buttonType;		// 按钮类型 0列表 1表单
	
	public TbButton() {
		this(null);
	}

	public TbButton(String id){
		super(id);
	}
	
	@Length(min=0, max=20, message="名称长度不能超过 20 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="挑战链接长度不能超过 200 个字符")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=500, message="可见角色长度不能超过 500 个字符")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public Integer getButtonType() {
		return buttonType;
	}

	public void setButtonType(Integer buttonType) {
		this.buttonType = buttonType;
	}
	
}