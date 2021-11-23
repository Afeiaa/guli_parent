package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entiy.UcenterMember;
import com.atguigu.ucenter.entiy.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UcenterMemberService extends IService<UcenterMember> {
    String login(UcenterMember member);

    boolean register(RegisterVo registerVo);

    UcenterMember getWxMember(String openId);

    Integer registerCountByDay(String day);
}
