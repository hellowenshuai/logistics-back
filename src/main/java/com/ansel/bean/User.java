package com.ansel.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.33 用户表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(strategy = "assigned", name = "id")
	@Column(length = 50)
	private String loginId;// 登录ID
	
	@Column(length = 50)
	private String password;
	
	@Column(columnDefinition = "bit(1) default 0")
	private boolean ifOnline;// 是否在线


}
