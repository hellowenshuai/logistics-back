package com.ansel.bean;

import lombok.*;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 4.7 客户回执信息表
 * 
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "customerreceiptinfo")
public class CustomerReceiptInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String goodsBillCode;
	
	@Column(length = 50)
	private String customer;
	
	@Column(length = 50)
	private String checkGoodsRecord;
	
	@Column(length = 50)
	private String receiveGoodsPerson;
	
	@Column
	private Date receiveGoodsDate;
	
	private int carryBillEventId;


}
