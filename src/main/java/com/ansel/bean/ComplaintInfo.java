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
 * @description 4.16 投诉信息表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "complaintinfo")
public class ComplaintInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String customer;

	@Column(length = 50)
	private String goodsBillCode;

	@Column(length = 50)
	private String appealContent;

	@Column(length = 50)
	private String appealDate;

	@Column
	private boolean ifHandle;

	@Column(length = 50)
	private String dealDate;

	@Column(length = 50)
	private String dealResult;

	@Column
	private boolean ifCallback;

	@Column(length = 50)
	private String callBackDate;

	@Column(length = 50)
	private String dealPerson;


}
