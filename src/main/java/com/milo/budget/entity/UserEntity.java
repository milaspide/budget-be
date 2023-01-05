package com.milo.budget.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "budget")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -321885656958921997L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SURNAME")
	private String surname;

	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "BANK_ACCOUNT")
	private BigDecimal bankAccount;

	@Column(name = "FIXED_SAVING")
	private BigDecimal fixedSaving;

	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "LAST_LOGIN")
	private Timestamp lastLogin;
	
	@Column(name = "remaining_budget")
	private BigDecimal remainingBudget;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	SalaryEntity salary;
	
	@OneToMany(mappedBy = "user", targetEntity = CasualExpenseEntity.class)
	List<CasualExpenseEntity> casualExpenses;
	
	@OneToMany(mappedBy = "user", targetEntity = FixedExpenseEntity.class)
	List<FixedExpenseEntity> fixedExpenses;

}
