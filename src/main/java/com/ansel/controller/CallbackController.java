package com.ansel.controller;

import com.ansel.bean.CallbackInfo;
import com.ansel.service.ICallbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenshuai
 * @version 1.0
 * @description 回告
 * @date 2019/3/17 0017 16:31
 */
@CrossOrigin
@RestController
@ControllerAdvice
@Api(value = "回告 controller")
@RequestMapping(value = "/callback")
public class CallbackController extends ReturnType {

    @Autowired
    private ICallbackService callbackService;

    @ApiOperation(value = "回告", notes = "回告 - 添加一条回告信息")
    @RequestMapping(value = "/add/{goodsBillId}", method = RequestMethod.POST)
    public String addInfo(@PathVariable("goodsBillId") String goodsBillId, CallbackInfo callbackInfo) {

        boolean flag = false;
        flag = callbackService.addInfo(callbackInfo);
        if (!flag) {
            return ERROR;
        }
        return SUCCESS;
    }

    @ApiOperation(value = "查询一条回告信息")
    @RequestMapping(value = "/findDetail/{goodsBillId}/{type}", method = RequestMethod.GET)
    public CallbackInfo findDetail(@PathVariable("goodsBillId") String goodsBillId, @PathVariable("type") String type) {

        CallbackInfo callbackInfo = callbackService.findDetail(goodsBillId, type);
        return callbackInfo;

    }

}
