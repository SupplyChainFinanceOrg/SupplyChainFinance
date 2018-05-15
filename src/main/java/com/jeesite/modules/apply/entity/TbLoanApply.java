/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.apply.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_loan_applyEntity
 * @author z
 * @version 2018-05-12
 */
@Table(name="tb_loan_apply", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="comp_name", attrName="compName", label="企业名称", queryType=QueryType.LIKE),
		@Column(name="comp_code", attrName="compCode", label="统一社会信用代码"),
		@Column(name="register_date", attrName="registerDate", label="注册时间"),
		@Column(name="register_address", attrName="registerAddress", label="注册地址"),
		@Column(name="register_money", attrName="registerMoney", label="注册资金"),
		@Column(name="comp_legal_person", attrName="compLegalPerson", label="企业法人"),
		@Column(name="legal_person_phone", attrName="legalPersonPhone", label="法人电话"),
		@Column(name="comp_contact", attrName="compContact", label="企业联系人"),
		@Column(name="contact_phone", attrName="contactPhone", label="联系人电话"),
		@Column(name="nature_id", attrName="natureId", label="公司性质"),
		@Column(name="industry", attrName="industry", label="所属行业"),
		@Column(name="main_business", attrName="mainBusiness", label="主营业务"),
		@Column(name="comp_profile", attrName="compProfile", label="公司简介"),
		@Column(name="specia_industry", attrName="speciaIndustry", label="是否特殊行业"),
		@Column(name="special_trade_license", attrName="specialTradeLicense", label="特殊行业许可证"),
		@Column(name="business_license", attrName="businessLicense", label="营业执照"),
		@Column(name="id_card_a", attrName="idCardA", label="法人身份证正面"),
		@Column(name="id_card_b", attrName="idCardB", label="法人身份证反面"),
		@Column(name="comp_phone", attrName="compPhone", label="公司电话"),
		@Column(name="comp_email", attrName="compEmail", label="公司邮箱"),
		@Column(name="employees_count", attrName="employeesCount", label="员工数量"),
		@Column(name="comp_id", attrName="compId", label="企业id"),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="loan_amount", attrName="loanAmount", label="借款金额"),
		@Column(name="loan_name", attrName="loanName", label="借款人姓名", queryType=QueryType.LIKE),
		@Column(name="loan_phone", attrName="loanPhone", label="借款人电话"),
		@Column(name="product_id", attrName="productId", label="借款产品id"),
		@Column(name="loan_time", attrName="loanTime", label="借款期限 关联产品类型表"),
		@Column(name="cooperation_id", attrName="cooperationId", label="核心企业合作年限 关联COO表"),
		@Column(name="loan_use", attrName="loanUse", label="借款用途"),
		@Column(name="reimbursement_souce", attrName="reimbursementSouce", label="还款来源"),
		@Column(name="comp_house", attrName="compHouse", label="企业房产"),
		@Column(name="comp_land", attrName="compLand", label="企业土地"),
		@Column(name="annual_sales", attrName="annualSales", label="年销售额"),
		@Column(name="net_profit", attrName="netProfit", label="净利润"),
		@Column(name="legal_idcard", attrName="legalIdcard", label="法人身份证号"),
		@Column(name="legal_prp_place", attrName="legalPrpPlace", label="法人户口所在地Registered permanent residence"),
		@Column(name="legal_marriage", attrName="legalMarriage", label="法人婚姻状况"),
		@Column(name="legal_house", attrName="legalHouse", label="法人房产情况"),
		@Column(name="legal_car", attrName="legalCar", label="法人车产情况"),
		@Column(name="living_situation", attrName="livingSituation", label="居住情况"),
		@Column(name="bank_responsible", attrName="bankResponsible", label="银行负债"),
		@Column(name="actual_living_address", attrName="actualLivingAddress", label="实际居住地址"),
		@Column(name="is_rpr_address", attrName="isRprAddress", label="户籍地址与实际居住地址是否一致"),
		@Column(name="rpr_address", attrName="rprAddress", label="如与实际居住地址不符 填写户籍地址"),
		@Column(name="household_contacts1", attrName="householdContacts1", label="家庭联系人1"),
		@Column(name="household_contacts2", attrName="householdContacts2", label="家庭联系人2"),
		@Column(name="household_contacts1_relationship", attrName="householdContacts1Relationship", label="家庭联系人1关系"),
		@Column(name="household_contacts2_relationship", attrName="householdContacts2Relationship", label="家庭联系人2关系"),
		@Column(name="household_contacts1_phone", attrName="householdContacts1Phone", label="家庭联系人1 电话"),
		@Column(name="household_contacts2_phone", attrName="householdContacts2Phone", label="家庭联系人2 电话"),
		@Column(name="other_contacts1", attrName="otherContacts1", label="其他联系人1"),
		@Column(name="other_contacts2", attrName="otherContacts2", label="其他联系人2"),
		@Column(name="other_contacts1_relationship", attrName="otherContacts1Relationship", label="其他联系人1关系"),
		@Column(name="other_contacts2_relationship", attrName="otherContacts2Relationship", label="其他联系人2关系"),
		@Column(name="other_contacts1_phone", attrName="otherContacts1Phone", label="其他联系人1电话"),
		@Column(name="other_contacts2_phone", attrName="otherContacts2Phone", label="其他联系人2电话"),
		@Column(name="apply_time", attrName="applyTime", label="申请时间"),
		@Column(name="apply_state", attrName="applyState", label="申请状态"),
		@Column(name="operation_user_id", attrName="operationUserId", label="操作人"),
		@Column(name="operation_time", attrName="operationTime", label="操作时间"),
		@Column(name="line_credit", attrName="lineCredit", label="授信额度"),
		@Column(name="core_comp_id", attrName="coreCompId", label="授权核心企业id"),
		@Column(name="core_sys_url", attrName="coreSysUrl", label="核心企业系统登录地址"),
		@Column(name="core_sys_username", attrName="coreSysUsername", label="核心企业系统登录用户名"),
		@Column(name="core_sys_pwd", attrName="coreSysPwd", label="核心企业系统登录密码"),
		@Column(name="actual_living_address", attrName="actualLivingAddress", label="实际居住地址"),
		@Column(name="household_contacts1", attrName="householdContacts1", label="家庭联系人1"),
		@Column(name="household_contacts2", attrName="householdContacts2", label="家庭联系人2"),
		@Column(name="household_contacts1_relationship", attrName="householdContacts1Relationship", label="家庭联系人1关系 0父母 1配偶 2子女"),
		@Column(name="household_contacts2_relationship", attrName="householdContacts2Relationship", label="家庭联系人2关系 0父母 1配偶 2子女"),
		@Column(name="household_contacts1_phone", attrName="householdContacts1Phone", label="家庭联系人1 电话"),
		@Column(name="household_contacts2_phone", attrName="householdContacts2Phone", label="家庭联系人2 电话"),
		@Column(name="other_contacts1", attrName="otherContacts1", label="其他联系人1"),
		@Column(name="other_contacts1_relationship", attrName="otherContacts1Relationship", label="其他联系人1关系 0亲属 1朋友 2同事"),
		@Column(name="other_contacts1_phone", attrName="otherContacts1Phone", label="其他联系人1电话"),
		@Column(name="other_contacts2_phone", attrName="otherContacts2Phone", label="其他联系人2电话"),
		@Column(name="contarct_code", attrName="contarctCode", label="购销合同编号（应收账款）"),
		@Column(name="contact_amount", attrName="contactAmount", label="账款金额"),
		@Column(name="urge_date_start", attrName="urgeDateStart", label="催账日期起"),
		@Column(name="urge_date_end", attrName="urgeDateEnd", label="催账日期止"),
		@Column(name="money_collecting_account", attrName="moneyCollectingAccount", label="催账日期止"),
		@Column(name="money_collecting_bank", attrName="moneyCollectingBank", label="催账日期止"),
		@Column(name="money_collecting_name", attrName="moneyCollectingName", label="催账日期止"),
		@Column(name="regCode", attrName="regCode", label="工商注册号"),
		@Column(name="orgCode", attrName="orgCode", label="组织结构代码"),
		@Column(name="taxCode", attrName="taxCode", label="税务登记号"),
		@Column(name="cardNo", attrName="cardNo", label="法人身份证号"),
	}, orderBy="a.id DESC"
)
public class TbLoanApply extends DataEntity<TbLoanApply> {
	
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
	private Integer natureId;		// 公司性质
	private String industry;		// 所属行业
	private String mainBusiness;		// 主营业务
	private String compProfile;		// 公司简介
	private Integer speciaIndustry;		// 是否特殊行业
	private String specialTradeLicense;		// 特殊行业许可证
	private String businessLicense;		// 营业执照
	private String idCardA;		// 法人身份证正面
	private String idCardB;		// 法人身份证反面
	private String compPhone;		// 公司电话
	private String compEmail;		// 公司邮箱
	private Long employeesCount;		// 员工数量
	private String compId;		// 企业id
	private String userId;		// 用户id
	private String loanAmount;		// 借款金额
	private String loanName;		// 借款人姓名
	private String loanPhone;		// 借款人电话
	private Long productId;		// 借款产品id
	private Long loanTime;		// 借款期限 关联产品类型表
	private Long cooperationId;		// 核心企业合作年限 关联COO表
	private String loanUse;		// 借款用途
	private String reimbursementSouce;		// 还款来源
	private Integer compHouse;		// 企业房产
	private Integer compLand;		// 企业土地
	private String annualSales;		// 年销售额
	private String netProfit;		// 净利润
	private String legalIdcard;		// 法人身份证号
	private String legalPrpPlace;		// 法人户口所在地Registered permanent residence
	private Integer legalMarriage;		// 法人婚姻状况
	private Integer legalHouse;		// 法人房产情况
	private Integer legalCar;		// 法人车产情况
	private Integer livingSituation;		// 居住情况
	private String bankResponsible;		// 银行负债
	private String actualLivingAddress;		// 实际居住地址
	private Integer isRprAddress;		// 户籍地址与实际居住地址是否一致
	private String rprAddress;		// 如与实际居住地址不符 填写户籍地址
	private String householdContacts1;		// 家庭联系人1
	private String householdContacts2;		// 家庭联系人2
	private Integer householdContacts1Relationship;		// 家庭联系人1关系
	private Integer householdContacts2Relationship;		// 家庭联系人2关系
	private String householdContacts1Phone;		// 家庭联系人1 电话
	private String householdContacts2Phone;		// 家庭联系人2 电话
	private String otherContacts1;		// 其他联系人1
	private String otherContacts2;		// 其他联系人2
	private Integer otherContacts1Relationship;		// 其他联系人1关系
	private Integer otherContacts2Relationship;		// 其他联系人2关系
	private String otherContacts1Phone;		// 其他联系人1电话
	private String otherContacts2Phone;		// 其他联系人2电话
	private Date applyTime;		// 申请时间
	private Long applyState;		// 申请状态
	private String operationUserId;		// 操作人
	private Date operationTime;		// 操作时间
	private String lineCredit;		// 授信额度
	private String coreCompId;		// 授权核心企业id
	private String coreSysUrl;		// 核心企业系统登录地址
	private String coreSysUsername;		// 核心企业系统登录用户名
	private String coreSysPwd;		// 核心企业系统登录密码

	private String contarctCode;
	private String contactAmount;
	private String urgeDateStart;
	private String urgeDateEnd;
	private String moneyCollectingName;
	private String moneyCollectingBank;
	private String moneyCollectingAccount;
	
	private String regCode;		// 工商注册号
	private String orgCode;		// 组织结构代码
	private String taxCode;		// 税务登记号
	private String cardNo;	  //法人身份证号
	

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

	public String getMoneyCollectingName() {
		return moneyCollectingName;
	}

	public void setMoneyCollectingName(String moneyCollectingName) {
		this.moneyCollectingName = moneyCollectingName;
	}

	public String getMoneyCollectingBank() {
		return moneyCollectingBank;
	}

	public void setMoneyCollectingBank(String moneyCollectingBank) {
		this.moneyCollectingBank = moneyCollectingBank;
	}

	public String getMoneyCollectingAccount() {
		return moneyCollectingAccount;
	}

	public void setMoneyCollectingAccount(String moneyCollectingAccount) {
		this.moneyCollectingAccount = moneyCollectingAccount;
	}

	public String getUrgeDateStart() {
		return urgeDateStart;
	}

	public void setUrgeDateStart(String urgeDateStart) {
		this.urgeDateStart = urgeDateStart;
	}

	public String getUrgeDateEnd() {
		return urgeDateEnd;
	}

	public void setUrgeDateEnd(String urgeDateEnd) {
		this.urgeDateEnd = urgeDateEnd;
	}

	public TbLoanApply() {
		this(null);
	}

	public TbLoanApply(String id){
		super(id);
	}
	
	public String getContarctCode() {
		return contarctCode;
	}

	public void setContarctCode(String contarctCode) {
		this.contarctCode = contarctCode;
	}

	public String getContactAmount() {
		return contactAmount;
	}

	public void setContactAmount(String contactAmount) {
		this.contactAmount = contactAmount;
	}

	@NotBlank(message="企业名称不能为空")
	@Length(min=0, max=100, message="企业名称长度不能超过 100 个字符")
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	@NotBlank(message="统一社会信用代码不能为空")
	@Length(min=0, max=50, message="统一社会信用代码长度不能超过 50 个字符")
	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="注册时间不能为空")
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
	
	@NotBlank(message="注册资金不能为空")
	@Length(min=0, max=20, message="注册资金长度不能超过 20 个字符")
	public String getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(String registerMoney) {
		this.registerMoney = registerMoney;
	}
	
	@NotBlank(message="企业法人不能为空")
	@Length(min=0, max=20, message="企业法人长度不能超过 20 个字符")
	public String getCompLegalPerson() {
		return compLegalPerson;
	}

	public void setCompLegalPerson(String compLegalPerson) {
		this.compLegalPerson = compLegalPerson;
	}
	
	@NotBlank(message="法人电话不能为空")
	@Length(min=0, max=20, message="法人电话长度不能超过 20 个字符")
	public String getLegalPersonPhone() {
		return legalPersonPhone;
	}

	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone;
	}
	
	@NotBlank(message="企业联系人不能为空")
	@Length(min=0, max=20, message="企业联系人长度不能超过 20 个字符")
	public String getCompContact() {
		return compContact;
	}

	public void setCompContact(String compContact) {
		this.compContact = compContact;
	}
	
	@NotBlank(message="联系人电话不能为空")
	@Length(min=0, max=20, message="联系人电话长度不能超过 20 个字符")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@NotNull(message="公司性质不能为空")
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
	
	@NotNull(message="是否特殊行业不能为空")
	public Integer getSpeciaIndustry() {
		return speciaIndustry;
	}

	public void setSpeciaIndustry(Integer speciaIndustry) {
		this.speciaIndustry = speciaIndustry;
	}
	
	@NotBlank(message="特殊行业许可证不能为空")
	public String getSpecialTradeLicense() {
		return specialTradeLicense;
	}

	public void setSpecialTradeLicense(String specialTradeLicense) {
		this.specialTradeLicense = specialTradeLicense;
	}
	
	@NotBlank(message="营业执照不能为空")
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
	
	@NotBlank(message="公司电话不能为空")
	@Length(min=0, max=20, message="公司电话长度不能超过 20 个字符")
	public String getCompPhone() {
		return compPhone;
	}

	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}
	
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
	
	@Length(min=0, max=64, message="企业id长度不能超过 64 个字符")
	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotBlank(message="借款金额不能为空")
	@Length(min=0, max=20, message="借款金额长度不能超过 20 个字符")
	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	@NotBlank(message="借款人姓名不能为空")
	@Length(min=0, max=20, message="借款人姓名长度不能超过 20 个字符")
	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	
	@NotBlank(message="借款人电话不能为空")
	@Length(min=0, max=20, message="借款人电话长度不能超过 20 个字符")
	public String getLoanPhone() {
		return loanPhone;
	}

	public void setLoanPhone(String loanPhone) {
		this.loanPhone = loanPhone;
	}
	
	@NotNull(message="借款产品id不能为空")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@NotNull(message="借款期限不能为空")
	public Long getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Long loanTime) {
		this.loanTime = loanTime;
	}
	
	@NotNull(message="核心企业合作年限不能为空")
	public Long getCooperationId() {
		return cooperationId;
	}

	public void setCooperationId(Long cooperationId) {
		this.cooperationId = cooperationId;
	}
	
	@Length(min=0, max=2000, message="借款用途长度不能超过 2000 个字符")
	public String getLoanUse() {
		return loanUse;
	}

	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}
	
	@Length(min=0, max=1000, message="还款来源长度不能超过 1000 个字符")
	public String getReimbursementSouce() {
		return reimbursementSouce;
	}

	public void setReimbursementSouce(String reimbursementSouce) {
		this.reimbursementSouce = reimbursementSouce;
	}
	
	@NotNull(message="企业房产不能为空")
	public Integer getCompHouse() {
		return compHouse;
	}

	public void setCompHouse(Integer compHouse) {
		this.compHouse = compHouse;
	}
	
	@NotNull(message="企业土地不能为空")
	public Integer getCompLand() {
		return compLand;
	}

	public void setCompLand(Integer compLand) {
		this.compLand = compLand;
	}
	
	@Length(min=0, max=20, message="年销售额长度不能超过 20 个字符")
	public String getAnnualSales() {
		return annualSales;
	}

	public void setAnnualSales(String annualSales) {
		this.annualSales = annualSales;
	}
	
	@Length(min=0, max=20, message="净利润长度不能超过 20 个字符")
	public String getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(String netProfit) {
		this.netProfit = netProfit;
	}
	
	@NotBlank(message="法人身份证号不能为空")
	@Length(min=0, max=20, message="法人身份证号长度不能超过 20 个字符")
	public String getLegalIdcard() {
		return legalIdcard;
	}

	public void setLegalIdcard(String legalIdcard) {
		this.legalIdcard = legalIdcard;
	}
	
	@Length(min=0, max=20, message="法人户口所在地不能超过 20 个字符")
	public String getLegalPrpPlace() {
		return legalPrpPlace;
	}

	public void setLegalPrpPlace(String legalPrpPlace) {
		this.legalPrpPlace = legalPrpPlace;
	}
	
	@NotNull(message="法人婚姻状况不能为空")
	public Integer getLegalMarriage() {
		return legalMarriage;
	}

	public void setLegalMarriage(Integer legalMarriage) {
		this.legalMarriage = legalMarriage;
	}
	
	@NotNull(message="法人房产情况不能为空")
	public Integer getLegalHouse() {
		return legalHouse;
	}

	public void setLegalHouse(Integer legalHouse) {
		this.legalHouse = legalHouse;
	}
	
	@NotNull(message="法人车产情况不能为空")
	public Integer getLegalCar() {
		return legalCar;
	}

	public void setLegalCar(Integer legalCar) {
		this.legalCar = legalCar;
	}
	
	@NotNull(message="居住情况不能为空")
	public Integer getLivingSituation() {
		return livingSituation;
	}

	public void setLivingSituation(Integer livingSituation) {
		this.livingSituation = livingSituation;
	}
	
	@Length(min=0, max=20, message="银行负债长度不能超过 20 个字符")
	public String getBankResponsible() {
		return bankResponsible;
	}

	public void setBankResponsible(String bankResponsible) {
		this.bankResponsible = bankResponsible;
	}
	
	@Length(min=0, max=500, message="实际居住地址长度不能超过 500 个字符")
	public String getActualLivingAddress() {
		return actualLivingAddress;
	}

	public void setActualLivingAddress(String actualLivingAddress) {
		this.actualLivingAddress = actualLivingAddress;
	}
	
	@NotNull(message="户籍地址与实际居住地址是否一致不能为空")
	public Integer getIsRprAddress() {
		return isRprAddress;
	}

	public void setIsRprAddress(Integer isRprAddress) {
		this.isRprAddress = isRprAddress;
	}
	
	@Length(min=0, max=500, message="如与实际居住地址不符 填写户籍地址长度不能超过 500 个字符")
	public String getRprAddress() {
		return rprAddress;
	}

	public void setRprAddress(String rprAddress) {
		this.rprAddress = rprAddress;
	}
	
	@NotBlank(message="家庭联系人1不能为空")
	@Length(min=0, max=20, message="家庭联系人1长度不能超过 20 个字符")
	public String getHouseholdContacts1() {
		return householdContacts1;
	}

	public void setHouseholdContacts1(String householdContacts1) {
		this.householdContacts1 = householdContacts1;
	}
	
	@Length(min=0, max=20, message="家庭联系人2长度不能超过 20 个字符")
	public String getHouseholdContacts2() {
		return householdContacts2;
	}

	public void setHouseholdContacts2(String householdContacts2) {
		this.householdContacts2 = householdContacts2;
	}
	
	@NotNull(message="家庭联系人1关系不能为空")
	public Integer getHouseholdContacts1Relationship() {
		return householdContacts1Relationship;
	}

	public void setHouseholdContacts1Relationship(Integer householdContacts1Relationship) {
		this.householdContacts1Relationship = householdContacts1Relationship;
	}
	
	public Integer getHouseholdContacts2Relationship() {
		return householdContacts2Relationship;
	}

	public void setHouseholdContacts2Relationship(Integer householdContacts2Relationship) {
		this.householdContacts2Relationship = householdContacts2Relationship;
	}
	
	@NotBlank(message="家庭联系人1 电话不能为空")
	@Length(min=0, max=20, message="家庭联系人1 电话长度不能超过 20 个字符")
	public String getHouseholdContacts1Phone() {
		return householdContacts1Phone;
	}

	public void setHouseholdContacts1Phone(String householdContacts1Phone) {
		this.householdContacts1Phone = householdContacts1Phone;
	}
	
	@Length(min=0, max=20, message="家庭联系人2 电话长度不能超过 20 个字符")
	public String getHouseholdContacts2Phone() {
		return householdContacts2Phone;
	}

	public void setHouseholdContacts2Phone(String householdContacts2Phone) {
		this.householdContacts2Phone = householdContacts2Phone;
	}
	
	@NotBlank(message="其他联系人1不能为空")
	@Length(min=0, max=20, message="其他联系人1长度不能超过 20 个字符")
	public String getOtherContacts1() {
		return otherContacts1;
	}

	public void setOtherContacts1(String otherContacts1) {
		this.otherContacts1 = otherContacts1;
	}
	
	@Length(min=0, max=20, message="其他联系人2长度不能超过 20 个字符")
	public String getOtherContacts2() {
		return otherContacts2;
	}

	public void setOtherContacts2(String otherContacts2) {
		this.otherContacts2 = otherContacts2;
	}
	
	@NotNull(message="其他联系人1关系不能为空")
	public Integer getOtherContacts1Relationship() {
		return otherContacts1Relationship;
	}

	public void setOtherContacts1Relationship(Integer otherContacts1Relationship) {
		this.otherContacts1Relationship = otherContacts1Relationship;
	}
	
	public Integer getOtherContacts2Relationship() {
		return otherContacts2Relationship;
	}

	public void setOtherContacts2Relationship(Integer otherContacts2Relationship) {
		this.otherContacts2Relationship = otherContacts2Relationship;
	}
	
	@NotBlank(message="其他联系人1电话不能为空")
	@Length(min=0, max=20, message="其他联系人1电话长度不能超过 20 个字符")
	public String getOtherContacts1Phone() {
		return otherContacts1Phone;
	}

	public void setOtherContacts1Phone(String otherContacts1Phone) {
		this.otherContacts1Phone = otherContacts1Phone;
	}
	
	@Length(min=0, max=20, message="其他联系人2电话长度不能超过 20 个字符")
	public String getOtherContacts2Phone() {
		return otherContacts2Phone;
	}

	public void setOtherContacts2Phone(String otherContacts2Phone) {
		this.otherContacts2Phone = otherContacts2Phone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Long getApplyState() {
		return applyState;
	}

	public void setApplyState(Long applyState) {
		this.applyState = applyState;
	}
	
	@Length(min=0, max=64, message="操作人长度不能超过 64 个字符")
	public String getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(String operationUserId) {
		this.operationUserId = operationUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	@Length(min=0, max=20, message="授信额度长度不能超过 20 个字符")
	public String getLineCredit() {
		return lineCredit;
	}

	public void setLineCredit(String lineCredit) {
		this.lineCredit = lineCredit;
	}
	
	@Length(min=0, max=64, message="授权核心企业id长度不能超过 64 个字符")
	public String getCoreCompId() {
		return coreCompId;
	}

	public void setCoreCompId(String coreCompId) {
		this.coreCompId = coreCompId;
	}
	
	@NotBlank(message="核心企业系统登录地址不能为空")
	@Length(min=0, max=200, message="核心企业系统登录地址长度不能超过 200 个字符")
	public String getCoreSysUrl() {
		return coreSysUrl;
	}

	public void setCoreSysUrl(String coreSysUrl) {
		this.coreSysUrl = coreSysUrl;
	}
	
	@NotBlank(message="核心企业系统登录用户名不能为空")
	@Length(min=0, max=100, message="核心企业系统登录用户名长度不能超过 100 个字符")
	public String getCoreSysUsername() {
		return coreSysUsername;
	}

	public void setCoreSysUsername(String coreSysUsername) {
		this.coreSysUsername = coreSysUsername;
	}
	
	@NotBlank(message="核心企业系统登录密码不能为空")
	@Length(min=0, max=50, message="核心企业系统登录密码长度不能超过 50 个字符")
	public String getCoreSysPwd() {
		return coreSysPwd;
	}

	public void setCoreSysPwd(String coreSysPwd) {
		this.coreSysPwd = coreSysPwd;
	}
	
	public Date getRegisterDate_gte() {
		return sqlMap.getWhere().getValue("register_date", QueryType.GTE);
	}

	public void setRegisterDate_gte(Date registerDate) {
		sqlMap.getWhere().and("register_date", QueryType.GTE, registerDate);
	}
	
	public Date getRegisterDate_lte() {
		return sqlMap.getWhere().getValue("register_date", QueryType.LTE);
	}

	public void setRegisterDate_lte(Date registerDate) {
		sqlMap.getWhere().and("register_date", QueryType.LTE, registerDate);
	}
	
	public Date getApplyTime_gte() {
		return sqlMap.getWhere().getValue("apply_time", QueryType.GTE);
	}

	public void setApplyTime_gte(Date applyTime) {
		sqlMap.getWhere().and("apply_time", QueryType.GTE, applyTime);
	}
	
	public Date getApplyTime_lte() {
		return sqlMap.getWhere().getValue("apply_time", QueryType.LTE);
	}

	public void setApplyTime_lte(Date applyTime) {
		sqlMap.getWhere().and("apply_time", QueryType.LTE, applyTime);
	}
	
	public Date getOperationTime_gte() {
		return sqlMap.getWhere().getValue("operation_time", QueryType.GTE);
	}

	public void setOperationTime_gte(Date operationTime) {
		sqlMap.getWhere().and("operation_time", QueryType.GTE, operationTime);
	}
	
	public Date getOperationTime_lte() {
		return sqlMap.getWhere().getValue("operation_time", QueryType.LTE);
	}

	public void setOperationTime_lte(Date operationTime) {
		sqlMap.getWhere().and("operation_time", QueryType.LTE, operationTime);
	}
	
}
