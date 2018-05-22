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
 * tb_botton_userEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_botton_user", alias="a", columns={
		@Column(name="borrow_id", attrName="borrowId", label="借款id", isPK=true),
		@Column(name="user_code", attrName="userCode", label="用户id"),
		@Column(name="button_id", attrName="buttonId", label="按钮id"),
		@Column(name="state_id", attrName="stateId", label="状态id"),
		@Column(name="source_id", attrName="sourceId", label="资源id"),
		@Column(name="role_code", attrName="roleCode", label="角色id"),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="url", attrName="url", label="url"),
		@Column(name="isstatuss", attrName="isstatuss", label="包含状态"),
	}, orderBy="a.borrow_id DESC"
)
public class TbBottonUser extends DataEntity<TbBottonUser> {
	
	private static final long serialVersionUID = 1L;
	private String borrowId;		// 借款id
	private String userCode;		// 用户id
	private String buttonId;		// 按钮id
	private String stateId;		// 状态id
	private String sourceId;		// 资源id
	private String roleCode;		// 角色id
	private String type;
	private String url;
	private String isstatuss;//状态

	public String getIsstatuss() {
		return isstatuss;
	}

	public void setIsstatuss(String isstatuss) {
		this.isstatuss = isstatuss;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TbBottonUser() {
		this(null);
	}

	public TbBottonUser(String id){
		super(id);
	}
	
	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	
	@Length(min=0, max=100, message="用户id长度不能超过 100 个字符")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@Length(min=0, max=11, message="按钮id长度不能超过 11 个字符")
	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	
	@Length(min=0, max=11, message="状态id长度不能超过 11 个字符")
	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	
	@Length(min=0, max=100, message="资源id长度不能超过 100 个字符")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Length(min=0, max=100, message="角色id长度不能超过 100 个字符")
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}