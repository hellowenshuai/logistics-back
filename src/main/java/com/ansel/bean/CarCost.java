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
 * @description 4.42 打印车辆成本
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "carcost")
public class CarCost {

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String driverCode;// 司机名称
	
	@Column(length = 50)
	private String carNo;// 车号
	
	@Column(length = 50)
	private String carType;// 车型
	
	@Column
	private double allowCarryWeight;// 准载重量
	
	@Column
	private String carWidth;// 车厢宽度
	
	@Column(length = 50)
	private String goodsHeight;// 载物高度
	
	private double carryFeeTotal;// 承运费总计
	
	private double addCarriageTotal;// 加运费总计
	
	private double factCarriageTotal;// 实际总运量
	
	@Column(length = 50)
	private String loadStation;// 装货地点
	
	@Column(length = 50)
	private String dealGoodsStation;// 交货地点
	
	private String backBillCode;// 回执单编号
	
	private Date balanceTime;// 结算时间

	


	

}
