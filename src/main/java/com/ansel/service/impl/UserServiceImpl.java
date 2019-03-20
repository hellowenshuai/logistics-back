package com.ansel.service.impl;

import com.ansel.bean.User;
import com.ansel.dao.IUserDao;
import com.ansel.service.IUserService;
import com.ansel.util.Enctype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenshuai
 */
@Service(value = "userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    /**
     * @return java.util.Map<?,?>
     * @description 根据用户Id验证用户密码是否正确，进行登录验证; 登录成功后，置为上线
     * @params [loginId, password]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Map<?, ?> userLogin(String loginId, String password) {

        Map<String, Object> result = new HashMap<>();
        try {
            User user = userDao.findByLoginId(loginId);
            if (user.getPassword().equals(Enctype.MD5(password))) {
                // 上线
                userDao.updateOnline(true, loginId);
                result.put("STATUS", "SUCCESS");
                result.put("USER", user);
            } else {
                result.put("STATUS", "ERROR");
            }
            return result;
        } catch (Exception e) {
            // TODO: handle exception
            log.error("用户密码验证失败" + e.getMessage());
            result.put("STATUS", "ERROR");
            return result;
        }
    }

    /**
     * @return boolean
     * @description 判断该用户是否存在
     * @params [loginId]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean ifExist(String loginId) {

        // TODO Auto-generated method stub
        boolean flag = false;
        flag = userDao.findByLoginId(loginId)==null ? false : true;
        return flag;
    }

    /**
     * @return boolean
     * @description 修改用户密码 使用MD5加密
     * @params [loginId, oldPassword, newPassword]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean changePassword(String loginId, String oldPassword, String newPassword) {

        // TODO Auto-generated method stub
        User user = userDao.findByLoginId(loginId);
        if (Enctype.MD5(oldPassword).equals(user.getPassword())) {
            user.setPassword(Enctype.MD5(newPassword));
            userDao.save(user);
            return true;
        }
        return false;
    }


}
