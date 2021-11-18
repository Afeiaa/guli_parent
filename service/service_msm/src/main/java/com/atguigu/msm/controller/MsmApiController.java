package com.atguigu.msm.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RandomUtil;
import com.atguigu.msm.service.MsmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/msm")
@CrossOrigin //跨域
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send/{phone}")    // 数据库中存了手机的验证码，让他一直存在，然后生成的后台打印出来，然后去使用
    public R sendCode(@PathVariable("phone") String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isAllEmpty(code)) return R.ok().data("code", code);
        code = RandomUtil.getFourBitRandom();
        System.out.println("######################################" + code);
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.sendCode(phone, "SMS_228015620", param);

        // 还剩余四条,代码没问题,存入redis也没问题
        // boolean isSend = msmService.sendCode1(phone);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok().data("code", code);
        }
        return R.error().message("短信发送失败！");
    }


}
