package com.ansel.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author chenshuai
 * @version 1.0
 * @description 发送短信工具类
 * @date 2019/3/25 0025 21:48
 */
public class SendMsgWebChinese {

    public static boolean SendMsg(String phone,String content) throws Exception {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data = {
                new NameValuePair("Uid", "chenshuai02"),
                new NameValuePair("Key", "d41d8cd98f00b204e980"),
                new NameValuePair("smsMob", phone),
                new NameValuePair("smsText", content)};
        post.setRequestBody(data);

        client.executeMethod(post);

        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        if (statusCode==200) {
            return true;
        }
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result); //打印返回消息状态

        post.releaseConnection();
        return false;
    }
}
