/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.lend.entity;

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
 * tb_lendEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_lend", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="lend_amount", attrName="lendAmount", label="放款金额"),
		@Column(name="user_id", attrName="userId", label="放款人id"),
		@Column(name="lend_time", attrName="lendTime", label="放款时间"),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
	}, orderBy="a.id DESC"
)
public class TbLend extends DataEntity<TbLend> {
	
	private static final long serialVersionUID = 1L;
	private String lendAmount;		// 放款金额
	private String userId;		// 放款人id
	private Date lendTime;		// 放款时间
	private String loanId;		// 借款id
	
	public TbLend() {
		this(null);
	}

	public TbLend(String id){
		super(id);
	}
	
	@Length(min=0, max=20, message="放款金额长度不能超过 20 个字符")
	public String getLendAmount() {
		return lendAmount;
	}

	public void setLendAmount(String lendAmount) {
		this.lendAmount = lendAmount;
	}
	
	@Length(min=0, max=64, message="放款人id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLendTime() {
		return lendTime;
	}

	public void setLendTime(Date lendTime) {
		this.lendTime = lendTime;
	}
	
	@Length(min=0, max=64, message="借款id长度不能超过 64 个字符")
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	public Date getLendTime_gte() {
		return sqlMap.getWhere().getValue("lend_time", QueryType.GTE);
	}

	public void setLendTime_gte(Date lendTime) {
		sqlMap.getWhere().and("lend_time", QueryType.GTE, lendTime);
	}
	
	public Date getLendTime_lte() {
		return sqlMap.getWhere().getValue("lend_time", QueryType.LTE);
	}

	public void setLendTime_lte(Date lendTime) {
		sqlMap.getWhere().and("lend_time", QueryType.LTE, lendTime);
	}
	
}