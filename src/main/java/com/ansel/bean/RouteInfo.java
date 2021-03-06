package com.ansel.bean;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.28 地区城市表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "routeinfo")
public class RouteInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private int startStation;
	
	@Column(length = 100)
	private String passStation;

	@Column
	private int endStation;

	private double distance;

	private double fetchTime;



}
