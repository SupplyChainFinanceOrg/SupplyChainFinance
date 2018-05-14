/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_contract_apiEntity
 * @author hanzl
 * @version 2018-05-14
 */
@Table(name="tb_contract_api", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="comp_id", attrName="compId", label="企业id"),
		@Column(name="cert", attrName="cert", label="数字证书"),
		@Column(name="contract_id", attrName="contractId", label="合同id"),
		@Column(name="attachment_url", attrName="attachmentUrl", label="附件地址"),
		@Column(name="contract_url", attrName="contractUrl", label="合同地址"),
	}, orderBy="a.id DESC"
)
public class TbContractApi extends DataEntity<TbContractApi> {
	
	private static final long serialVersionUID = 1L;
	private String compId;		// 企业id
	private String cert;		// 数字证书
	private String contractId;		// 合同id
	private String attachmentUrl;		// 附件地址
	private String contractUrl;		// 合同地址
	
	public TbContractApi() {
		this(null);
	}

	public TbContractApi(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="企业id长度不能超过 64 个字符")
	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	@Length(min=0, max=200, message="数字证书长度不能超过 200 个字符")
	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}
	
	@Length(min=0, max=500, message="合同id长度不能超过 500 个字符")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	
	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
}