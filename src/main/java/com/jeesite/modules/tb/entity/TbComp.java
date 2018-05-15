/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.tb.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_compEntity
 * @author zheng
 * @version 2018-05-09
 */
@Table(name="tb_comp", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="comp_name", attrName="compName", label="企业名称", queryType=QueryType.LIKE),
		@Column(name="comp_code", attrName="compCode", label="统一社会信用代码", queryType=QueryType.LIKE),
		@Column(name="register_date", attrName="registerDate", label="注册时间"),
		@Column(name="register_address", attrName="registerAddress", label="注册地址"),
		@Column(name="register_money", attrName="registerMoney", label="注册资金"),
		@Column(name="comp_legal_person", attrName="compLegalPerson", label="企业法人", queryType=QueryType.LIKE),
		@Column(name="legal_person_phone", attrName="legalPersonPhone", label="法人电话"),
		@Column(name="comp_contact", attrName="compContact", label="企业联系人", queryType=QueryType.LIKE),
		@Column(name="contact_phone", attrName="contactPhone", label="联系人电话"),
		@Column(name="nature_id", attrName="natureId", label="公司性质id"),
		@Column(name="industry", attrName="industry", label="所属行业"),
		@Column(name="main_business", attrName="mainBusiness", label="主营业务"),
		@Column(name="comp_profile", attrName="compProfile", label="公司简介"),
		@Column(name="specia_industry", attrName="speciaIndustry", label="是否特殊行业 0否 1是"),
		@Column(name="special_trade_license", attrName="specialTradeLicense", label="特殊行业许可证"),
		@Column(name="business_license", attrName="businessLicense", label="营业执照"),
		@Column(name="id_card_a", attrName="idCardA", label="法人身份证正面"),
		@Column(name="id_card_b", attrName="idCardB", label="法人身份证反面"),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="comp_phone", attrName="compPhone", label="公司电话"),
		@Column(name="comp_email", attrName="compEmail", label="公司邮箱"),
		@Column(name="employees_count", attrName="employeesCount", label="员工数量"),
		@Column(name="comp_type", attrName="compType", label="企业类型"),
		@Column(name="apply_state", attrName="applyState", label="审核状态"),
		@Column(name="apply_date", attrName="applyDate", label="注册日期"),
		@Column(name="operation_user_id", attrName="operationUserId", label="操作人"),
		@Column(name="operation_data", attrName="operationData", label="操作时间"),
		@Column(name="comp_profile_attachment", attrName="compProfileAttachment", label="企业简介附件"),
		@Column(name="articles_association", attrName="articlesAssociation", label="企业章程附件  核心企业上传"),
		@Column(name="regCode", attrName="capitalVerificationReport", label="工商注册号"),
		@Column(name="orgCode", attrName="capitalVerificationReport", label="组织结构代码"),
		@Column(name="taxCode", attrName="capitalVerificationReport", label="税务登记号"),
		@Column(name="cardNo", attrName="capitalVerificationReport", label="法人身份证号"),
		@Column(name="capital_verification_report", attrName="capitalVerificationReport", label="验资报告附件  核心企业上传"),

	}, orderBy="a.id DESC"
)
/*private String regCode;		// 工商注册号
private String orgCode;		// 组织结构代码
private String taxCode;		// 税务登记号
private String cardNo;	  //法人身份证号
*/public class TbComp extends DataEntity<TbComp> {
	public static long JKQYTYPE=0;
	public static long HXQYTYPE=1;
	public static long JRQYTYPE=2;
	public static String JKQYROLECODE="jkqy";
	public static String HXQYROLECODE="hxqy";
	public static String JRQYROLECODE="jrjg";
	private static final long serialVersionUID = 1L;
	private String compName;		// 企业名称
	private String compCode;		// 统一社会信用代码
	private Date registerDate;		// 注册时间
	private String registerAddress;		// 注册地址
	private String registerMoney;		// 注册资金
	private String compLegalPerson;		// 企业法人
	private String legalPersonPhone;		// 法人电话
	private String compContact;		// 企业联系人
	private String contactPhone;		// 联系人电话
	private Integer natureId;		// 公司性质id
	private String industry;		// 所属行业
	private String mainBusiness;		// 主营业务
	private String compProfile;		// 公司简介
	private Integer speciaIndustry;		// 是否特殊行业 0否 1是
	private String specialTradeLicense;		// 特殊行业许可证
	private String businessLicense;		// 营业执照
	private String idCardA;		// 法人身份证正面
	private String idCardB;		// 法人身份证反面
	private String userId;		// 用户id
	private String compPhone;		// 公司电话
	private String compEmail;		// 公司邮箱
	private Long employeesCount;		// 员工数量
	private Integer compType;		// 企业类型
	private Long applyState;		// 审核状态
	private Date applyDate;		// 注册日期
	private String operationUserId;		// 操作人
	private Date operationData;		// 操作时间
	private String compProfileAttachment;		// 企业简介附件
	private String articlesAssociation;		// 企业章程附件  核心企业上传
	private String capitalVerificationReport;		// 验资报告附件  核心企业上传
	private String regCode;		// 工商注册号
	private String orgCode;		// 组织结构代码
	private String taxCode;		// 税务登记号
	private String cardNo;	  //法人身份证号
	public TbComp() {
		this(null);
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public TbComp(String id){
		super(id);
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=100, message="企业名称长度不能超过 100 个字符")
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=50, message="统一社会信用代码长度不能超过 50 个字符")
	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@Length(min=0, max=500, message="注册地址长度不能超过 500 个字符")
	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	
	@Length(min=0, max=20, message="注册资金长度不能超过 20 个字符")
	public String getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(String registerMoney) {
		this.registerMoney = registerMoney;
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=20, message="企业法人长度不能超过 20 个字符")
	public String getCompLegalPerson() {
		return compLegalPerson;
	}

	public void setCompLegalPerson(String compLegalPerson) {
		this.compLegalPerson = compLegalPerson;
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=20, message="法人电话长度不能超过 20 个字符")
	public String getLegalPersonPhone() {
		return legalPersonPhone;
	}

	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone;
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=20, message="企业联系人长度不能超过 20 个字符")
	public String getCompContact() {
		return compContact;
	}

	public void setCompContact(String compContact) {
		this.compContact = compContact;
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=20, message="联系人电话长度不能超过 20 个字符")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Integer getNatureId() {
		return natureId;
	}

	public void setNatureId(Integer natureId) {
		this.natureId = natureId;
	}
	
	@Length(min=0, max=20, message="所属行业长度不能超过 20 个字符")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}
	
	public String getCompProfile() {
		return compProfile;
	}

	public void setCompProfile(String compProfile) {
		this.compProfile = compProfile;
	}
	
	public Integer getSpeciaIndustry() {
		return speciaIndustry;
	}

	public void setSpeciaIndustry(Integer speciaIndustry) {
		this.speciaIndustry = speciaIndustry;
	}
	
	public String getSpecialTradeLicense() {
		return specialTradeLicense;
	}

	public void setSpecialTradeLicense(String specialTradeLicense) {
		this.specialTradeLicense = specialTradeLicense;
	}
	
	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	
	public String getIdCardA() {
		return idCardA;
	}

	public void setIdCardA(String idCardA) {
		this.idCardA = idCardA;
	}
	
	public String getIdCardB() {
		return idCardB;
	}

	public void setIdCardB(String idCardB) {
		this.idCardB = idCardB;
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=20, message="公司电话长度不能超过 20 个字符")
	public String getCompPhone() {
		return compPhone;
	}

	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}
	@NotBlank(message="录入信息有误")
	@Length(min=0, max=64, message="公司邮箱长度不能超过 64 个字符")
	public String getCompEmail() {
		return compEmail;
	}

	public void setCompEmail(String compEmail) {
		this.compEmail = compEmail;
	}
	
	public Long getEmployeesCount() {
		return employeesCount;
	}

	public void setEmployeesCount(Long employeesCount) {
		this.employeesCount = employeesCount;
	}
	
	public Integer getCompType() {
		return compType;
	}

	public void setCompType(Integer compType) {
		this.compType = compType;
	}
	
	public Long getApplyState() {
		return applyState;
	}

	public void setApplyState(Long applyState) {
		this.applyState = applyState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	@Length(min=0, max=64, message="操作人长度不能超过 64 个字符")
	public String getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(String operationUserId) {
		this.operationUserId = operationUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperationData() {
		return operationData;
	}

	public void setOperationData(Date operationData) {
		this.operationData = operationData;
	}
	
	@Length(min=0, max=500, message="企业简介附件长度不能超过 500 个字符")
	public String getCompProfileAttachment() {
		return compProfileAttachment;
	}

	public void setCompProfileAttachment(String compProfileAttachment) {
		this.compProfileAttachment = compProfileAttachment;
	}
	
	@Length(min=0, max=500, message="企业章程附件  核心企业上传长度不能超过 500 个字符")
	public String getArticlesAssociation() {
		return articlesAssociation;
	}

	public void setArticlesAssociation(String articlesAssociation) {
		this.articlesAssociation = articlesAssociation;
	}
	
	@Length(min=0, max=500, message="验资报告附件  核心企业上传长度不能超过 500 个字符")
	public String getCapitalVerificationReport() {
		return capitalVerificationReport;
	}

	public void setCapitalVerificationReport(String capitalVerificationReport) {
		this.capitalVerificationReport = capitalVerificationReport;
	}
	
}