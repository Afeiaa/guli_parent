package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    // 1. 分页查询讲师
    HashMap indexTeacherPage(long current, long limit);
}
