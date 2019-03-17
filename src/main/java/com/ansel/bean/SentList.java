package com.ansel.bean;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.39 装货发车清单
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "sentlist")
public class SentList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;// 序号
	
	private int userId;// 用户ID

	@Column(length = 50)
	private String sendGoodsCustomer;// 发货客户
	
	@Column(length = 50)
	private String goodsName;// 货物名称
	
	@Column(length = 50)
	private String goodsCode;// 货物编号
	
	@Column(length = 50)
	private String transferDestination;// 中转目的�?
	
	@Column(length = 50)
	private String sendGoodsCustomerTel;// 发货客户电话
	
	private double cashPay;// 现付
	
	private double pickupPay;// 提付
	
	private double backCost;// 回结
	
	@Column(length = 50)
	private String helpAcceptFund;// 代收�?
	
	@Column(length = 50)
	private String remark;// 备注
	
	@Column(length = 50)
	private String driverName;// 司机名称
	
	@Column(length = 50)
	private String carCardNo;// 车牌�?
	
	@Column(length = 50)
	private String mobile;// 手机
	
	private int pieceAmount;// 件数
	
	@Column(length = 50)
	private String goodsRevertBillCode;// 货运回执单编�?


}
