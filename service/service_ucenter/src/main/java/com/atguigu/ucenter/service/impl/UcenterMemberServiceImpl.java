package com.atguigu.ucenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.ucenter.entiy.UcenterMember;
import com.atguigu.ucenter.mapper.UcenterMemberMapper;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Override
    public String login(UcenterMember member) {
        // 传参不能为null
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "请输入账号或密码！");
        }

        // mobile存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if(ucenterMember == null) {
            throw new GuliException(20001, "账号不存在！");
        }

        // 判断密码是否对
        String ucenterPassword = ucenterMember.getPassword();
        if(!MD5.encrypt(password).equals(ucenterPassword)) {    // java 字符串内容的比较不能用 == ，要使用equals
            throw new GuliException(20001, "密码错误，请重试！");
        }

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }
}
