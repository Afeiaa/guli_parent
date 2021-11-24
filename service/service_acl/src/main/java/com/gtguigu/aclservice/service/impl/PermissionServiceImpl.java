package com.gtguigu.aclservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gtguigu.aclservice.entity.Permission;
import com.gtguigu.aclservice.mapper.PermissionMapper;
import com.gtguigu.aclservice.service.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
