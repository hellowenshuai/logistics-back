package com.ansel.bean;

import lombok.*;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 4.15 杂费结算表
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "extraclear")
public class ExtraClear {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 50)
	private String subjectName;
	
	@Column
	private double balanceMoney;
	
	@Column(length = 50)
	private String remark;
	
	@Column
	private Date balanceDate;
	
	@Column(length = 50)
	private String balanceType;


}
