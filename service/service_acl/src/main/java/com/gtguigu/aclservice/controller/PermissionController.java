package com.gtguigu.aclservice.controller;

import com.atguigu.commonutils.R;
import com.gtguigu.aclservice.entity.Permission;
import com.gtguigu.aclservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aaa/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // 1. 递归查询所有菜单
    @GetMapping("/all")
    public R queryAllMenu() {
        List<Permission> list = permissionService.queryAllMenu();
        return R.ok().data("items", list);
    }

}
