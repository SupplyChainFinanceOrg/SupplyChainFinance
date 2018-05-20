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
		@Column(name="contract_temp_id", attrName="contractTempId", label="合同id"),
		@Column(name="contract_content", attrName="contractContent", label="合同内容"),
		@Column(name="upload_pdfpath", attrName="uploadPdfpath", label="合同模板pdf路径"),
		@Column(name="operation_time", attrName="operationTime", label="操作时间"),
		@Column(name="short_name", attrName="shortName", label="简称"),
		@Column(name="sign_type", attrName="signType", label="类型"),
		@Column(name="is_sign", attrName="isSign", label="是否已签"),
		@Column(name="down_pdfpath", attrName="downPdfpath", label="下载路径"),
		@Column(name="down_attpath", attrName="downAttpath", label="downAttpath"),
}, orderBy="a.id asc"
		)
public class TbContractSign extends DataEntity<TbContractSign> {

	private static final long serialVersionUID = 1L;
	private String loanId;		// 借款id
	private Long contractTempId;		// 合同id
	private String contractContent;		// 合同内容
	private String uploadPdfpath;// 合同模板pdf路径
	private Date operationTime;// 操作时间
	private String shortName;
	private Integer signType;		// 签署类型 0甲乙双方盖章 1甲方盖章  2甲方签字3甲乙丙三方盖章
	private Integer isSign;
	private String downPdfpath;
	private String downAttpath;
	
	public String getDownAttpath() {
		return downAttpath;
	}

	public void setDownAttpath(String downAttpath) {
		this.downAttpath = downAttpath;
	}

	public String getDownPdfpath() {
		return downPdfpath;
	}

	public void setDownPdfpath(String downPdfpath) {
		this.downPdfpath = downPdfpath;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public TbContractSign() {
		this(null);
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

	public Long getContractTempId() {
		return contractTempId;
	}

	public void setContractTempId(Long contractTempId) {
		this.contractTempId = contractTempId;
	}

	public String getContractContent() {
		return contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

}