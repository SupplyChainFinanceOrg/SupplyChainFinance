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
 * tb_loan_attachmentEntity
 * @author zhengkj
 * @version 2018-05-08
 */
@Table(name="tb_loan_attachment", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="attachment_name", attrName="attachmentName", label="附件名称", queryType=QueryType.LIKE),
		@Column(name="attachment_remar", attrName="attachmentRemar", label="附件备注"),
		@Column(name="attachment_type", attrName="attachmentType", label="附件类型 0企业 1个人"),
		@Column(name="is_core_visible", attrName="isCoreVisible", label="0可见 1不可见"),
		@Column(name="is_bank_visible", attrName="isBankVisible", label="0可见 1不可见"),
		@Column(name="isdel", attrName="isdel", label="是否删除 0否 1是"),
		@Column(name="attachment_name", attrName="attachmentName", label="附件名称", queryType=QueryType.LIKE),
		@Column(name="attachment_remar", attrName="attachmentRemar", label="附件备注"),
		@Column(name="attachment_type", attrName="attachmentType", label="附件类型 0企业 1个人"),
		@Column(name="required", attrName="required", label="必填"),

	}, orderBy="a.id asc"
)
public class TbLoanAttachment extends DataEntity<TbLoanAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String attachmentName;		// 附件名称
	private String attachmentRemar;		// 附件备注
	private Integer attachmentType;		// 附件类型 0企业 1个人
	private Integer isCoreVisible;		// 0可见 1不可见
	private Integer isBankVisible;		// 0可见 1不可见
	private Integer isdel;		// 是否删除 0否 1是
	private String required;//required为必填
	
	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public TbLoanAttachment() {
		this(null);
	}

	public TbLoanAttachment(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="附件名称长度不能超过 64 个字符")
	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	@Length(min=0, max=500, message="附件备注长度不能超过 500 个字符")
	public String getAttachmentRemar() {
		return attachmentRemar;
	}

	public void setAttachmentRemar(String attachmentRemar) {
		this.attachmentRemar = attachmentRemar;
	}
	
	public Integer getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(Integer attachmentType) {
		this.attachmentType = attachmentType;
	}
	
	public Integer getIsCoreVisible() {
		return isCoreVisible;
	}

	public void setIsCoreVisible(Integer isCoreVisible) {
		this.isCoreVisible = isCoreVisible;
	}
	
	public Integer getIsBankVisible() {
		return isBankVisible;
	}

	public void setIsBankVisible(Integer isBankVisible) {
		this.isBankVisible = isBankVisible;
	}
	
	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	
	
}