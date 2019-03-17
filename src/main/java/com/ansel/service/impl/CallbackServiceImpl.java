package com.ansel.service.impl;

import com.ansel.bean.CallbackInfo;
import com.ansel.dao.ICallbackDao;
import com.ansel.service.ICallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 */
@Transactional(rollbackFor = Exception.class)
@Service(value = "callbackService")
public class CallbackServiceImpl implements ICallbackService {

    @Autowired
    private ICallbackDao callbackDao;

    /**
     * @return boolean
     * @description
     * @params [callbackInfo]
     * @creator chenshuai
     * @date 2019/3/12 0012
     */
    @Override
    public boolean addInfo(CallbackInfo callbackInfo) {
        try {
            callbackDao.save(callbackInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("插入回告失败");
            return false;
        }
    }

    @Override
    public CallbackInfo findDetail(String goodsBillId, String type) {
        return callbackDao.findByGoodsBillIdAndType(goodsBillId, type);
    }

}
