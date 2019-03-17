package com.ansel.bean;

import lombok.*;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 4.23 管理费用表
 * 
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "managefee")
public class ManageFee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double officeFee;
	private double houseRent;
	private double waterElecFee;
	private double phoneFee;
	private double otherPayout;
	private String payoutMonth;
	private Date writeDate;


}
