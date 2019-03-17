package com.ansel.util;

import java.util.Random;

/**
 * @author chenshuai
 * @version 1.0
 *  *
 * @date 2019/3/14 0014 23:05
 * @description: 人员相关的一些操作
 */
public class AddPeopleUtils {
    /**
     * @return java.lang.String
     * @description 返回一个定长的字符串
     * @params []
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    public static String randomCode() {

        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public static String departmentPrefix(String department) {
        String result = "";
        if (department.equals("管理组")) {
            result = "GL";
        } else if (department.equals("票据组")) {
            result = "PJ";
        } else if (department.equals("财务组")) {
            result = "CW";
        } else if (department.equals("客服组")) {
            result = "KF";
        } else if (department.equals("监控组")) {
            result = "JK";
        }
        return result;
    }
}
