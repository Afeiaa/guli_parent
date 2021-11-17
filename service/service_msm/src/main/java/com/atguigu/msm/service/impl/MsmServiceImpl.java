package com.atguigu.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.msm.service.MsmService;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;


import java.util.Map;


@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean sendCode(String phone, String SMS_228015620, Map<String, Object> param) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "11", "11");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "天天向上在线教育平台");
        request.putQueryParameter("TemplateCode", "SMS_228015620");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean sendCode1(String phone)  {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data ={ new NameValuePair("Uid", "阿飞专用"),
                        new NameValuePair("Key", "d41d8cd98f00b204e980"),
                        new NameValuePair("smsMob", phone),
                        new NameValuePair("smsText", "8888")};
        post.setRequestBody(data);

        try {
            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:"+statusCode);
            for(Header h : headers)
            {
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
            System.out.println(result); //打印返回消息状态
            post.releaseConnection();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}

