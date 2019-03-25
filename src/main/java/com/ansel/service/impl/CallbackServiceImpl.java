package com.ansel.service.impl;

import com.ansel.bean.CallbackInfo;
import com.ansel.dao.ICallbackDao;
import com.ansel.service.ICallbackService;
import com.ansel.util.SendMsgWebChinese;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenshuai
 */
@Transactional(rollbackFor = Exception.class)
@Service(value = "callbackService")
public class CallbackServiceImpl implements ICallbackService {

    private static final Logger log = LoggerFactory.getLogger(CallbackServiceImpl.class);


    @Autowired
    private ICallbackDao callbackDao;

    /**
     * @return boolean
     * @description 回告 - 添加一条回告信息
     * @params [callbackInfo]
     * @creator chenshuai
     * @date 2019/3/12 0012
     */
    @Override
    public boolean addInfo(CallbackInfo callbackInfo) {
        try {
            callbackDao.save(callbackInfo);
            String phone = callbackInfo.getDialNo();
            String content = callbackInfo.getContent();
            boolean b = SendMsgWebChinese.SendMsg(phone, content);
            if (b) {
                log.info("发件人：" + callbackInfo.getWriter() + "给" + phone + "发" + content + ",回告类型：" + callbackInfo.getType());
            }
            return true;
        } catch (Exception e) {
            log.error("插入回告失败:" + e.getMessage());
            return false;
        }
    }

    /**
     * @return com.ansel.bean.CallbackInfo
     * @description 查询某个回告详情
     * @params [goodsBillId, type]
     * @creator chenshuai
     * @date 2019/3/19 0019
     */
    @Override
    public CallbackInfo findDetail(String goodsBillId, String type) {
        return callbackDao.findByGoodsBillIdAndType(goodsBillId, type);
    }

}
