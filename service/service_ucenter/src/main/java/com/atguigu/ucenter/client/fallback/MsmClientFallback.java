package com.atguigu.ucenter.client.fallback;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.ucenter.client.MsmClient;
import org.springframework.stereotype.Component;

@Component
public class MsmClientFallback implements MsmClient {


    @Override
    public R sendCode(String phone) {
        throw new GuliException(20001, "发送验证码的服务熔断！");
    }
}
