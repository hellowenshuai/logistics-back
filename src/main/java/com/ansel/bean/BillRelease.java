package com.ansel.bean;

import lombok.*;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 4.1 单据分发表
 * 
 * @author Ansel
 *
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
