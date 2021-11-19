package com.atguigu.ucenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.ucenter.client.MsmClient;
import com.atguigu.ucenter.entiy.UcenterMember;
import com.atguigu.ucenter.entiy.vo.RegisterVo;
import com.atguigu.ucenter.mapper.UcenterMemberMapper;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String > redisTemplate;

    @Autowired
    private MsmClient msmClient;

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

    @Override
    public boolean register(RegisterVo registerVo) {
        // 必填手机号和密码
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickname();
        String phoneCode = registerVo.getCode();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName)) {
            throw new GuliException(20001, "请输入手机号或密码！");
        }
        // 验证码
        // 先调用,后验证,  返回成功代表在redis中有值了,直接取
        // R r = msmClient.sendCode(mobile);
        // if(r.getCode() == 20001) {
        //     throw new GuliException(20001, "验证码错误，发送错误！");
        // }
        String code = null;
        try {
            code = redisTemplate.opsForValue().get(mobile);      //  如果redis中没有该数据，会报空指针异常
            if(!code.equals(phoneCode)) {
                throw new GuliException(20001, "验证码错误，输入错误！");
            }
        } catch (NullPointerException e) {
            throw new GuliException(20001, "请重新获取验证码！");
        }

        // 手机号是否存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new GuliException(20001, "账号已存在！");
        }
        // 正式注册
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickName);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        // 默认头像
        ucenterMember.setAvatar("https://edu-guli-973.oss-cn-shanghai.aliyuncs.com/u%3D2237039644%2C3735368368%26fm%3D26%26fmt%3Dauto.jfif");
        int result = baseMapper.insert(ucenterMember);
        return result > 0;
    }

    @Override
    public UcenterMember getWxMember(String openId) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openId);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }
}
