package com.ansel.service.impl;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.IClearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenshuai
 * 结算业务
 */
@Transactional(rollbackFor = Exception.class)
@Service(value = "clearService")
public class ClearServiceImpl implements IClearService {
    private static final Logger log = LoggerFactory.getLogger(ClearServiceImpl.class);

    // 货运回执单主表
    @Autowired
    private ICargoReceiptDao cargoReceiptDao;

    @Autowired
    private IDriverClearDao driverClearDao;
    //货运单事件表
    @Autowired
    private IGoodsBillEventDao goodsBillEventDao;
    //客户运单结算表
    @Autowired
    private ICustomerBillClearDao customerBillClearDao;
    //货运单主表
    @Autowired
    private IGoodsBillDao goodsBillDao;
    //代收货款结算表
    @Autowired
    private IProxyFeeClearDao proxyFeeClearDao;
    //杂费结算表
    @Autowired
    private IExtraClearDao extraClearDao;

    /**
     * @return java.util.List<com.ansel.bean.DriverClear>
     * @description 司机结算-返回未结的所有实体(实体中能填的属性都填好)
     * @params [eventName]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<DriverClear> selectDrclear(String eventName) {
        List<DriverClear> driverCleareds = new ArrayList(); // 已结
        List<DriverClear> driverUnClears = new ArrayList(); // 未结

        if ("已结合同".equals(eventName)) {
            List<CargoReceipt> cargoReceipts = cargoReceiptDao.findByBackBillState("已结合同");
            for (CargoReceipt cargoReceipt : cargoReceipts) {
                DriverClear driverClear = driverClearDao.findByBackBillCode(cargoReceipt.getGoodsRevertBillCode());
                driverCleareds.add(driverClear);
            }
            return driverCleareds;
        } else {
            List<CargoReceipt> cargoReceipts = cargoReceiptDao.findByBackBillState("未结合同");
            for (CargoReceipt cargoReceipt : cargoReceipts) {
                DriverClear temp = driverClearDao.findByBackBillCode(cargoReceipt.getGoodsRevertBillCode());
                if (temp==null) {
                    //封装司机结算实体
                    DriverClear driverClear = new DriverClear();
                    driverClear.setBackBillCode(cargoReceipt.getGoodsRevertBillCode());
                    driverClear.setDriverCode(cargoReceipt.getDriverId());
                    driverClear.setBalanceType("结出");
                    driverClear.setPrepayMoney(cargoReceipt.getStartAdvance()); // 预付金额
                    driverClear.setBindInsurance(cargoReceipt.getCarryGoodsInsurance()); // 定装保证金
                    driverClear.setDispatchServiceFee(cargoReceipt.getDispatchServiceFee()); // 配载服务费
                    driverClear.setInsurance(cargoReceipt.getInsurance());

                    driverClearDao.save(driverClear);
                    driverUnClears.add(driverClear);
                } else {
                    driverUnClears.add(temp);
                }
            }
            return driverUnClears;
        }

    }

    /**
     * @return com.ansel.bean.DriverClear
     * @description 司机结算-通过订单编号查询单个实体的已填所有信息
     * @params [cargoReceiptCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public DriverClear selectByCargoReceiptCode(String cargoReceiptCode) {
        return driverClearDao.findByBackBillCode(cargoReceiptCode);
    }

    /**
     * @return boolean
     * @description 司机结算
     * @params [driverClear]
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public boolean driClear(DriverClear driverClear) {
        try {
            // 承运费
            double carryFee = driverClear.getCarryFee();
            // 定装保证金
            double bindInsurance = driverClear.getBindInsurance();
            // 加运费
            double addCarriage = driverClear.getAddCarriage();
            double allCarriage = cargoReceiptDao.findByGoodsRevertBillCode(driverClear.getBackBillCode())
                    .getAllCarriage();
            // 余额
            double balance = carryFee + bindInsurance + addCarriage - driverClear.getPayedMoney();
            driverClear.setBalance(balance);
            double money = allCarriage + bindInsurance + addCarriage;
            log.info("输入的是：" + driverClear.getPayedMoney());
            log.info("money：" + money);
            if (money!=driverClear.getPayedMoney()) {
                driverClearDao.save(driverClear);
            } else {
                driverClearDao.save(driverClear);
                cargoReceiptDao.updateStateByCode("已结合同", driverClear.getBackBillCode());
            }
            return true;
        } catch (Exception e) {
            log.error("司机结算 插入失败！" + e.getMessage());
            return false;
        }
    }

    /**
     * @return java.util.List<com.ansel.bean.CustomerBillClear>
     * @description 客户运单结算
     * @params [eventName]
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public List<CustomerBillClear> selectCusclear(String eventName) {
        // 已结
        List<CustomerBillClear> customerBillClears = new ArrayList();
        // 未结
        List<CustomerBillClear> customerBillUnClears = new ArrayList();

        if ("已结运单".equals(eventName)) {
            List<GoodsBillEvent> goodsBillEvents = goodsBillEventDao.findByEventName("已结运单");
            for (GoodsBillEvent goodsBillEvent : goodsBillEvents) {
                CustomerBillClear customerBillClear = customerBillClearDao
                        .findByGoodsBillCode(goodsBillEvent.getGoodsBillId());
                customerBillClears.add(customerBillClear);
            }
            return customerBillClears;
        } else {
            List<GoodsBillEvent> goodsBillEvents = goodsBillEventDao.findByEventName("未结");
            for (GoodsBillEvent goodsBillEvent : goodsBillEvents) {
                GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillEvent.getGoodsBillId());
                CustomerBillClear temp = customerBillClearDao.findByGoodsBillCode(goodsBillEvent.getGoodsBillId());
                if (temp==null) {
                    CustomerBillClear customerBillClear = new CustomerBillClear();
                    customerBillClear.setCustomerCode(goodsBill.getSendGoodsCustomerNo()); // 客户编号
                    customerBillClear.setGoodsBillCode(goodsBillEvent.getGoodsBillId()); // 货运单编号
                    customerBillClear.setCarriageReduceFund(goodsBill.getReduceFund()); // 减款
                    customerBillClear.setInsurance(goodsBill.getInsurance()); // 保险费
                    customerBillClear.setPayKickback(goodsBill.getPayKickback()); // 付回扣
                    customerBillClear.setCarryGoodsFee(goodsBill.getCarryGoodsFee());// 送货费
                    customerBillClear.setBalanceType("结入"); // 结算类型

                    customerBillClearDao.save(customerBillClear);
                    customerBillUnClears.add(customerBillClear);
                } else {
                    customerBillUnClears.add(temp);
                }

            }
            return customerBillUnClears;
        }
    }

    /**
     * @return com.ansel.bean.CustomerBillClear
     * @description 客户结算-通过订单编号查询单个实体的已填所有信息
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public CustomerBillClear selectByBillCode(String goodsBillCode) {
        return customerBillClearDao.findByGoodsBillCode(goodsBillCode);
    }

    /**
     * @return boolean
     * @description 客户运单结算表
     * @params [customerBillClear]
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public boolean cusClear(CustomerBillClear customerBillClear) {
        log.info("customerBillClear:" + customerBillClear.toString());
        try {
            double billMoney = customerBillClear.getBillMoney(); // 本单
            double insurance = customerBillClear.getInsurance(); // 保险费
            double carriageReduceFund = customerBillClear.getCarriageReduceFund(); // 减款
            double prepayMoney = customerBillClear.getPrepayMoney(); // 预付金额
            double receivedMoney = customerBillClear.getReceivedMoney(); // 已收

            double moneyReceivable = billMoney + insurance - carriageReduceFund - prepayMoney; // 应收

            customerBillClear.setMoneyReceivable(moneyReceivable);

            double money = billMoney + insurance - carriageReduceFund;
            if (money!=receivedMoney) {
                log.debug("结算失败! ");
                customerBillClearDao.save(customerBillClear);
            } else {
                log.debug("结算成功! ");
                customerBillClearDao.save(customerBillClear);
                goodsBillEventDao.updateStateByCode("已结运单", customerBillClear.getGoodsBillCode());
            }
            return true;
        } catch (Exception e) {
            log.error("客户结算 插入失败! ");
            return false;
        }
    }

    /**
     * @return java.util.List<com.ansel.bean.ProxyFeeClear>
     * @description 代收结算-返回未结的所有实体(实体中能填的属性都填好)
     * @params [eventName]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<ProxyFeeClear> selectHelpclear(String eventName) {
        // 已结
        List<ProxyFeeClear> proxyFeeClears = new ArrayList();
        // 未结
        List<ProxyFeeClear> proxyFeeUnClears = new ArrayList();
//TODO 有疑惑  已结运单  已结代收
        if (eventName.equals("已结运单")) {
            List<GoodsBillEvent> goodsBillEvents = goodsBillEventDao.findByEventName("已结运单");
            for (GoodsBillEvent goodsBillEvent : goodsBillEvents) {
                // 找到货运单主表
                GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillEvent.getGoodsBillId());
                if (goodsBill.getHelpAcceptPayment()!=0) { // 是代收
                    ProxyFeeClear proxyFeeClear = proxyFeeClearDao.findByGoodsBillCode(goodsBillEvent.getGoodsBillId());
                    proxyFeeClears.add(proxyFeeClear);
                }
            }
            return proxyFeeClears;
        } else {
            List<GoodsBillEvent> goodsBillEvents = goodsBillEventDao.findByEventName("未结");
            for (GoodsBillEvent goodsBillEvent : goodsBillEvents) {
                // 找到货运单主表
                GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillEvent.getGoodsBillId());
                if (goodsBill.getHelpAcceptPayment()!=0) { // 是代收

                    ProxyFeeClear temp = proxyFeeClearDao.findByGoodsBillCode(goodsBillEvent.getGoodsBillId());
                    if (temp==null) {
                        ProxyFeeClear proxyFeeClear = new ProxyFeeClear();
                        proxyFeeClear.setGoodsBillCode(goodsBillEvent.getGoodsBillId()); // 货运单编号
                        proxyFeeClear.setCustomerCode(goodsBill.getSendGoodsCustomerNo()); // 客户名称
                        // 应收货款 算
                        proxyFeeClear.setFactReceiveFund(goodsBill.getHelpAcceptPayment()); // 实收货款
                        double goodsPayChange = goodsBill.getMoneyOfChangePay() - goodsBill.getHelpAcceptPayment(); // 变更
                        proxyFeeClear.setGoodsPayChange(goodsPayChange); // 变更
                        proxyFeeClear.setCommisionRate(0.02f); // 佣金率
                        // 已收佣金 填 TODO 佣金计算
                        // 应收佣金 算
                        // 结算时间 填
                        proxyFeeClearDao.save(proxyFeeClear);
                        proxyFeeUnClears.add(proxyFeeClear);
                    } else {
                        proxyFeeUnClears.add(temp);
                    }
                }

            }
            return proxyFeeUnClears;
        }
    }

    /**
     * @return com.ansel.bean.ProxyFeeClear
     * @description 代收结算-通过订单编号查询单个实体的已填所有信息
     * @params [goodsBillCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public ProxyFeeClear selectByGoodsBillCode(String goodsBillCode) {
        return proxyFeeClearDao.findByGoodsBillCode(goodsBillCode);
    }

    /**
     * @return boolean
     * @description 代收结算（结算成功之后，这个货运单状态就是已结运单）
     * @params [proxyFeeClear]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean helpClear(ProxyFeeClear proxyFeeClear) {
        try {
            double factReceiveFund = proxyFeeClear.getFactReceiveFund(); // 实收
            double commisionRate = proxyFeeClear.getCommisionRate(); // 佣金率
            double receivedCommision = proxyFeeClear.getReceivedCommision(); // 已收佣金

            double commisionReceivable = factReceiveFund * commisionRate - receivedCommision; // 应收佣金
            log.info("应收佣金:" + commisionReceivable);
            proxyFeeClear.setCommisionReceivable(commisionReceivable);
            // 1.应收佣金》已收佣金
            // 2.佣金率 》0 且 实收佣金=0
            // 3.应收佣金！=0
            if ((commisionRate * factReceiveFund > receivedCommision) || (commisionRate > 0 && factReceiveFund==0)
                    || (commisionReceivable!=0)) {
                proxyFeeClearDao.save(proxyFeeClear);
            } else {
                proxyFeeClearDao.save(proxyFeeClear);
                goodsBillEventDao.updateStateByCode("已结运单", proxyFeeClear.getGoodsBillCode());
            }
            return true;
        } catch (Exception e) {
            log.error("代收结算 插入失败！" + e.getMessage());
            return false;
        }
    }

    /**
     * @return boolean
     * @description 杂费结算  add
     * @params [extraClear]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean saveExtraClear(ExtraClear extraClear) {
        try {
            extraClearDao.save(extraClear);
            return true;
        } catch (Exception e) {
            log.error("杂费结算 插入失败！" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.ExtraClear>
     * @description 杂费结算  select
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<ExtraClear> selectAllExtraClearByPage(Pageable pageable) {
        return extraClearDao.findAll(pageable);
    }

}
