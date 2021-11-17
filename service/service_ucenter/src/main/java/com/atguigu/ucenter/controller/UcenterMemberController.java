package com.atguigu.ucenter.controller;


import com.atguigu.commonutils.R;
import com.atguigu.ucenter.entiy.UcenterMember;
import com.atguigu.ucenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember member) {
        String token = ucenterMemberService.login(member);
        if(StringUtils.isEmpty(token)) {
            return R.error().message("登录失败，请重试！");
        }
        return R.ok().data("token", token);
    }

}
