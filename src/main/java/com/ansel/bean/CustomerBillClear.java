package com.ansel.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * 4.13 客户运单结算表
 * 
 * @author Chenss
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "customerbillclear")
public class CustomerBillClear {
	@Column
	private String CustomerCode;
	
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String goodsBillCode;

	@Column
	private double billMoney;

	@Column
	private double moneyReceivable;

	@Column
	private double receivedMoney;

	@Column
	private double prepayMoney;

	@Column
	private double carriageReduceFund;

	@Column
	private double balance;

	@Column
	private Date balanceTime;

	@Column
	private double insurance;

	@Column
	private double payKickback;

	@Column
	private double carryGoodsFee;

	@Column(length = 50)
	private String balanceType;



}
