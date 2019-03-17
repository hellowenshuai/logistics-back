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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 * 司机相关操作
 */
@Transactional
@Service(value = "driverInfoService")
public class DriverInfoServiceImpl implements IDriverInfoService {

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
            System.err.println("司机信息表 | 用户信息表 | 用户与组表 插入失败");
            return false;
        }
    }

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
            System.err.println("司机信息 | 用户表 | 用户与组表 删除失败");
            return false;
        }
    }

    @Override
    public boolean updateById(String id, DriverInfo driverInfo) {

        DriverInfo oldDriverInfo = driverInfoDao.findById(id);
        driverInfo.setState(oldDriverInfo.getState());

        try {
            driverInfoDao.save(driverInfo);
            return true;
        } catch (Exception e) {
            System.err.println("司机信息更新失败");
            return false;
        }
    }

    @Override
    public DriverInfo findById(String id) {
        return driverInfoDao.findById(id);
    }



    @Override
    public List<String> findAllId() {
        return driverInfoDao.findAllId();
    }

}
