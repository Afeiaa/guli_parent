package com.atguigu.ucenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.ucenter.entiy.UcenterMember;
import com.atguigu.ucenter.entiy.vo.RegisterVo;
import com.atguigu.ucenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    // 登录，并返回token
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember member) {
        String token = ucenterMemberService.login(member);
        if(StringUtils.isEmpty(token)) {
            return R.error().message("登录失败，请重试！");
        }
        return R.ok().data("token", token);
    }

    // https://edu-guli-973.oss-cn-shanghai.aliyuncs.com/u%3D2237039644%2C3735368368%26fm%3D26%26fmt%3Dauto.jfif
    // 注册
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        boolean flag = ucenterMemberService.register(registerVo);
        if(flag == false) {
            return R.error().message("注册失败！");
        }
        return R.ok();
    }

    // 根据token，返回用户信息
    @PostMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberIdByJwtToken);
        if(member == null) {
            return R.error().message("获取用户信息失败，请重新登录！");
        }
        return R.ok().data("userInfo", member);
    }

}
