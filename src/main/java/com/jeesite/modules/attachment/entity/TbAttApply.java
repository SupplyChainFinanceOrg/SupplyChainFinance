/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.attachment.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_att_applyEntity
 * @author zhengkj
 * @version 2018-05-08
 */
@Table(name="tb_att_apply", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="apply_id", attrName="applyId", label="申请表"),
		@Column(name="attachent_id", attrName="attachentId", label="附件"),
		@Column(name="path", attrName="path", label="附件位置"),
	}, orderBy="a.id DESC"
)
public class TbAttApply extends DataEntity<TbAttApply> {
	
	private static final long serialVersionUID = 1L;
	private String applyId;		// 申请表
	private String attachentId;		// 附件
	private String path;		// 附件位置
	
	public TbAttApply() {
		this(null);
	}

	public TbAttApply(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="申请表长度不能超过 64 个字符")
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	@Length(min=0, max=64, message="附件长度不能超过 64 个字符")
	public String getAttachentId() {
		return attachentId;
	}

	public void setAttachentId(String attachentId) {
		this.attachentId = attachentId;
	}
	
	@Length(min=0, max=250, message="附件位置长度不能超过 250 个字符")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}