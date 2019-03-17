package com.ansel.service.impl;

import com.ansel.bean.BillInfo;
import com.ansel.bean.BillRelease;
import com.ansel.bean.GoodsReceiptInfo;
import com.ansel.dao.*;
import com.ansel.service.IBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Administrator
 */
@Service(value = "billService")
@Transactional(rollbackFor = Exception.class)
public class BillServiceImpl implements IBillService {

    private static final Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private IBillInfoDao billInfoDao;

    @Autowired
    private IBillReleaseDao billReleaseDao;

    @Autowired
    private IGoodsBillEventDao goodsBillEventDao;

    @Autowired
    private IGoodsBillDao goodsBillDao;

    @Autowired
    private ICargoReceiptDao cargoReceiptDao;

    @Autowired
    private ICargoReceiptDetailDao cargoReceiptDetailDao;

    @Autowired
    private IGoodsReceiptInfoDao goodsReceiptInfoDao;

    @Override
    public Page<BillInfo> findAllByPage(Pageable pageable) {
        return billInfoDao.findAll(pageable);
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.BillInfo>
     * @description 获取没有分派的货运单
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/12 0012
     */
    @Override
    public Page<BillInfo> findNotRelease(Pageable pageable) {

        return billInfoDao.findNotRelease(pageable);
    }

    /**
     * @return boolean
     * @description 分发 - 将一个单子分配给某个司机
     * @params [billRelease]
     * @creator chenshuai
     * @date 2019/3/12 0012
     */
    @Override
    public boolean addRelease(BillRelease billRelease) {
        billRelease.setBillType("货运单");
        /**获取货运单的单号*/
        String billCode = billRelease.getBillCode();
        try {
            /** 1.保持在已分派的货运单中*/
            billReleaseDao.save(billRelease);
            /** 2.改货运单事件状态*/
            goodsBillEventDao.updateEventName("未到", new Date(), billCode);
            //获取到运输单 单号
            String goodsRevertBillId = cargoReceiptDetailDao.findByGoodsBillDetailId(billCode).getGoodsRevertBillId();
            /** 3.修改运输单状态*/
            cargoReceiptDao.updateRelease(billRelease.getReceiveBillTime(), billRelease.getReceiveBillPerson(), "未到车辆", goodsRevertBillId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("单据明细表插入失败 | 货运单 & 货运回执信息 更新失败");
            return false;

        }
    }

    /**
     * @return boolean
     * @description 到货 - 添加一条货物到货回执信息
     * @params [goodsReceiptInfo]
     * @creator chenshuai
     * @date 2019/3/12 0012
     */
    @Override
    public boolean addGoodsReceipt(GoodsReceiptInfo goodsReceiptInfo) {

        String goodsRevertBillId = goodsReceiptInfo.getGoodsRevertCode();
        //获取到运输单的id
        String billId = cargoReceiptDetailDao.findByGoodsRevertBillId(goodsRevertBillId).getGoodsBillDetailId();
        try {
            goodsReceiptInfoDao.save(goodsReceiptInfo);
            //修改接货单状态 未结
            goodsBillEventDao.updateEventName("未结", new Date(), billId);
            //修改到货日期
            goodsBillDao.updateFactDealDate(goodsReceiptInfo.getRceiveGoodsDate(), billId);
            //修改运输单状态 为 未结合同
            cargoReceiptDao.updateArriveTime(goodsReceiptInfo.getRceiveGoodsDate(), "未结合同", goodsRevertBillId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("货物回执信息添加失败");
            return false;
        }
    }

}
