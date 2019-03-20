package com.ansel.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.22 财务费用表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "financefee")
public class FinanceFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double fee;
    private String payoutMonth;
    @JSONField(format="yyyy-MM-dd")
    private Date writeDate;


}
