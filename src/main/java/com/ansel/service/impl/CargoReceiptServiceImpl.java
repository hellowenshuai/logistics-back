package com.ansel.service.impl;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.ICargoReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author chenshuai
 */
@Transactional(rollbackFor = Exception.class)
@Service(value = "cargoReceiptService")
public class CargoReceiptServiceImpl implements ICargoReceiptService {

    private static final Logger log = LoggerFactory.getLogger(CargoReceiptServiceImpl.class);

    @Autowired
    private ICargoReceiptDao cargoReceiptDao;

    @Autowired
    private ICargoReceiptDetailDao cargoReceiptDetailDao;

    @Autowired
    private IBillInfoDao billInfoDao;

    @Autowired
    private IGoodsBillDao goodsBillDao;

    @Autowired
    private IGoodsBillEventDao goodsBillEventDao;

    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private IRouteInfoDao routeInfoDao;

    /**
     * @return boolean
     * @description 添加货运回执单
     * @params [cargoReceipt]
     * @creator chenshuai
     * @date 2019/3/12 0012
     */
    @Override
    public boolean save(CargoReceipt cargoReceipt) {
        log.info("回执单详情：" + cargoReceipt);
        try {
            // 保存货运回执单
            cargoReceiptDao.save(cargoReceipt);

            /**获取开始城市和目的城市*/
            String loadStation = cargoReceipt.getLoadStation();
            String dealGoodsStation = cargoReceipt.getDealGoodsStation();
            /**获取开始城市和目的城市的id*/
            int startStation = regionDao.findByCity(loadStation).getId();
            int endStation = regionDao.findByCity(dealGoodsStation).getId();

            /**获取起始地到目的地直接的城市线路信息*/ // 这个线路可以有多个
            List<RouteInfo> routeList = routeInfoDao.findByStartStationAndEndStation(startStation, endStation);
            /**获取首条线路的所有中转城市的城市id*/
            String passStation = routeList.get(0).getPassStation();
            String result = "";
            //
            if (null!=passStation && !"".equals(passStation)) {
                for (int i = 1; i < routeList.size(); i++) {
                    //获取临时城市id数组
                    String temp = routeList.get(i).getPassStation();
                    //   TODO 首条线路的中转城市id 与之后的线路的中转城市id 的长的比较，取最少中转城市的最优解
                    passStation = (temp.length() < passStation.length() ? temp : passStation);
                }
                String[] pass_station = passStation.split(",");
                //通过运输单id寻找 接货单id  TODO 无用
                String goodsBillCode = cargoReceiptDetailDao.findByGoodsRevertBillId(cargoReceipt.getGoodsRevertBillCode()).getGoodsBillDetailId();
                //通过接货单id获取 接货单详情
                GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillCode);

                //中转费用是1.3倍 *距离
                double transfer_fee = 1.3 * pass_station.length;
                goodsBill.setTransferFee(transfer_fee);

                for (int i = 0; i < pass_station.length; i++) {
                    //获取中转站城市
                    String station_name = regionDao.findById(Integer.valueOf(pass_station[i])).getCity();
                    result += (i==0 ? "" : ",");
                    result += station_name;
                }
                goodsBill.setTransferStation(result);
                // 重新保存接货单
                goodsBillDao.save(goodsBill);
            }

            // 保存单据明细表
            BillInfo billInfo = new BillInfo();
            billInfo.setBillType("货运回执单");
            billInfo.setAcceptStation(cargoReceipt.getAcceptStation());
            billInfo.setBillCode(cargoReceipt.getGoodsRevertBillCode());
            billInfo.setBillState("已填");
            billInfo.setWriteDate(new Date());
            log.info("保存单据明细表:" + billInfo.toString());
            billInfoDao.save(billInfo);
            return true;
        } catch (Exception e) {
            log.info("货运回执单添加失败:" + e.getMessage());
            return false;
        }
    }

    /**
     * @return java.util.List<java.lang.String>
     * @description 查询货运回执单编号
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<String> selectAllCodes() {
        return cargoReceiptDetailDao.findAllCargoReceiptCode();
    }

    /**
     * @return com.ansel.bean.GoodsBill
     * @description 通过货运回执单查询客户信息
     * @params [goodsRevertBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public GoodsBill selectGoodsBill(String goodsRevertBillCode) {
        String goodsBillCode = cargoReceiptDetailDao.findByGoodsRevertBillId(goodsRevertBillCode).getGoodsBillDetailId();
        return goodsBillDao.findByGoodsBillCode(goodsBillCode);
    }

    /**
     * @return java.util.List<com.ansel.bean.CargoReceipt>
     * @description 通过 id 查询司机的所有运单
     * @params [driverId]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<CargoReceipt> findByDriverId(String driverId) {
        return cargoReceiptDao.findByDriverId(driverId);
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.CargoReceipt>
     * @description 查询所有运单
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<CargoReceipt> selectAll(Pageable pageable) {
        return cargoReceiptDao.findAll(pageable);
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.CargoReceipt>
     * @description 查询运单状态
     * @params [backBillState, pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<CargoReceipt> selectByEvent(String backBillState, Pageable pageable) {
        return cargoReceiptDao.findByBackBillState(pageable, backBillState);
    }

    /**
     * @return com.ansel.bean.CargoReceipt
     * @description 通过id查询单个货运单
     * @params [goodsRevertBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public CargoReceipt selectByCode(String goodsRevertBillCode) {
        return cargoReceiptDao.findByGoodsRevertBillCode(goodsRevertBillCode);
    }

    /**
     * @return boolean
     * @description 修改货运回执单
     * @params [cargoReceipt]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean update(CargoReceipt cargoReceipt) {
        try {
            cargoReceiptDao.save(cargoReceipt);
            return true;
        } catch (Exception e) {
            log.error("货运回执单更新失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return boolean
     * @description 发货操作，
     * 1.修改运输单状态为 “未到车辆”
     * 2.修改货运单事件状态为 “未到”
     * @params [cargoReceipt]
     * @creator chenshuai
     * @date 2019/3/17 0017
     */
    @Override
    public boolean submit(CargoReceipt cargoReceipt) {

        try {
            //更新运输单
            cargoReceipt.setBackBillState("未到车辆");
            cargoReceiptDao.save(cargoReceipt);

            String goodsBillCode = cargoReceiptDetailDao.findByGoodsRevertBillId(cargoReceipt.getGoodsRevertBillCode()).getGoodsBillDetailId();
            /**获取运输单 事件*/
            GoodsBillEvent goodsBillEvent = goodsBillEventDao.findByGoodsBillId(goodsBillCode);

            goodsBillEvent.setEventName("未到");
            goodsBillEvent.setOccurTime(new Date());
            goodsBillEventDao.save(goodsBillEvent);
            return true;
        } catch (Exception e) {
            log.info("货运回执单提交失败" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String goodsRevertBillCode) {
        /**
         *@description 删除货运回执单
         *@params [goodsRevertBillCode]
         *@return boolean
         *@creator chenshuai
         *@date 2019/3/20 0020
         */
        BillInfo billInfo = billInfoDao.findByBillCode(goodsRevertBillCode);
        billInfo.setBillState("作废");
        billInfo.setWriteDate(new Date());
        try {
            CargoReceipt cargoReceipt = new CargoReceipt();
            cargoReceipt.setGoodsRevertBillCode(goodsRevertBillCode);
            cargoReceiptDao.delete(cargoReceipt);
            return true;
        } catch (Exception e) {
            log.info("货运回执单删除失败:" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.CargoReceipt>
     * @description 查询一个司机的所有未到运单
     * @params [driverId, backBillState, pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<CargoReceipt> findByDriverIdAndState(String driverId, String backBillState, Pageable pageable) {
        return cargoReceiptDao.findByDriverIdAndBackBillState(driverId, backBillState, pageable);
    }

    /**
     * @return java.util.List<?>
     * @description 查询未被安排的货运单
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<?> selectLeftCodes() {
        return cargoReceiptDetailDao.findLeftCodes();
    }

    /**
     * @return com.ansel.bean.CargoReceiptDetail
     * @description 查询货运回执单号
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public CargoReceiptDetail findByGoodsBillCode(String goodsBillCode) {
        return cargoReceiptDetailDao.findByGoodsBillDetailId(goodsBillCode);
    }

}
