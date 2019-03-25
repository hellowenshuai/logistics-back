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

import java.util.ArrayList;
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
     * @description 查询一个司机的所有运单(中转 | 到货) 未到车辆
     * @params [type, driverId]
     * @creator chenshuai
     * @date 2019/3/19 0019
     */
    @Override
    public List<GoodsBill> transferGoods(String type, String driverId) {
        // TODO 1.待修改
        List<GoodsBill> list = goodsBillDao.transferState(type, driverId);
        List<GoodsBill> result = new LinkedList<>();
        //判断中转情况
        for (GoodsBill goodsBillx : list) {
            GoodsBill goodsBill = null;
            try {
                goodsBill = (GoodsBill)goodsBillx.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            //如果这个货运单没有中转城市，直接跳出循环
            String transferStation = goodsBill.getTransferStation();
            if ("".equals(transferStation.trim())) {
                continue;
            }
            String[] citys = goodsBill.getTransferStation().split("，");
            /**
             * 货运单编号+中转城市，查询出中转记录，说明在数据库中查询不到中转记录，就将数据返回给前端*/
            //如果有多个中转城市，此时以第一个为中转城市
            for (int i = 0; i < citys.length; i++) {
                if (transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBill.getGoodsBillCode(), citys[i])==null) {
                    goodsBill.setTransferStation(citys[i]);
                    goodsBill.setGoodsBillCode(goodsBill.getGoodsBillCode());
                    result.add(goodsBill);
                    GoodsBill goodsBill1 = new GoodsBill();
                    goodsBill1.setGoodsBillCode(goodsBill.getGoodsBillCode());
                    goodsBill1.setTransferStation(goodsBill.getTransferStation());
                    goodsBill1.setRemark(goodsBill.getRemark());
                    log.info("goodsBill1:" + goodsBill1);
                }
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
        if (goodsBill==null) {
            return null;
        }
        String transferStation = goodsBill.getTransferStation().trim();

        String[] cityArray = transferStation.split("，");
        TransferComInfo transferComInfo = new TransferComInfo();
        //TODO 待修改
        for (String city : cityArray) {
            if (transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBillCode, city)==null) {
                transferComInfo = transferComInfoDao.findByCity(city);
                break;
            }
        }
        return transferComInfo;
    }

    /**
     * @return boolean
     * @description 添加中转信息, 之后，到货管理中才可以看到此运单
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
     * @description 查询一个司机的所有 未到车辆 货运单
     * @params [type, driverId]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> arriveGoods(String type, String driverId) {

        List<GoodsBill> list = goodsBillDao.transferState(type, driverId);
        List<GoodsBill> result = new LinkedList<>();
        //判断中转情况
        for (int i = 0; i < list.size(); i++) {
            String transferStation1 = list.get(i).getTransferStation().trim();
            if ("".equals(transferStation1)) {
                result.add(list.get(i));
                continue;
            }
            // TODO 待修改
            List<TransferInfo> transferInfoList = new ArrayList<>();
            String[] cityArray = transferStation1.split("，");
            for (int j = 0; j < cityArray.length; j++) {
                String transferStation = "%" + cityArray[0] + "%";
                String goodsBillCode = list.get(i).getGoodsBillCode();
                log.info("transferStation:" + transferStation);
                TransferInfo transferInfo = transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBillCode, transferStation);
                if (transferInfo!=null) {
                    transferInfoList.add(transferInfo);
                }
            }
            if (transferInfoList.size()==cityArray.length) {
                result.add(list.get(i));
            }
            log.info("transferInfoList:" + transferInfoList.toString());
        }
        log.info("result:", result.toArray().toString());
        return result;
    }

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 具有中转回告的所有货运单
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> findOnWayBills() {
// TODO 待修改
        List<GoodsBill> list = goodsBillDao.findOnWayBills();
        List<GoodsBill> result = new LinkedList<>();
        for (GoodsBill goodsBill : list) {
            //查询司机 1.有中转信息而 2.无回告的 3.中转城市不为空的 哪些运单
            TransferInfo transferInfo = transferInfoDao.findByGoodsBillCode(goodsBill.getGoodsBillCode());
            CallbackInfo callbackInfo = callbackDao.findByGoodsBillIdAndType(goodsBill.getGoodsBillCode(), "中转回告");
            if (transferInfo!=null && callbackInfo==null && !"".equals(goodsBill.getTransferStation())) {
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

    /**
     * @return com.ansel.bean.TransferComInfo
     * @description 查询运单的中转详情 中转城市+运单号
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public TransferComInfo findByGoodsBillCodeAndTransferStation(String goodsBillCode, String transferStation) {

        TransferComInfo transferComInfo = new TransferComInfo();
        //2. TODO 待修改
        if (transferInfoDao.findByGoodsBillCodeAndTransferStationContaining(goodsBillCode, transferStation)==null) {
            transferComInfo = transferComInfoDao.findByCity(transferStation);
        }
        return transferComInfo;
    }
}
