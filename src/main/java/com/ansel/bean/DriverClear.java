package com.ansel.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * 4.12 司机结算主表
 * 
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "driverclear")
public class DriverClear {

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String backBillCode;

	@Column(length = 50)
	private String driverCode;

	@Column(length = 50)
	private String balanceType;

	@Column
	private double needPayment;

	@Column
	private double carryFee;

	@Column
	private double prepayMoney;

	@Column
	private double bindInsurance;

	@Column
	private double addCarriage;

	@Column
	private double payedMoney;

	@Column
	private double balance;

	@Column
	private Date balanceTime;

	@Column
	private double dispatchServiceFee;

	@Column
	private double insurance;


}
