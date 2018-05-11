/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.add.entity;


import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户与角色关联表Entity
 * @author z
 * @version 2018-05-11
 */
@Table(name="${_prefix}sys_user_role", alias="a", columns={
		@Column(name="user_code", attrName="userCode", label="用户编码"),
		@Column(name="role_code", attrName="roleCode", label="角色编码"),
	}, orderBy="a.user_code DESC, a.role_code DESC"
)
public class UserRoleBase extends DataEntity<UserRoleBase> {
	
	private static final long serialVersionUID = 1L;
	private String userCode;		// 用户编码
	private String roleCode;		// 角色编码
	
	public UserRoleBase() {
		//this(null);
	}

	public UserRoleBase(String userCode, String roleCode){
		this.userCode = userCode;
		this.roleCode = roleCode;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}