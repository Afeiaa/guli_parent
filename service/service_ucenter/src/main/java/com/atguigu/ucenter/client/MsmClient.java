package com.atguigu.ucenter.client;


import com.atguigu.commonutils.R;
import com.atguigu.ucenter.client.fallback.MsmClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-msm", fallback = MsmClientFallback.class)
public interface MsmClient {

    @GetMapping(value = "/api/msm/send/{phone}")    // 数据库中存了手机的验证码，让他一直存在，然后生成的后台打印出来，然后去使用
    public R sendCode(@PathVariable("phone") String phone);

}
