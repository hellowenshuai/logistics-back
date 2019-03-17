package com.ansel.service.impl;

import com.ansel.bean.CustomerInfo;
import com.ansel.bean.User;
import com.ansel.bean.UserWithGroup;
import com.ansel.dao.ICustomerDao;
import com.ansel.dao.IGroupDao;
import com.ansel.dao.IUserDao;
import com.ansel.dao.IUserWithGroupDao;
import com.ansel.service.ICustomerService;
import com.ansel.util.AddPeopleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 * 客户服务
 */
@Service(value = "customerService")
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private IGroupDao groupDao;

    @Autowired
    private IUserWithGroupDao userWithGroupDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public boolean save(CustomerInfo customer) {
        try {
            String customerCode = "KH";
            while (true) {
                customerCode += AddPeopleUtils.randomCode();
                if (customerDao.findByCustomerCode(customerCode)==null) {
                    break;
                }
            }

            // 添加用户信息表
            User user = new User(customerCode, "E10ADC3949BA59ABBE56E057F20F883E", false);
            userDao.save(user);

            // 添加用户与组情况
            String department = "客户组";
            int groupId = groupDao.findByGroupName(department).getId();
            UserWithGroup userWithGroup = new UserWithGroup();
            userWithGroup.setGroupId(groupId);
            userWithGroup.setUserId(customerCode);
            userWithGroupDao.save(userWithGroup);

            // 添加客户表
            customer.setCustomerCode(customerCode);
            customerDao.save(customer);

            return true;
        } catch (Exception e) {
            System.err.println("客户 | 用户组关系表 | 用户 信息插入失败！");
            return false;
        }
    }

    @Override
    public boolean delete(String customerCode) {

        // 共删除3张表
        try {
            // 用户表
            User user = userDao.findByLoginId(customerCode);
            userDao.delete(user);

            // 用户与组表
            UserWithGroup userWithGroup = userWithGroupDao.findByUserId(customerCode);
            userWithGroupDao.delete(userWithGroup);

            // 客户表
            CustomerInfo customer = new CustomerInfo();
            customer.setCustomerCode(customerCode);
            customerDao.delete(customer);
            return true;
        } catch (Exception e) {
            System.err.println("客户 | 用户组关系表 | 用户 信息删除失败！");
            return false;
        }
    }

    @Override
    public boolean update(String customerCode, CustomerInfo customer) {
        CustomerInfo temp = customerDao.findByCustomerCode(customerCode);
        customer.setCustomerCode(customerCode);
        customer.setCustomer(temp.getCustomer());
        try {
            customerDao.save(customer);
            return true;
        } catch (Exception e) {
            System.err.println("顾客信息更新失败");
            return false;
        }
    }

    @Override
    public Page<CustomerInfo> selectAllCusByPage(Pageable pageable) {
        return customerDao.findAll(pageable);
    }

    @Override
    public CustomerInfo selectByCustomerCode(String customerCode) {
        return customerDao.findByCustomerCode(customerCode);
    }

    @Override
    public List<String> selectAllCusCode() {
        return customerDao.findAllCustomerCode();
    }


}
