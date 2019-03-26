package com.ansel.bean;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.*;
import java.sql.Date;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.3 货运单主表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "goodsbill")
public class GoodsBill implements Serializable, Cloneable {

    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "assigned")
    @Column(length = 50)
    private String goodsBillCode;

    @Column(length = 50)
    private String sendGoodsCustomerNo;

    @Column(length = 50)
    private String sendGoodsCustomer;

    @Column(length = 50)
    private String sendGoodsCustomerTel;

    @Column(length = 50)
    private String sendGoodsCustomerAddr;

    @Column(length = 50)
    private String receiveGoodsCustomerCode;

    @Column(length = 50)
    private String receiveGoodsCustomer;

    @Column(length = 50)
    private String receiveGoodsCustomerTel;

    @Column(length = 50)
    private String receiveGoodsCustomerAddr;

    @Column
    private Date sendGoodsDate;

    @Column(length = 50)
    private String sendGoodsAddr;

    @Column(length = 50)
    private String receiveGoodsAddr;

    @Column
    private Date predeliveryDate;

    @Column
    private Date factDealDate;

    @Column
    private double helpAcceptPayment;

    @Column(length = 50)
    private String acceptProcedureRate;

    @Column(length = 50)
    private String payMode;

    @Column(length = 50)
    private String fetchGoodsMode;

    @Column(length = 50)
    private String writeBillPerson;

    @Column
    private Date writeDate;

    @Column(length = 50)
    private boolean validity;

    @Column(length = 50)
    private boolean ifAudit;

    @Column(length = 50)
    private boolean ifSettleAccounts;

    @Column(length = 50)
    private String transferStation;

    @Column
    private double transferFee;

    @Column
    private double reduceFund;

    @Column
    private double payKickback;

    @Column
    private double moneyOfChangePay;

    @Column
    private double carryGoodsFee;

    @Column
    private double carriage;

    @Column
    private double insurance;

    @Column(length = 50)
    private String employeeCode;

    @Column(length = 50)
    private String remark;

    @Column(length = 100)
    private String acceptStation;

    @Override
    public Object clone() throws CloneNotSupportedException {
        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
            return (oi.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
