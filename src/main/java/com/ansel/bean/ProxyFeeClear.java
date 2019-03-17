package com.ansel.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * 4.14 代收货款结算表
 * 
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "proxyfeeclear")
public class ProxyFeeClear {
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String goodsBillCode;

	@Column(length = 50)
	private String customerCode;

	@Column
	private double accountReceivable;

	@Column
	private double factReceiveFund;

	@Column
	private double goodsPayChange;

	@Column(length = 15)
	private float commisionRate;

	@Column
	private double commisionReceivable;

	@Column
	private double receivedCommision;

	@Column
	private Date balanceDate;


}
