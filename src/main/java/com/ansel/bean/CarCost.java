package com.ansel.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * 4.42 打印车辆成本
 * 
 * @author lenovo
 *
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
	
	private double carryFeeTotal;// 承运费�?�计
	
	private double addCarriageTotal;// 加运费�?�计
	
	private double factCarriageTotal;// 实际总运�?
	
	@Column(length = 50)
	private String loadStation;// 装货地点
	
	@Column(length = 50)
	private String dealGoodsStation;// 交货地点
	
	private String backBillCode;// 回执单编�?
	
	private Date balanceTime;// 结算时间

	


	

}
