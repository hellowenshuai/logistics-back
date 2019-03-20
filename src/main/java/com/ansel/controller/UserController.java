package com.ansel.controller;

import com.ansel.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author chenshuai
 * @version 1.0
 * @description 用户
 * @date 2019/3/17 0017 16:31
 */
@CrossOrigin
@RestController
@Api(value = "用户Controller")
@ApiIgnore
@ControllerAdvice
public class UserController extends ReturnType {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "用户登录验证", notes = "根据用户Id验证用户密码是否正确，进行登录验证; 登录成功后，置为上线")
    @ApiImplicitParam(name = "loginId", value = "用户Id", paramType = "Query", required = true, dataType = "String")
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public Map<?, ?> login(String loginId, String password) {

        Map<?, ?> result = userService.userLogin(loginId, password);
        return result;
    }

    @ApiOperation(value = "用户密码修改", notes = "需要用户id，旧密码，新密码，缺一不可")
    @RequestMapping(value = "/change", method = RequestMethod.PUT)
    public String change(String loginId, String oldPassword, String newPassword) {

        System.out.println(loginId + " " + oldPassword + " " + newPassword);
        boolean flag = false;
        flag = userService.changePassword(loginId, oldPassword, newPassword);
        if (!flag) {
            return ERROR;
        }
        return SUCCESS;
    }

}
