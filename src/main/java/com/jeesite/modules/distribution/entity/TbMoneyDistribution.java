/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_money_distributionEntity
 * @author z
 * @version 2018-05-08
 */
@Table(name="tb_money_distribution", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
		@Column(name="apply_amount", attrName="applyAmount", label="申请金额"),
		@Column(name="bank_comp_id", attrName="bankCompId", label="金融机构id"),
	}, orderBy="a.id DESC"
)
public class TbMoneyDistribution extends DataEntity<TbMoneyDistribution> {
	
	private static final long serialVersionUID = 1L;
	private String loanId;		// 借款id
	private String applyAmount;		// 申请金额
	private String bankCompId;		// 金融机构id
	
	public TbMoneyDistribution() {
		this(null);
	}

	public TbMoneyDistribution(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="借款id长度不能超过 64 个字符")
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	@Length(min=0, max=20, message="申请金额长度不能超过 20 个字符")
	public String getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	@Length(min=0, max=64, message="金融机构id长度不能超过 64 个字符")
	public String getBankCompId() {
		return bankCompId;
	}

	public void setBankCompId(String bankCompId) {
		this.bankCompId = bankCompId;
	}
	
}