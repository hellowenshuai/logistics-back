package com.ansel.service.impl;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.IMoniterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chenshuai
 * 监控处理
 */
@Service(value = "moniterService")
@Transactional(rollbackFor = Exception.class)
public class MoniterServiceImpl implements IMoniterService {

    private static final Logger log = LoggerFactory.getLogger(MoniterServiceImpl.class);

    @Autowired
    private IGoodsBillDao goodsBillDao;

    @Autowired
    private ICargoReceiptDetailDao cargoReceiptDetailDao;

    @Autowired
    private ICargoReceiptDao cargoReceiptDao;

    @Autowired
    private ICustomerAmountDao customerAmountDao;

    @Autowired
    private IDriverAmountDao driverAmountDao;

    @Autowired
    private IDriverClearDao driverClearDao;

    @Autowired
    private ICustomerBillClearDao customerBillClearDao;

    @Autowired
    private IContactsServiceDao contactsServiceDao;

    @Autowired
    private ILineOverallDao lineOverallDao;

    @Autowired
    private IDriverInfoDao driverInfoDao;

    @Autowired
    private ICarCostDao carCostDao;

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 预期未到运单
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> selectAllUnArrive() {

        List<GoodsBill> goodsBills = goodsBillDao.findAll();
        List<GoodsBill> goodsBillsUnArrive = new ArrayList<>();

        for (GoodsBill goodsBill : goodsBills) {
            log.info("goodsBill: " + goodsBill);
            //4.5
            CargoReceiptDetail cargoReceiptDetail = cargoReceiptDetailDao.findByGoodsBillDetailId(goodsBill.getGoodsBillCode());
            log.info("cargoReceiptDetail:" + cargoReceiptDetail);
            //4.6
            CargoReceipt cargoReceipt = cargoReceiptDao.findByGoodsRevertBillCode(cargoReceiptDetail.getGoodsRevertBillId());
            log.info("cargoReceipt:" + cargoReceipt);
            if (cargoReceipt==null) {
                continue;
            }
            // 到货时间
            Date arriveTime = cargoReceipt.getArriveTime();
            Date startCarryTime = cargoReceipt.getStartCarryTime();

            if (arriveTime!=null && startCarryTime!=null) {
                long difference = arriveTime.getTime() - startCarryTime.getTime();
                // 实际中转天数
                int transferDays = (int) difference / (3600 * 24 * 1000);
                // 中转次数
                int transferNumber = (int) (goodsBill.getTransferFee() / 1.3);
                if (transferDays > (transferNumber + 1)) {
                    goodsBillsUnArrive.add(goodsBill);
                }
            }


        }
        return goodsBillsUnArrive;
    }

    /**
     * @return java.util.List<com.ansel.bean.GoodsBill>
     * @description 滞留未取运单
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<GoodsBill> selectAllUnTake() {

        List<GoodsBill> goodsBills = goodsBillDao.findAll();
        List<GoodsBill> goodsBillsUnTake = new ArrayList<>();

        for (GoodsBill goodsBill : goodsBills) {
            // 4.5
            CargoReceiptDetail cargoReceiptDetail = cargoReceiptDetailDao.findByGoodsBillDetailId(goodsBill.getGoodsBillCode());
            // 4.6
            CargoReceipt cargoReceipt = cargoReceiptDao.findByGoodsRevertBillCode(cargoReceiptDetail.getGoodsRevertBillId());
            if (cargoReceipt==null) {
                continue;
            }
            // 到货时间
            Date arriveTime = cargoReceipt.getArriveTime();
            // 预计交货时间
            Date factDealDate = goodsBill.getFactDealDate();
            if (arriveTime!=null && factDealDate!=null) {
                long difference = arriveTime.getTime() - factDealDate.getTime();
                int day = (int) difference / (3600 * 24 * 1000);
                if (day >= 3) {
                    goodsBillsUnTake.add(goodsBill);
                }
            }
        }
        return goodsBillsUnTake;
    }

    /**
     * @return java.util.List<com.ansel.bean.CustomerAmount>
     * @description 打印客户用量
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<CustomerAmount> selectAllCusAcount() {

        List<Object[]> strings = goodsBillDao.find();
        List<CustomerAmount> customerAmounts = new ArrayList<>();

        for (Object[] string : strings) {

            CustomerAmount customerAmount = new CustomerAmount();
            customerAmount.setSendGoodsCustomer((String) string[0]);
            customerAmount.setCarriageTotal((double) string[1]);
            customerAmount.setInsuranceTotal((double) string[2]);
            customerAmount.setPieceAmountTotal(Integer.parseInt(String.valueOf(string[3])));

            customerAmountDao.save(customerAmount);
            customerAmounts.add(customerAmount);
        }
        return customerAmounts;
    }

    /**
     * @return java.util.List<com.ansel.bean.DriverAmount>
     * @description 打印司机用量
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<DriverAmount> selectAllDriAcount() {

        List<Object[]> strings = driverClearDao.find();
        List<DriverAmount> driverAmounts = new ArrayList<>();

        for (Object[] string : strings) {

            DriverAmount driverAmount = new DriverAmount();
            driverAmount.setDriverCode((String) string[0]);
            driverAmount.setCarryFeeTotal((double) string[1]);
            driverAmount.setAddCarriageTotal((double) string[2]);
            driverAmount.setTotal(Integer.parseInt(String.valueOf(string[3])));

            driverAmountDao.save(driverAmount);
            driverAmounts.add(driverAmount);
        }
        return driverAmounts;


    }

    /**
     * @return java.util.List<com.ansel.bean.ContactsService>
     * @description 打印往来业务用量
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<ContactsService> printAllContactsService() {

        List<GoodsBill> goodsBills = goodsBillDao.findAll();
        List<ContactsService> contactsServices = new ArrayList<>();

        for (GoodsBill goodsBill : goodsBills) {

            String goodsBillCode = goodsBill.getGoodsBillCode();
            log.info("1查询到" + goodsBillCode);

            ContactsService contactsService = new ContactsService();
            contactsService.setSendGoodsCustomer(goodsBill.getSendGoodsCustomer());
            contactsService.setGoodsBillCode(goodsBillCode);
            contactsService.setSendGoodsAddr(goodsBill.getSendGoodsAddr());
            contactsService.setReceiveGoodsAddr(goodsBill.getReceiveGoodsAddr());
            contactsService.setCarriage(goodsBill.getCarriage());
            contactsService.setInsurance(goodsBill.getInsurance());
            contactsService.setSendGoodsDate(goodsBill.getSendGoodsDate());

            CustomerBillClear customerBillClear = customerBillClearDao.findByGoodsBillCode(goodsBillCode);
            if (customerBillClear!=null) {
                contactsService.setBillMoney(customerBillClear.getBillMoney());
                contactsService.setMoneyReceivable(customerBillClear.getMoneyReceivable());
                contactsService.setReceivedMoney(customerBillClear.getReceivedMoney());

                contactsServiceDao.save(contactsService);
                log.info("2查询到" + contactsService.getGoodsBillCode());
                contactsServices.add(contactsService);
            }

        }
        return contactsServices;
    }

    /**
     * @return java.util.List<com.ansel.bean.LineOverall>
     * @description 打印专线整体
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<LineOverall> printAllLineOverall() {
        //当你不再需要该表时， 用 drop；当你仍要保留该表，但要删除所有记录时，
        // 用 truncate；当你要删除部分记录时（always with a WHERE clause), 用 delete.
        lineOverallDao.truncateTable();

        List<Object[]> strings = cargoReceiptDao.find();
        List<LineOverall> lineOveralls = new ArrayList<>();

        for (Object[] string : strings) {

            LineOverall lineOverall = new LineOverall();
            lineOverall.setLoadStation((String) string[0]);
            lineOverall.setDealGoodsStation((String) string[1]);
            lineOverall.setAllCarriageTotal((double) string[2]);
            lineOverall.setInsuranceTotal((double) string[3]);
            lineOverall.setTimes(Integer.parseInt(String.valueOf(string[4])));

            lineOverallDao.save(lineOverall);
            lineOveralls.add(lineOverall);
        }
        return lineOveralls;
    }

    /**
     * @return java.util.List<com.ansel.bean.CarCost>
     * @description 打印车辆成本
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<CarCost> printAllCarCost() {

        List<CarCost> carCosts = new ArrayList<>();
        List<DriverAmount> driverAmounts = selectAllDriAcount();
        for (DriverAmount driverAmount : driverAmounts) {
            String driverCode = driverAmount.getDriverCode();

            if (carCostDao.findByDriverCode(driverCode)==null) {
                CarCost carCost = new CarCost();
                carCost.setDriverCode(driverCode);

                DriverInfo driverInfo = driverInfoDao.findById(driverCode);
                if (driverInfo!=null) {
                    carCost.setCarNo(driverInfo.getCarNo());
                    carCost.setCarType(driverInfo.getCarType());
                    carCost.setAllowCarryWeight(driverInfo.getAllowCarryWeight());
                    carCost.setCarWidth(driverInfo.getCarWidth());
                    carCost.setGoodsHeight(driverInfo.getGoodsHeight());
                    carCost.setCarryFeeTotal(driverAmount.getCarryFeeTotal());
                    carCost.setAddCarriageTotal(driverAmount.getAddCarriageTotal());

                    carCostDao.save(carCost);
                    carCosts.add(carCost);
                }

            } else {
                carCosts.add(carCostDao.findByDriverCode(driverCode));
            }
        }
        return carCosts;
    }

    /**
     * @return com.ansel.bean.CarCost
     * @description 打印车辆成本-查询
     * @params [driverCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public CarCost selectByCode(String driverCode) {
        log.info("driverCode:" + driverCode);
        return carCostDao.findByDriverCode(driverCode);
    }

    /**
     * @return com.ansel.bean.ContactsService
     * @description 打印往来业务用量-查询
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public ContactsService selectByGoodsBillCode(String goodsBillCode) {
        return contactsServiceDao.findByGoodsBillCode(goodsBillCode);
    }

}
