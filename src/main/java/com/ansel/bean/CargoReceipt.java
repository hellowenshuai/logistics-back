package com.ansel.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.6 货运回执单主表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "cargoreceipt")
public class CargoReceipt {

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String goodsRevertBillCode;

	@Column(length = 50)
	private String loadStation;

	@Column
	private Date startCarryTime;

	@Column(length = 50)
	private String dealGoodsStation;

	@Column
	private Date arriveTime;

	@Column(length = 50)
	private String receiveGoodsLinkman;

	@Column(length = 50)
	private String linkmanPhone;

	@Column(length = 50)
	private String receiveGoodsDetailAddr;

	@Column(length = 50)
	private String carriageBanlanceMode;

	@Column(length = 50)
	private String remark;

	@Column(length = 50)
	private String driverId;

	@Column(length = 50)
	private String ifBalance;

	@Column(length = 50)
	private String backBillState;

	private double startAdvance;

	private double carryGoodsBillDeposit;

	private double carryGoodsInsurance;

	private double dispatchServiceFee;

	private double allCarriage;

	private double insurance;

	private Date signTime;

	@Column(length = 50)
	private String acceptStation;

	@Column(length = 50)
	private String carriageMode;


}
