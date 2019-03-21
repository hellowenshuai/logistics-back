package com.ansel.dao;

import com.ansel.bean.TransferInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransferInfoDao extends JpaRepository<TransferInfo, Long> {

    public TransferInfo findByGoodsBillCodeAndTransferStationContaining(String goodsBillCode, String transferStation);

    public List<TransferInfo> findByGoodsBillCode(String goodsBillCode);

}
