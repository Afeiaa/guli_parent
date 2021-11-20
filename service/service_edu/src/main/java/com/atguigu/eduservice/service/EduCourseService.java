package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    String updateCourseInfoById(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    Page<EduCourse> getCourseList(long current, long limit, EduCourse eduCourse);

    HashMap indexCourseList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    HashMap indexCourseInfo(String courseId);
}
