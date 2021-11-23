package com.atguigu.staservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    // 查询某一天注册人数，给统计提供接口
    @GetMapping("/educenter/member/registerCount/{day}")
    public Integer registerCountByDay(@PathVariable("day") String day);

}
