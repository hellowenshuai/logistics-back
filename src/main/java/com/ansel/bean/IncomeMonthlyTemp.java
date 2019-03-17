package com.ansel.bean;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.45 损益月报临时统计表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "incomemonthlytemp")
public class IncomeMonthlyTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 收入
    private double income;

    // 支出
    private double payout;

    // 营业收入
    private double bizIncome;

    // 运费金额
    private double carriageMoney;

    // 保险金额
    private double insuranceMoney;


    private double unbizIncome;// 非营业收入


    private double bizFee;// 经营费用


    private double carCarriage;// 车运�?


    private double conveyWage;// 搬运工资


    private double manageFee;// 管理费用


    private double officeFee;// 办公�?


    private double houseRent;// 房租�?


    private double waterElecFee;// 水电�?


    private double phoneFee;// 电话�?


    private double other;// 其他


    private double financeFee;// 财务费用


    private double profit;// 利润

    private double wage;//工资

    private String month;//月份


}