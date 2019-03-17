package com.ansel.dao;

import com.ansel.bean.CargoReceiptDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICargoReceiptDetailDao extends JpaRepository<CargoReceiptDetail, Long> {
    //TODO 这里有个疑惑  返回一个对象，却用一个string来接受
    public CargoReceiptDetail findByGoodsRevertBillId(String goodsRevertBillId);

    @Query(value = "select goodsRevertBillId from cargoreceiptdetail")
    public List<String> findAllCargoReceiptCode();

    public CargoReceiptDetail findByGoodsBillDetailId(String goodsBillDetailId);

    @Query(value = "select goodsRevertBillId from cargoreceiptdetail where goodsRevertBillId not in (select goodsRevertBillCode from cargoreceipt)")
    public List<?> findLeftCodes();

}