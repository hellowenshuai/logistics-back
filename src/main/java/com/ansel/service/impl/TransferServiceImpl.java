package com.ansel.service.impl;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@Service(value = "transferService")
public class TransferServiceImpl implements ITransferService {

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

    @Override
    public boolean addCompany(TransferComInfo transferComInfo) {
        try {
            transferComInfoDao.save(transferComInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("中转公司信息添加失败");
            return false;
        }
    }

    @Override
    public Page<TransferComInfo> findAllByPage(Pageable pageable) {
        return transferComInfoDao.findAll(pageable);
    }

    @Override
    public List<GoodsBill> transferGoods(String type, String driverId) {
        List<GoodsBill> list = goodsBillDao.transferState(type, driverId);
        List<GoodsBill> result = new LinkedList<>();
        //判断中转情况
        for (GoodsBill goodsBill : list) {
            String[] citys = goodsBill.getTransferStation().split(",");
            if (transferInfoDao.findByGoodsBillCodeOrTransferStationContaining(goodsBill.getGoodsBillCode(), citys[citys.length - 1])==null) {
                result.add(goodsBill);
            }
        }
        return result;
    }

    @Override
    public TransferComInfo findByGoodsBillCode(String goodsBillCode) {
        GoodsBill goodsBill = goodsBillDao.findByGoodsBillCode(goodsBillCode);
        String[] citys = goodsBill.getTransferStation().split(",");
        TransferComInfo transferComInfo = new TransferComInfo();
        for (String string : citys) {
            if (transferInfoDao.findByGoodsBillCodeOrTransferStationContaining(goodsBillCode, string)==null) {
                transferComInfo = transferComInfoDao.findByCity(string);
                break;
            }
        }
        return transferComInfo;
    }

    @Override
    public boolean addTransferInfo(TransferInfo transferInfo) {
        try {
            transferInfoDao.save(transferInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("中转信息表插入失败");
            return false;
        }
    }

    @Override
    public List<GoodsBill> arriveGoods(String type, String driverId) {
        // TODO Auto-generated method stub
        List<GoodsBill> list = goodsBillDao.transferState(type, driverId);
        List<GoodsBill> result = new LinkedList<>();
        //判断中转情况
        for (int i = 0; i < list.size(); i++) {
            String[] citys = list.get(i).getTransferStation().split("，");
            String transferStation = "%"+citys[citys.length - 1]+"%";
            String goodsBillCode = list.get(i).getGoodsBillCode();
            System.out.println(transferStation);
            TransferInfo byGoodsBillCodeAndTransferStationContaining = transferInfoDao.findByGoodsBillCodeOrTransferStationContaining(goodsBillCode,transferStation);
            System.out.println(byGoodsBillCodeAndTransferStationContaining);
            if (transferInfoDao.findByGoodsBillCodeOrTransferStationContaining(goodsBillCode, transferStation)!=null) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public List<GoodsBill> findOnWayBills() {
        // TODO Auto-generated method stub
        List<GoodsBill> list = goodsBillDao.findOnWayBills();
        List<GoodsBill> result = new LinkedList<>();
        for (GoodsBill goodsBill : list) {
//			List<TransferInfo> infos = transferInfoDao.findByGoodsBillCode(goodsBill.getGoodsBillCode());
            CallbackInfo callbackInfo = callbackDao.findByGoodsBillIdAndType(goodsBill.getGoodsBillCode(), "中转回告");
//			String[] citys = goodsBill.getTransferStation().split(",");
            if (callbackInfo==null) {
                result.add(goodsBill);
            }
        }
        return result;
    }

    @Override
    public Page<TransferInfo> findInfoByPage(Pageable pageable) {
        return transferInfoDao.findAll(pageable);
    }

    @Override
    public boolean addCustomerReceiptInfo(CustomerReceiptInfo customerReceiptInfo) {
        try {
            customerReceiptInfoDao.save(customerReceiptInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("顾客回执信息添加失败");
            return false;
        }

    }

    @Override
    public Page<CustomerReceiptInfo> findCusRecPage(String customerCode, Pageable pageable) {
        return customerReceiptInfoDao.findByReceiveGoodsPerson(customerCode, pageable);
    }

}
