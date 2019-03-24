package com.ansel.dao;

import com.ansel.bean.TransferInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransferInfoDao extends JpaRepository<TransferInfo, Long> {
    // 货运单编号+中转城市，查询出中转记录
    public TransferInfo findByGoodsBillCodeAndTransferStationContaining(String goodsBillCode, String transferStation);

    public TransferInfo findByGoodsBillCode(String goodsBillCode);

}
