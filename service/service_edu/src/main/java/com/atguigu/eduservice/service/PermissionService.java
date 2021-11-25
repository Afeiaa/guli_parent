package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllMenu();
}
