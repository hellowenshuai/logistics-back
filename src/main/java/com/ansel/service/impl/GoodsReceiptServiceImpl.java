package com.ansel.service.impl;

import com.ansel.bean.GoodsReceiptInfo;
import com.ansel.dao.IGoodsReceiptInfoDao;
import com.ansel.service.IGoodsReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author chenshuai
 */
@Service(value = "goodsReceiptService")
@Transactional(rollbackFor = Exception.class)
public class GoodsReceiptServiceImpl implements IGoodsReceiptService {

    private static final Logger log = LoggerFactory.getLogger(GoodsReceiptServiceImpl.class);

    @Autowired
    private IGoodsReceiptInfoDao goodsReceiptInfoDao;

    /**
     * @return boolean
     * @description 新增一条司机回执信息
     * @params [goodsReceiptInfo]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean add(GoodsReceiptInfo goodsReceiptInfo) {
        try {
            goodsReceiptInfoDao.save(goodsReceiptInfo);
            return true;
        } catch (Exception e) {
            log.error("货物回执信息添加失败" + e.getMessage());
            return false;
        }
    }

}
