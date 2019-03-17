package com.ansel.bean;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

/**
 * 4.8 货物回执信息表
 *
 * @author Ansel
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "goodsreceiptinfo")
public class GoodsReceiptInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String goodsRevertCode;

    @Column(length = 50)
    private String driverName;

    @Column(length = 50)
    private String checkGoodsRecord;

    @Column(length = 50)
    private String receiveGoodsPerson;

    @Column(length = 50)
    private Date rceiveGoodsDate;


}
