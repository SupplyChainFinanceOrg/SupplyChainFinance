/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.risk.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.control.entity.TbRiskControl;

/**
 * tb_loan_riskEntity
 * @author zhengkj
 * @version 2018-05-08
 */
@Table(name="tb_loan_risk", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="risk_id", attrName="riskId", label="风控id"),
		@Column(name="loan_id", attrName="loanId", label="借款id"),
		@Column(name="total_score", attrName="totalScore", label="分值"),
		@Column(name="score", attrName="score", label="得分"),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="complete", attrName="complete", label="齐全"),		
	},
    // 支持联合查询，如左右连接查询，支持设置查询自定义关联表的返回字段列
    joinTable={
      @JoinTable(type=Type.LEFT_JOIN, entity=TbRiskControl.class, alias="o", 
              on="o.id = a.risk_id",
              columns={@Column(includeEntity=TbRiskControl.class)}),
       
    }, orderBy="o.id DESC"
)
public class TbLoanRisk extends DataEntity<TbLoanRisk> {
	
	private static final long serialVersionUID = 1L;
	private Long riskId;		// 风控id
	private String loanId;		// 借款id
	private Long totalScore;		// 分值
	private Long score;		// 得分
	private String remark;		// 备注
	private TbRiskControl tbRiskControl;	
	private String complete;
	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	

	public TbRiskControl getTbRiskControl() {
		return tbRiskControl;
	}

	public void setTbRiskControl(TbRiskControl tbRiskControl) {
		this.tbRiskControl = tbRiskControl;
	}

	public TbLoanRisk() {
		this(null);
	}

	public TbLoanRisk(String id){
		super(id);
	}
	
	public Long getRiskId() {
		return riskId;
	}

	public void setRiskId(Long riskId) {
		this.riskId = riskId;
	}
	
	@Length(min=0, max=64, message="借款id长度不能超过 64 个字符")
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	public Long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}
	
	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
	
	@Length(min=0, max=500, message="备注长度不能超过 500 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}