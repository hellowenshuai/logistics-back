package com.ansel.bean;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author chenshuai
 * @version 1.0
 * @description 4.5 货运回执单详表
 * @date 2019/3/17 0017 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "cargoreceiptdetail")
public class CargoReceiptDetail {

    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "assigned")
    @Column(length = 50)
    private String goodsRevertBillId;

    private String goodsBillDetailId;

    private int pieceAmount;

    private double weight;

    private double volume;

    @Column(length = 50, nullable = false)
    private String priceMode;

    @Column(length = 50, nullable = false)
    private String priceStandard;

    private double goodsValue;


}
