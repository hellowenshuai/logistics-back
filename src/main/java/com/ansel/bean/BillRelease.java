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
 * @description 4.1 单据分发表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "billrelease")
public class BillRelease {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String billType;

	@Column(length = 50)
	private String billCode;

	@Column(length = 50)
	private String receiveBillPerson;

	@Column(length = 50)
	private String acceptStation;

	@Column
	private Date receiveBillTime;

	@Column(length = 50)
	private String releasePerson;


}
