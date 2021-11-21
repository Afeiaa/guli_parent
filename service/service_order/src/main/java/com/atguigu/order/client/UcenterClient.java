package com.atguigu.order.client;


import com.atguigu.commonutils.vo.UserMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    // 根据id返回订单的个人信息
    @GetMapping("/educenter/member/getUerInfoOrder/{userId}")
    public UserMemberVo getUerInfoOrder(@PathVariable("userId") String userId);

}
