package com.ansel.bean;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 4.9 货运差错表
 * @author Ansel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "cargoerror")
public class CargoError {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 50)
	private String goodsRevertBillCode;
	
	@Column(length = 50)
	private String goodsBillCode;
	
	@Column(length = 50)
	private String customer;
	
	@Column(length = 50)
	private String goodsName;
	
	@Column(length = 50)
	private String mistakeType;
	
	@Column
	private int pieceAmount;
	
	@Column(length = 50)
	private String size;
	
	@Column
	private double goodsValue;


}
