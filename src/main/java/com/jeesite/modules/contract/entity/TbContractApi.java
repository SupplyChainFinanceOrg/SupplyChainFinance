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
		@Column(name="cert_type", attrName="certType", label="证书类型"),
		@Column(name="has_seal", attrName="hasSeal", label="是否有印章0无 1 有"),
		@Column(name="ssq_id", attrName="ssqId", label="上上签用户名"),
		@Column(name="ssq_type", attrName="ssqType", label="上上签类型 1个人 2企业"),
	}, orderBy="a.id DESC"
)
public class TbContractApi extends DataEntity<TbContractApi> {
	
	private static final long serialVersionUID = 1L;
	private String compId;		// 企业id
	private String cert;		// 数字证书
	private String certType;		// 证书类型
	private Integer hasSeal;		// 是否有印章0无 1 有
	private String ssqId;		// 上上签用户名
	private Integer ssqType;		// 上上签类型 1个人 2企业
	
	public String getSsqId() {
		return ssqId;
	}

	public void setSsqId(String ssqId) {
		this.ssqId = ssqId;
	}

	public Integer getSsqType() {
		return ssqType;
	}

	public void setSsqType(Integer ssqType) {
		this.ssqType = ssqType;
	}

	public Integer getHasSeal() {
		return hasSeal;
	}

	public void setHasSeal(Integer hasSeal) {
		this.hasSeal = hasSeal;
	}

	public TbContractApi() {
		this(null);
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
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
	
}