package com.gtguigu.aclservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gtguigu.aclservice.entity.RolePermission;
import com.gtguigu.aclservice.mapper.RolePermissionMapper;
import com.gtguigu.aclservice.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
