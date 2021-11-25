package com.gtguigu.aclservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gtguigu.aclservice.entity.User;
import com.gtguigu.aclservice.mapper.UserMapper;
import com.gtguigu.aclservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
