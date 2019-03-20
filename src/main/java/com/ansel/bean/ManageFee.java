package com.ansel.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.23 管理费用表
 * @date 2019/3/17 0017 16:09
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
	@JSONField(format="yyyy-MM-dd")
	private Date writeDate;


}
