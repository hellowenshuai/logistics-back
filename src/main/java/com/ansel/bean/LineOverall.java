package com.ansel.bean;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 4.38 打印专线整体
 * 
 * @author lenovo
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "lineoverall")
public class LineOverall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String loadStation;// 装货地点

	@Column(length = 50)
	private String dealGoodsStation;// 交货地点

	private double allCarriageTotal;// 总运费�?�计

	private double insuranceTotal;// 保险费�?�计

	private int times;// 次数


}
