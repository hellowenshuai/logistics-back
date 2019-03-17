package com.ansel.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.25 客户基本信息表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "customerinfo")
public class CustomerInfo {
	
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String customerCode;

	@Column(length = 50)
	private String customer;

	@Column(length = 50)
	private String phone;

	@Column(length = 50)
	private String fax;

	@Column(length = 50)
	private String address;

	@Column(length = 50)
	private String postCode;

	@Column(length = 50)
	private String linkman;

	@Column(length = 50)
	private String linkmanMobile;

	@Column(length = 50)
	private String customerType;

	@Column(length = 50)
	private String enterpriseProperty;

	@Column(length = 50)
	private String enterpriseSize;

	@Column(length = 50)
	private String email;


}
