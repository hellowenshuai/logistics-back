package com.ansel.service.impl;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.IGoodsBillService;
import com.ansel.util.AddPeopleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author chenshuai
 * 货运单相关操作
 */
@Transactional(rollbackFor = Exception.class)
@Service(value = "goodsBillService")
public class GoodsBillServiceImpl implements IGoodsBillService {

    private static final Logger log = LoggerFactory.getLogger(GoodsBillServiceImpl.class);

    @Autowired
    private IGoodsBillDao goodsBillDao;

    @Autowired
    private IBillInfoDao billInfoDao;

    @Autowired
    private IGoodsBillEventDao goodsBillEventDao;

    @Autowired
    private ICargoReceiptDetailDao cargoReceiptDetailDao;

    @Autowired
    private ICallbackDao callbackDao;

    /**
     * @return java.util.Map<?   ,   ?>
     * @description 添加一个新货运单
     * @params [goodsBill]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Map<?, ?> save(GoodsBill goodsBill) {
        Map<String, String> map = new HashMap<>(16);
        try {
            String goodsBillCode = "HY";
            while (true) {
                goodsBillCode += AddPeopleUtils.randomCode();
                if (goodsBillDao.findByGoodsBillCode(goodsBillCode)==null) {
                    break;
                }
            }
            goodsBill.setGoodsBillCode(goodsBillCode);
            goodsBill.setValidity(false);
            goodsBill.setIfAudit(false);
            goodsBillDao.save(goodsBill);

            BillInfo billInfo = new BillInfo();
            billInfo.setBillType("货运单");
            billInfo.setBillCode(goodsBillCode);
            billInfo.setBillState("已填");
            billInfo.setWriteDate(new Date());
            billInfoDao.save(billInfo);

            GoodsBillEvent goodsBillEvent = new GoodsBillEvent();
            goodsBillEvent.setGoodsBillId(goodsBillCode);
            goodsBillEvent.setEventName("待发");
            goodsBillEvent.setRemark("单据已填");
            goodsBillEvent.setOccurTime(new Date());
            goodsBillEventDao.save(goodsBillEvent);
            map.put("goodsBillCode", goodsBillCode);
            map.put("status", "SUCCESS");
            return map;
        } catch (Exception e) {
            log.error("货运单 | 单据 | 货运单事件表 插入失败！" + e.getMessage());
            map.put("status", "ERROR");
            return map;
        }
    }

    /**
     * @return boolean
     * @description 添加货物
     * @params [goodsBillDetailId, cargoReceiptDetail]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean saveGoods(String goodsBillDetailId, CargoReceiptDetail cargoReceiptDetail) {
        try {
            String goodsRevertBillId = "HZ";
            while (true) {
                goodsRevertBillId += AddPeopleUtils.randomCode();
                if (cargoReceiptDetailDao.findByGoodsRevertBillId(goodsRevertBillId)==null) {
                    break;
                }
            }
            cargoReceiptDetail.setGoodsRevertBillId(goodsRevertBillId);
            cargoReceiptDetailDao.save(cargoReceiptDetail);

            GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillDetailId);
            goodsBill.setValidity(true);
            goodsBill.setIfAudit(true);
            goodsBillDao.save(goodsBill);
            return true;
        } catch (Exception e) {
            log.error("货物插入失败！" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.GoodsBillEvent>
     * @description 查询所有运单
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<GoodsBillEvent> selectAllGoogsBillByPage(Pageable pageable) {
        return goodsBillEventDao.findAll(pageable);
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.GoodsBillEvent>
     * @description 查询运单状态
     * @params [eventName, pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<GoodsBillEvent> selectGoodsBillByEvent(String eventName, Pageable pageable) {
        return goodsBillEventDao.findByEventName(pageable, eventName);
    }

    /**
     * @return com.ansel.bean.GoodsBill
     * @description 通过id查询单个货运单
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public GoodsBill selectByGoodsBillCode(String goodsBillCode) {
        GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillCode);
        String goodsBillCode1 = goodsBill.getGoodsBillCode();
        String[] transferStation = goodsBill.getTransferStation().split("，");

        //查询他有多少中转回告
        for (int i = 0; i < transferStation.length; i++) {
            CallbackInfo callbackInfo = callbackDao.findByGoodsBillIdAndTypeAndTransferStation(goodsBillCode1, "中转回告", transferStation[i]);
            if (callbackInfo==null) {
                goodsBill.setTransferStation(transferStation[i]);
            }
        }
        return goodsBill;
    }

    /**
     * @return boolean
     * @description 修改货运单
     * @params [goodsBill]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean update(GoodsBill goodsBill) {
        try {
            goodsBillDao.save(goodsBill);
            return true;
        } catch (Exception e) {
            log.error("货运单更新失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return boolean
     * @description 删除货运单
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean delete(String goodsBillCode) {
        GoodsBillEvent goodsBillEvent = new GoodsBillEvent();
        goodsBillEvent.setGoodsBillId(goodsBillCode);
        goodsBillEvent.setEventName("删除货运单");
        goodsBillEvent.setRemark("顾客不想发货");
        goodsBillEvent.setOccurTime(new Date());

        BillInfo billInfo = billInfoDao.findByBillCode(goodsBillCode);
        if (billInfo==null) {
            return false;
        }
        billInfo.setBillState("作废");
        billInfo.setWriteDate(new Date());

        CargoReceiptDetail cargoReceiptDetail = cargoReceiptDetailDao.findByGoodsBillDetailId(goodsBillCode);

        try {
            goodsBillEventDao.save(goodsBillEvent);
            billInfoDao.save(billInfo);
            cargoReceiptDetailDao.delete(cargoReceiptDetail);
            return true;
        } catch (Exception e) {
            log.error("货运单删除失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 查询所有没有分配司机的货运单
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> findDriverIdIsNull() {
        return goodsBillDao.findDriverIdIsNull();
    }

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 查询当前客户下的”未结“的货运单  且有提货回告的货运单
     * @params [customerCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> findWaitReceive(String customerCode) {
        List<GoodsBill> goodsBills = goodsBillDao.findWaitReceived(customerCode);
        //查询改货运单是否有提货回告，如果有，才返回此对象。没有就不返回。TODO xxx
        List<GoodsBill> result = new ArrayList<>();
        for (GoodsBill goodsBill : goodsBills) {
            String goodsBillCode = goodsBill.getGoodsBillCode();
            CallbackInfo callbackInfo = callbackDao.findByGoodsBillIdAndType(goodsBillCode, "提货回告");
            if (null!=callbackInfo) {
                result.add(goodsBill);
            }
        }
        return result;
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.GoodsBill>
     * @description 获取所有未发过 {提货 | 到货 | 中转 | 已提 | 代收} 回告的运单
     * @params [type, pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<GoodsBill> findInformGet(String type, Pageable pageable) {
        return goodsBillDao.informGet(type, pageable);
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.GoodsBill>
     * @description 获取所有已发过 {提货 | 到货 | 中转 | 已提 | 代收} 回告的运单
     * @params [type, pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<GoodsBill> findOldInform(String type, Pageable pageable) {
        return goodsBillDao.findOldCall(type, pageable);
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.GoodsBill>
     * @description 获取已提货的运单
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<GoodsBill> findAllGot(Pageable pageable) {
        return goodsBillDao.findAllGot(pageable);
    }

}
