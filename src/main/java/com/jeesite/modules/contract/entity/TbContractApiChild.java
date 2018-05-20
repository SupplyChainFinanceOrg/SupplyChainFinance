/**
api_contract_name * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_contract_api_childEntity
 * @author hanzl
 * @version 2018-05-15
 */
@Table(name="tb_contract_api_child", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="api_contract_id", attrName="apiContractId", label="上上签的合同id"),
		@Column(name="contract_id", attrName="contractId", label="平台合同id"),
		@Column(name="api_contract_url", attrName="apiContractUrl", label="合同url"),
		@Column(name="api_contract_att_url", attrName="apiContractAttUrl", label="附件url"),
		@Column(name="file_id", attrName="fileId", label="文件id"),
		@Column(name="has_add_signer", attrName="hasAddSigner", label="是否添加签署者"),
		@Column(name="api_contract_name", attrName="apiContractName", label="合同名称"),
		@Column(name="api_contract_att_name", attrName="apiContractAttName", label="附件名称"),
	}, orderBy="a.id DESC" 
)
public class TbContractApiChild extends DataEntity<TbContractApiChild> {
	
	private static final long serialVersionUID = 1L;
	private String apiContractId;		// 上上签的合同id
	private String contractId;		// 平台合同id
	private String apiContractUrl;		// 合同url
	private String apiContractAttUrl;		// 附件url
	private String fileId;// 文件id
	private Integer hasAddSigner;//是否添加签署者
	private String apiContractName;// 合同名称
	private String apiContractAttName;// 附件名称
	public String getApiContractName() {
		return apiContractName;
	}

	public void setApiContractName(String apiContractName) {
		this.apiContractName = apiContractName;
	}

	public String getApiContractAttName() {
		return apiContractAttName;
	}

	public void setApiContractAttName(String apiContractAttName) {
		this.apiContractAttName = apiContractAttName;
	}

	public Integer getHasAddSigner() {
		return hasAddSigner;
	}

	public void setHasAddSigner(Integer hasAddSigner) {
		this.hasAddSigner = hasAddSigner;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public TbContractApiChild() {
		this(null);
	}

	public TbContractApiChild(String id){
		super(id);
	}
	
	@Length(min=0, max=200, message="上上签的合同id长度不能超过 200 个字符")
	public String getApiContractId() {
		return apiContractId;
	}

	public void setApiContractId(String apiContractId) {
		this.apiContractId = apiContractId;
	}
	
	@Length(min=0, max=64, message="平台合同id长度不能超过 64 个字符")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	public String getApiContractUrl() {
		return apiContractUrl;
	}

	public void setApiContractUrl(String apiContractUrl) {
		this.apiContractUrl = apiContractUrl;
	}
	
	public String getApiContractAttUrl() {
		return apiContractAttUrl;
	}

	public void setApiContractAttUrl(String apiContractAttUrl) {
		this.apiContractAttUrl = apiContractAttUrl;
	}
	
}