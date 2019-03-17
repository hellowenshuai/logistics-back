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
 * @description 4.18 回告信息表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "callbackinfo")
public class CallbackInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String dialNo;

	@Column(length = 50)
	private String type;

	private String content;
	
	@Column(length = 50)
	private String goodsBillId;

	private Date writeTime;

	@Column(length = 50)
	private String writer;

	private Date finallyDialTime;

	private boolean success;

	private boolean locked;

	@Column(length = 50)
	private String billId;

	@Column(length = 50)
	private String billType;


}
