package com.ansel.service.impl;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.ICheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @author chenshuai
 */
@Transactional(rollbackFor = Exception.class)
@Service(value = "iCheckService")
public class CheckServiceImpl implements ICheckService {

    private static final Logger log = LoggerFactory.getLogger(CheckServiceImpl.class);

    @Autowired
    private IExtraIncomeDao extraIncomeDao;

    @Autowired
    private ICargoReceiptDao cargoReceiptDao;

    @Autowired
    private IFinanceFeeDao financeFeeDao;

    @Autowired
    private IManageFeeDao manageFeeDao;

    @Autowired
    private IEmployeeWageDao employeeWageDao;

    @Autowired
    private IExtraClearDao extraClearDao;

    @Autowired
    private IIncomeMonthlyTempDao iMonthlyTempDao;

    /**
     * @return boolean
     * @description 录入营业外收入
     * @params [extraIncome]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean save(ExtraIncome extraIncome) {
        try {
            extraIncomeDao.save(extraIncome);
            return true;
        } catch (Exception e) {
            log.error("营业外收入添加失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.ExtraIncome>
     * @description 查询所有营业外收入
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<ExtraIncome> selectAllExtra(Pageable pageable) {
        return extraIncomeDao.findAll(pageable);
    }

    /**
     * @return java.util.List<com.ansel.bean.ExtraIncome>
     * @description 非营业收入
     * @params [incomeMonth]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<ExtraIncome> selectByIncomeMonth(String incomeMonth) {
        return extraIncomeDao.findByIncomeMonth(incomeMonth);
    }

    /**
     * @return java.util.List<com.ansel.bean.CargoReceipt>
     * @description 运费、保险费（营业收入）
     * @params [beginTime, endTime]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<CargoReceipt> selectBySignTime(String beginTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date date1 = null;
        try {
            date = df.parse(beginTime);
            date1 = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cargoReceiptDao.findBySignTime(date, date1);
    }

    /**
     * @return boolean
     * @description 录入财务费用
     * @params [financeFee]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean save(FinanceFee financeFee) {
        try {
            financeFeeDao.save(financeFee);
            return true;
        } catch (Exception e) {
            log.error("财务费用添加失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.FinanceFee>
     * @description 查询所有财务费用
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<FinanceFee> selectAllFinance(Pageable pageable) {
        return financeFeeDao.findAll(pageable);
    }

    /**
     * @return java.util.List<com.ansel.bean.FinanceFee>
     * @description 财务费用
     * @params [payoutMonth]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<FinanceFee> selectByFPayoutMonth(String payoutMonth) {
        return financeFeeDao.findByPayoutMonth(payoutMonth);
    }

    /**
     * @return boolean
     * @description 录入管理费用
     * @params [manageFee]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean save(ManageFee manageFee) {
        try {
            manageFeeDao.save(manageFee);
            return true;
        } catch (Exception e) {
            log.error("管理费用添加失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.ManageFee>
     * @description 查询所有管理费用
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<ManageFee> selectAllManage(Pageable pageable) {
        return manageFeeDao.findAll(pageable);
    }

    /**
     * @return java.util.List<com.ansel.bean.ManageFee>
     * @description 管理费用
     * @params [payoutMonth]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<ManageFee> selectByMPayoutMonth(String payoutMonth) {
        return manageFeeDao.findByPayoutMonth(payoutMonth);
    }

    /**
     * @return com.ansel.bean.ManageFee
     * @description 根据id查询管理费用
     * @params [id]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public ManageFee selectByMId(int id) {
        return manageFeeDao.findById(id);
    }

    /**
     * @return boolean
     * @description 录入员工工资
     * @params [employeeWage]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean save(EmployeeWage employeeWage) {
        //依据当前员工id，从数据库中查询
        EmployeeWage employeeWage1 = employeeWageDao.findByEmployeeCode(employeeWage.getEmployeeCode());
        try {
            //说明数据库中已经有这个人的薪资情况，所以，应该是修改操作
            if (employeeWage1!=null) {
                employeeWage.setId(employeeWage1.getId());
            }
            employeeWageDao.save(employeeWage);
            return true;
        } catch (Exception e) {
            log.error("员工工资添加失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.EmployeeWage>
     * @description 查询所有员工工资
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<EmployeeWage> selectAllWage(Pageable pageable) {
        return employeeWageDao.findAll(pageable);
    }

    /**
     * @return java.util.List<com.ansel.bean.EmployeeWage>
     * @description 获取指定时间间隔 员工工资
     * @params [beginTime, endTime]
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public List<EmployeeWage> selectByDate(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date date1 = null;
        try {
            date = sdf.parse(beginTime);
            date1 = sdf.parse(endTime);
        } catch (ParseException e) {
            log.error("日期转换失败" + e.getMessage());
        }
        return employeeWageDao.findByDate(date, date1);
    }

    /**
     * @return com.ansel.bean.EmployeeWage
     * @description 根据员工编号查询员工工资
     * @params [employeeCode]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public EmployeeWage selectByEmployeeCode(String employeeCode) {
        return employeeWageDao.findByEmployeeCode(employeeCode);
    }

    /**
     * @return java.util.List<com.ansel.bean.ExtraClear>
     * @description 查询搬运费失败
     * @params [beginTime, endTime]
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public List<ExtraClear> selectByBalanceDate(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date date1 = null;
        try {
            date = sdf.parse(beginTime);
            date1 = sdf.parse(endTime);
        } catch (ParseException e) {
            log.error("查询搬运费失败" + e.getMessage());
        }
        return extraClearDao.findByBalanceDate(date, date1);
    }

    /**
     * @return com.ansel.bean.IncomeMonthlyTemp
     * @description 保存当前统计成本报表
     * @params []
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public IncomeMonthlyTemp save() {
        log.info("保存月报开始");
        //初始化一个日历类
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));

        String cMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String cYear = String.valueOf(calendar.get(Calendar.YEAR));
        if (cMonth.length()==1) {
            //如果是0-9 ，补充前一位
            cMonth = "0" + cMonth;
        }
        String cTime = cYear + "-" + cMonth;
        //本月开始日期
        String bTime = cTime + "-01";
        //本月结束日期
        String eTime = cTime + "-31";
        //运费
        double carriageMoney = 0;
        //保险费
        double insuranceMoney = 0;
        //非营业收入
        double unbizIncome = 0;
        double fFee = 0;
        double officeFee = 0;
        double houseRent = 0;
        double waterElecFee = 0;
        double phoneFee = 0;
        double other = 0;
        //工资
        double wage = 0;
        double balanceMoney = 0;
        double carCarriage = 0;
        //支出
        double payOut = 0;
        //总收入
        double income = 0;
        // 运费、保险费（营业收入）
        List<CargoReceipt> cargoReceipts = selectBySignTime(bTime, eTime);
        for (int i = 0; i < cargoReceipts.size(); i++) {
            carriageMoney += cargoReceipts.get(i).getAllCarriage();
            insuranceMoney += cargoReceipts.get(i).getInsurance();
        }
        log.info("运费营业收入查询结果:" + carriageMoney);
        log.info("保险费营业收入查询结果:" + insuranceMoney);
        // 非营业收入
        List<ExtraIncome> extraIncomes = selectByIncomeMonth(cTime);
        log.info("非营业收入查询结果:" + extraIncomes);
        for (int i = 0; i < extraIncomes.size(); i++) {
            unbizIncome += extraIncomes.get(i).getMoney();
        }
        // 收入
        income = carriageMoney + insuranceMoney + unbizIncome;
        log.info("总收入查询结果:" + income);
        // 财务费用
        List<FinanceFee> financeFees = selectByFPayoutMonth(cTime);
        System.out.println(1);
        for (int i = 0; i < financeFees.size(); i++) {
            fFee += financeFees.get(i).getFee();
        }
        log.info("财务费用:" + fFee);
        // 管理费用
        List<ManageFee> manageFees = selectByMPayoutMonth(cTime);
        for (int i = 0; i < manageFees.size(); i++) {
            officeFee += manageFees.get(i).getOfficeFee();
            houseRent += manageFees.get(i).getHouseRent();
            waterElecFee += manageFees.get(i).getWaterElecFee();
            phoneFee += manageFees.get(i).getPhoneFee();
            other += manageFees.get(i).getOtherPayout();
        }
        log.info("管理费用:");
        log.info("officeFee:" + officeFee);
        log.info("houseRent:" + houseRent);
        log.info("waterElecFee:" + waterElecFee);
        log.info("phoneFee:" + phoneFee);
        log.info("other:" + other);
        // 工资
        List<EmployeeWage> employeeWages = selectByDate(bTime, eTime);
        System.out.println(1);
        for (int i = 0; i < employeeWages.size(); i++) {
            wage += (employeeWages.get(i).getBasicWage() + employeeWages.get(i).getAllowance()
                    + employeeWages.get(i).getStationWage());
        }
        log.info("工资:" + wage);
        // 经营费用（搬运费、车运费）
        List<ExtraClear> extraClears = selectByBalanceDate(bTime, eTime);
        System.out.println(1);
        for (int i = 0; i < extraClears.size(); i++) {
            balanceMoney += extraClears.get(i).getBalanceMoney();
        }
        log.info("经营费用（搬运费、车运费）:" + balanceMoney);
        carCarriage = 500;
        log.info("经营费用:" + carCarriage);
        // 总支出
        payOut = carCarriage + balanceMoney + wage + officeFee + houseRent + waterElecFee + phoneFee + officeFee + fFee;
        log.info("总支出:" + payOut);
        try {
            IncomeMonthlyTemp iMonthlyTemp = selectByMonth(cTime);
            if (iMonthlyTemp==null) {
                iMonthlyTemp = new IncomeMonthlyTemp();
            }
            iMonthlyTemp.setMonth(cTime);
            iMonthlyTemp.setCarriageMoney(carriageMoney);
            iMonthlyTemp.setInsuranceMoney(insuranceMoney);
            iMonthlyTemp.setBizIncome(insuranceMoney + carriageMoney);
            iMonthlyTemp.setUnbizIncome(unbizIncome);
            iMonthlyTemp.setIncome(income);
            iMonthlyTemp.setFinanceFee(fFee);
            iMonthlyTemp.setOfficeFee(officeFee);
            iMonthlyTemp.setHouseRent(houseRent);
            iMonthlyTemp.setWaterElecFee(waterElecFee);
            iMonthlyTemp.setPhoneFee(phoneFee);
            iMonthlyTemp.setOther(other);
            iMonthlyTemp.setManageFee(officeFee + houseRent + waterElecFee + phoneFee + other);
            iMonthlyTemp.setWage(wage);
            iMonthlyTemp.setConveyWage(balanceMoney);
            iMonthlyTemp.setCarCarriage(carCarriage);
            iMonthlyTemp.setBizFee(carCarriage + balanceMoney);
            iMonthlyTemp.setPayout(payOut);
            iMonthlyTemp.setProfit(income - payOut);
            iMonthlyTempDao.save(iMonthlyTemp);
            return iMonthlyTemp;
        } catch (Exception e) {
            log.error("月报信息添加失败" + e.getMessage());
            return null;
        }
    }

    /**
     * @return com.ansel.bean.IncomeMonthlyTemp
     * @description 查询本月流水
     * @params [month]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public IncomeMonthlyTemp selectByMonth(String month) {
        return iMonthlyTempDao.findByMonth(month);
    }

    /**
     * @return com.ansel.bean.IncomeMonthlyTemp
     * @description 查询当前月报
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public IncomeMonthlyTemp selectAll() {
        return save();
    }

}
