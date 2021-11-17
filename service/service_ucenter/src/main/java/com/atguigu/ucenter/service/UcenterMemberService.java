package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entiy.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UcenterMemberService extends IService<UcenterMember> {
    String login(UcenterMember member);
}
