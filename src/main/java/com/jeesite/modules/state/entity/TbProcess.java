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
 * tb_processEntity
 * @author z
 * @version 2018-05-11
 */
@Table(name="tb_process", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="button_id", attrName="buttonId", label="按钮id"),
		@Column(name="this_state_id", attrName="thisStateId", label="当前状态id"),
		@Column(name="next_state_id", attrName="nextStateId", label="下一个状态id"),
		@Column(name="role_id", attrName="roleId", label="角色"),
		@Column(name="type", attrName="type", label="0注册  1申请"),
	}, orderBy="a.id asc"
)
public class TbProcess extends DataEntity<TbProcess> {
	
	private static final long serialVersionUID = 1L;
	public static String COMPANYTYPE="0";
	public static String APPALYTYPE="1";

	private String buttonId;		// 按钮id
	private String thisStateId;		// 当前状态id
	private String nextStateId;		// 下一个状态id
	private String roleId;		// 角色
	private String type;		// 0注册  1申请
	private String name;		// 名称
	private String url;		// 挑战链接
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TbProcess() {
		this(null);
	}

	public TbProcess(String id){
		super(id);
	}
	
	@Length(min=0, max=11, message="按钮id长度不能超过 11 个字符")
	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	
	@Length(min=0, max=11, message="当前状态id长度不能超过 11 个字符")
	public String getThisStateId() {
		return thisStateId;
	}

	public void setThisStateId(String thisStateId) {
		this.thisStateId = thisStateId;
	}
	
	@Length(min=0, max=11, message="下一个状态id长度不能超过 11 个字符")
	public String getNextStateId() {
		return nextStateId;
	}

	public void setNextStateId(String nextStateId) {
		this.nextStateId = nextStateId;
	}
	
	@Length(min=0, max=64, message="角色长度不能超过 64 个字符")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Length(min=0, max=1, message="0注册  1申请长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}