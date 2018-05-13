/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.contract.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * tb_contract_signEntity
 * @author hanzl
 * @version 2018-05-10
 */
@Table(name="tb_contract_sign", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
		@Column(name="contract_id", attrName="contractId", label="合同id"),
		@Column(name="contract_content", attrName="contractContent", label="合同内容"),
		@Column(name="upload_pdfpath", attrName="uploadPdfpath", label="合同模板pdf路径"),
		@Column(name="operation_time", attrName="operationTime", label="操作时间"),
		@Column(name="state", attrName="state", label="操作时间"),
		@Column(name="short_name", attrName="shortName", label="简称"),
	}, orderBy="a.id asc"
)
public class TbContractSign extends DataEntity<TbContractSign> {
	
	private static final long serialVersionUID = 1L;
	private String loanId;		// 借款id
	private Long contractId;		// 合同id
	private String contractContent;		// 合同内容
	private String uploadPdfpath;// 合同模板pdf路径
	private Date operationTime;// 操作时间
	private String state;// 操作时间
	private String shortName;
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public TbContractSign() {
		this(null);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUploadPdfpath() {
		return uploadPdfpath;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public void setUploadPdfpath(String uploadPdfpath) {
		this.uploadPdfpath = uploadPdfpath;
	}

	public TbContractSign(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="借款id长度不能超过 64 个字符")
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	
	public String getContractContent() {
		return contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}
	
}