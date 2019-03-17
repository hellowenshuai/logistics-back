package com.ansel.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.43 打印往来业务表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "contactsservice")
public class ContactsService {
	
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String sendGoodsCustomer;// 发货客户
	
	@Column(length = 50)
	private String goodsBillCode;// 货运单编�?
	
	@Column(length = 50)
	private String sendGoodsAddr;// 发货地址
	
	@Column(length = 50)
	private String receiveGoodsAddr;// 收货地址
	
	private double carriage;// 运费
	private double insurance;// 保险�?
	private double billMoney;// 实际总运�?
	private double moneyReceivable;// 应收金额
	private double receivedMoney;// 已收金额
	private double balance;// 余额
	private Date sendGoodsDate;// 发货日期
	

}
