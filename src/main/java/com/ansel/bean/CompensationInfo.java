package com.ansel.bean;

import lombok.*;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.24 赔偿信息表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "compensationinfo")
public class CompensationInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double amends;

	@Column(length = 50)
	private String customer;

	private Date amendsTime;

	@Column(length = 255)
	private String remark;

	private int receiveStationId;

	private double badDestroyGoods;

	@Column(length = 50)
	private String receiveStationName;

	private Date writeDate;


}
