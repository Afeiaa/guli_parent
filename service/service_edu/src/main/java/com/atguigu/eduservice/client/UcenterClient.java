package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    @PostMapping("/educenter/member/getUserInfo")
    public R getUserInfo(HttpServletRequest request);

    @GetMapping("/educenter/member//getUserById/{id}")
    public R getUserById(@PathVariable("id") String id);
}
