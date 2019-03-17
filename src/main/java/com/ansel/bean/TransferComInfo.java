package com.ansel.bean;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 4.11 中转公司信息表
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "transfercominfo")
public class TransferComInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 50)
	private String city;
	
	@Column(length = 50)
	private String companyName;
	
	@Column(length = 50)
	private String linkPhone;
	
	@Column(length = 200)
	private String detailAddress;


}
