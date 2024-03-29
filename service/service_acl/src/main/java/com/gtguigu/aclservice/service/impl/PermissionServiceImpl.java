package com.gtguigu.aclservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gtguigu.aclservice.entity.Permission;
import com.gtguigu.aclservice.mapper.PermissionMapper;
import com.gtguigu.aclservice.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {



    /***********************************递归查询所有菜单***********************************/
    @Override
    public List<Permission> queryAllMenu() {
        // 查询出所有菜单
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);
        // 调用方法，得到遍历后的菜单
        List<Permission> list = buildPermission(permissions);

        return list;
    }

    private static List<Permission> buildPermission(List<Permission> permissions) {
        List<Permission> finalNode = new ArrayList<>();
        for (Permission it : permissions) {
            // 一级菜单
            if("0".equals(it.getPid())) {
                it.setLevel(1);
                it.getChildren().add(permissionChildren(permissions, it));
                finalNode.add(it);
            }
        }
        return finalNode;
    }

    private static Permission permissionChildren(List<Permission> permissions, Permission it) {
        for (Permission permission : permissions) {
            if (permission.getPid().equals(it.getId())) {
                permission.setLevel(it.getLevel() + 1);
                it.getChildren().add(permissionChildren(permissions, permission));
            }
        }
        return it;
    }



}
