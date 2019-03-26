package com.ansel.dao;

import com.ansel.bean.CallbackInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICallbackDao extends JpaRepository<CallbackInfo, Long> {

    public CallbackInfo findByGoodsBillIdAndType(String goodsBillId, String type);

    public List<CallbackInfo> findAllByGoodsBillIdAndType(String goodsBillId, String type);

    public CallbackInfo findByGoodsBillIdAndTypeAndTransferStation(String goodsBillId, String type, String transferStation);

}
