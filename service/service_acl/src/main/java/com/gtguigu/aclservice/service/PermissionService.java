package com.gtguigu.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gtguigu.aclservice.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllMenu();
}
