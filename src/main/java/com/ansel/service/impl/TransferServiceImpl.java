package com.ansel.service.impl;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.ITransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chenshuai
 * 中转管理
 */
@Service(value = "transferService")
@Transactional(rollbackFor = Exception.class)
public class TransferServiceImpl implements ITransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    @Autowired
    private ITransferComInfoDao transferComInfoDao;

    @Autowired
    private IGoodsBillDao goodsBillDao;

    @Autowired
    private ITransferInfoDao transferInfoDao;

    @Autowired
    private ICustomerReceiptInfoDao customerReceiptInfoDao;

    @Autowired
    private ICallbackDao callbackDao;

    /**
     * @return boolean
     * @description 添加中转公司信息
     * @params [transferComInfo]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean addCompany(TransferComInfo transferComInfo) {

        try {
            transferComInfoDao.save(transferComInfo);
            return true;
        } catch (Exception e) {
            log.error("中转公司信息添加失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.TransferComInfo>
     * @description 查询所有中转公司信息-分页
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<TransferComInfo> findAllByPage(Pageable pageable) {
        return transferComInfoDao.findAll(pageable);
    }

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 查询一个司机的所有运单(中转 | 到货)
     * @params [type, driverId]
     * @creator chenshuai
     * @date 2019/3/19 0019
     */
    @Override
    public List<GoodsBill> transferGoods(String type, String driverId) {
        //
        List<GoodsBill> list = goodsBillDao.transferState(type, driverId);
        List<GoodsBill> result = new LinkedList<>();
        //判断中转情况
        for (GoodsBill goodsBill : list) {
            String[] citys = goodsBill.getTransferStation().split("，");
            //？？
            if (transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBill.getGoodsBillCode(), citys[citys.length - 1])==null) {
                result.add(goodsBill);
            }
        }
        return result;
    }

    /**
     * @return com.ansel.bean.TransferComInfo
     * @description 查询运单的中转详情
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public TransferComInfo findByGoodsBillCode(String goodsBillCode) {

        GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillCode);
        String[] citys = goodsBill.getTransferStation().split("，");
        TransferComInfo transferComInfo = new TransferComInfo();
        for (String string : citys) {
            if (transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBillCode, string)==null) {
                transferComInfo = transferComInfoDao.findByCity(string);
                break;
            }
        }
        return transferComInfo;
    }

    /**
     * @return boolean
     * @description 添加中转信息
     * @params [transferInfo]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean addTransferInfo(TransferInfo transferInfo) {

        try {
            transferInfoDao.save(transferInfo);
            return true;
        } catch (Exception e) {
            log.error("中转信息表插入失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 查询一个司机的所有到货运单
     * @params [type, driverId]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> arriveGoods(String type, String driverId) {

        // TODO Auto-generated method stub
        List<GoodsBill> list = goodsBillDao.transferState(type, driverId);
        List<GoodsBill> result = new LinkedList<>();
        //判断中转情况
        for (int i = 0; i < list.size(); i++) {
            String[] citys = list.get(i).getTransferStation().split("，");
            String transferStation = "%" + citys[citys.length - 1] + "%";
            String goodsBillCode = list.get(i).getGoodsBillCode();
            System.out.println(transferStation);
            TransferInfo byGoodsBillCodeAndTransferStationContaining = transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBillCode, transferStation);
            System.out.println(byGoodsBillCodeAndTransferStationContaining);
            if (transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBillCode, transferStation)!=null) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 中转回告所需数据
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> findOnWayBills() {

        // TODO Auto-generated method stub
        List<GoodsBill> list = goodsBillDao.findOnWayBills();
        List<GoodsBill> result = new LinkedList<>();
        for (GoodsBill goodsBill : list) {
//			List<TransferInfo> infos = transferInfoDao.findByGoodsBillCode(goodsBill.getGoodsBillCode());
            CallbackInfo callbackInfo = callbackDao.findByGoodsBillIdAndType(goodsBill.getGoodsBillCode(), "中转回告");
//			String[] citys = goodsBill.getTransferStation().split("，");
            if (callbackInfo==null) {
                result.add(goodsBill);
            }
        }
        return result;
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.TransferInfo>
     * @description 查询所有的中转信息 货运单
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/19 0019
     */
    @Override
    public Page<TransferInfo> findInfoByPage(Pageable pageable) {
        return transferInfoDao.findAll(pageable);
    }

    /**
     * @return boolean
     * @description 添加顾客回执
     * @params [customerReceiptInfo]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean addCustomerReceiptInfo(CustomerReceiptInfo customerReceiptInfo) {

        try {
            customerReceiptInfoDao.save(customerReceiptInfo);
            return true;
        } catch (Exception e) {
            log.error("顾客回执信息添加失败" + e.getMessage());
            return false;
        }

    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.CustomerReceiptInfo>
     * @description 查询所有用户的到货回执
     * @params [customerCode, pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<CustomerReceiptInfo> findCusRecPage(String customerCode, Pageable pageable) {
        return customerReceiptInfoDao.findByReceiveGoodsPerson(customerCode, pageable);
    }

}
