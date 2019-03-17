package com.ansel.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * 4.40 打印司机运量
 * 
 * @author lenovo
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "driveramount")
public class DriverAmount {

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length = 50)
	private String driverCode;// 

	private double carryFeeTotal;// 承运费�?�计
	
	private double addCarriageTotal;// 加运费�?�计
	
	private double total;// 总计


	

}
