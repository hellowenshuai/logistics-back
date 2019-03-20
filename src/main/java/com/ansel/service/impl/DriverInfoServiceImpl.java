package com.ansel.service.impl;

import com.ansel.bean.DriverInfo;
import com.ansel.bean.User;
import com.ansel.bean.UserWithGroup;
import com.ansel.dao.IDriverInfoDao;
import com.ansel.dao.IGroupDao;
import com.ansel.dao.IUserDao;
import com.ansel.dao.IUserWithGroupDao;
import com.ansel.service.IDriverInfoService;
import com.ansel.util.AddPeopleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenshuai
 * 司机相关操作
 */
@Transactional(rollbackFor = Exception.class)
@Service(value = "driverInfoService")
public class DriverInfoServiceImpl implements IDriverInfoService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private IDriverInfoDao driverInfoDao;

    @Autowired
    private IUserWithGroupDao userWithGroupDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IGroupDao groupDao;

    @Override
    public Page<DriverInfo> findAllByPage(Pageable pageable) {
        return driverInfoDao.findAll(pageable);
    }

    /**
     * @return boolean
     * @description 添加一个司机
     * @params [driverInfo]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean addNewDriver(DriverInfo driverInfo) {

        driverInfo.setState("空闲");
        String driverCode = "SJ";
        //判断编号是否重复
        while (true) {
            driverCode += AddPeopleUtils.randomCode();
            if (driverInfoDao.findById(driverCode)==null) {
                break;
            }
        }
        driverInfo.setId(driverCode);
        try {

            // 添加用户信息表
            User user = new User(driverCode, "E10ADC3949BA59ABBE56E057F20F883E", false);
            userDao.save(user);

            // 添加用户与组情况
            String department = "司机组";
            int groupId = groupDao.findByGroupName(department).getId();
            UserWithGroup userWithGroup = new UserWithGroup();
            userWithGroup.setGroupId(groupId);
            userWithGroup.setUserId(driverCode);
            userWithGroupDao.save(userWithGroup);

            driverInfoDao.save(driverInfo);
            return true;

        } catch (Exception e) {
            log.error("司机信息表 | 用户信息表 | 用户与组表 插入失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return boolean
     * @description 删除某个司机
     * @params [id]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean deleteById(String id) {

        DriverInfo driverInfo = driverInfoDao.findById(id);
        User user = userDao.findByLoginId(id);
        UserWithGroup userWithGroup = userWithGroupDao.findByUserId(id);
        try {
            driverInfoDao.delete(driverInfo);
            userDao.delete(user);
            userWithGroupDao.delete(userWithGroup);
            return true;
        } catch (Exception e) {
            log.error("司机信息 | 用户表 | 用户与组表 删除失败" + e.getMessage());
            return false;
        }
    }

    /**
     * @return boolean
     * @description 修改某个司机
     * @params [id, driverInfo]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean updateById(String id, DriverInfo driverInfo) {

        DriverInfo oldDriverInfo = driverInfoDao.findById(id);
        driverInfo.setState(oldDriverInfo.getState());

        try {
            driverInfoDao.save(driverInfo);
            return true;
        } catch (Exception e) {
            log.error("司机信息更新失败 " + e.getMessage());
            return false;
        }
    }

    /**
     * @return com.ansel.bean.DriverInfo
     * @description 查询单个司机详情
     * @params [id]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public DriverInfo findById(String id) {
        return driverInfoDao.findById(id);
    }

    /**
     * @return java.util.List<java.lang.String>
     * @description 查询所有司机编号
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<String> findAllId() {
        return driverInfoDao.findAllId();
    }

}
