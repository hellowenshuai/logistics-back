package com.ansel.bean;

import lombok.*;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 4.10 中转信息表
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "transferinfo")
public class TransferInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 50)
	private String goodsBillCode;
	
	@Column(length = 50)
	private String transferStation;
	
	@Column(length = 50)
	private String transferCheck;
	
	@Column
	private Date checkTime;
	
	@Column(length = 50)
	private String description;
	
	@Column(length = 50)
	private String transferCompany;
	
	@Column(length = 50)
	private String transferAddr;
	
	@Column(length = 50)
	private String transferStationTel;
	
	@Column(length = 50)
	private double transferFee;
	
	@Column(length = 50)
	private String afterTransferBill;


}
