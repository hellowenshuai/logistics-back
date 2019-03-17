package com.ansel.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * 4.27 司机信息表
 * 
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "driverinfo")
public class DriverInfo {
	
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String id;

	@Column(length = 50)
	private String driverName;

	@Column(length = 50)
	private String idCard;

	@Column(length = 50)
	private String phone;

	@Column(length = 50)
	private String gender;

	@Column(length = 50)
	private Date birthday;

	@Column(length = 50)
	private String state;

	@Column(length = 50)
	private String carNo;

	@Column(length = 50)
	private double allowCarryVolume;

	@Column(length = 50)
	private double allowCarryWeight;

	@Column(length = 50)
	private String address;

	@Column(length = 50)
	private String carType;

	@Column(length = 50)
	private String carLength;

	@Column(length = 50)
	private String carDept;

	@Column(length = 50)
	private String carDeptTel;

	@Column(length = 50)
	private String driveLicence;

	@Column(length = 50)
	private String runLicence;

	@Column(length = 50)
	private String bizLicence;

	@Column(length = 50)
	private String insuranceCard;

	@Column(length = 50)
	private String carWidth;

	@Column(length = 50)
	private String goodsHeight;

	@Column(length = 50)
	private String carFrameNo;
	
	@Column(length = 50)
	private String engineNo;

	@Column(length = 50)
	private String remark;

	private boolean companyCar;


}