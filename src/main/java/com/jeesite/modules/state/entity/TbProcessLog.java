/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.state.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_process_logEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_process_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type", attrName="type", label="类型 0注册 1借款"),
		@Column(name="product_id", attrName="productId", label="产品id"),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="operation_time", attrName="operationTime", label="操作时间"),
		@Column(name="operation_log", attrName="operationLog", label="操作内容"),
		@Column(name="operation_remark", attrName="operationRemark", label="操作意见"),
		@Column(name="loan_comp_visible", attrName="loanCompVisible", label="借款企业可见"),
		@Column(name="core_comp_visible", attrName="coreCompVisible", label="核心企业可见"),
		@Column(name="bank_comp_visible", attrName="bankCompVisible", label="金融机构可见"),
		@Column(name="comp_id", attrName="compId", label="企业id"),
		@Column(name="logState", attrName="logState", label="状态"),

	}, orderBy="a.operation_time desc"
)
public class TbProcessLog extends DataEntity<TbProcessLog> {
	
	private static final long serialVersionUID = 1L;
	public static Integer REG_TYPE=0;
	public static Integer APPLY_TYPE=1;
	private Integer type;		// 类型 0注册 1借款
	private String productId;		// 产品id
	private String loanId;		// 借款id
	private String userId;		// 用户id
	private Date operationTime;		// 操作时间
	private String operationLog;		// 操作内容
	private String operationRemark;		// 操作意见
	private Integer loanCompVisible;		// 借款企业可见
	private Integer coreCompVisible;		// 核心企业可见
	private Integer bankCompVisible;		// 金融机构可见
	private Integer logState;		// 日志的状态
	private String compId;
	
	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public Integer getLogState() {
		return logState;
	}

	public void setLogState(Integer logState) {
		this.logState = logState;
	}

	public TbProcessLog() {
		this(null);
	}

	public TbProcessLog(String id){
		super(id);
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=11, message="产品id长度不能超过 11 个字符")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=64, message="借款id长度不能超过 64 个字符")
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	@Length(min=0, max=500, message="操作内容长度不能超过 500 个字符")
	public String getOperationLog() {
		return operationLog;
	}

	public void setOperationLog(String operationLog) {
		this.operationLog = operationLog;
	}
	
	@Length(min=0, max=500, message="操作意见长度不能超过 500 个字符")
	public String getOperationRemark() {
		return operationRemark;
	}

	public void setOperationRemark(String operationRemark) {
		this.operationRemark = operationRemark;
	}
	
	public Integer getLoanCompVisible() {
		return loanCompVisible;
	}

	public void setLoanCompVisible(Integer loanCompVisible) {
		this.loanCompVisible = loanCompVisible;
	}
	
	public Integer getCoreCompVisible() {
		return coreCompVisible;
	}

	public void setCoreCompVisible(Integer coreCompVisible) {
		this.coreCompVisible = coreCompVisible;
	}
	
	public Integer getBankCompVisible() {
		return bankCompVisible;
	}

	public void setBankCompVisible(Integer bankCompVisible) {
		this.bankCompVisible = bankCompVisible;
	}
	
}