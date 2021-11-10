package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 添加课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(20001, "添加失败~");
        }

        // 2. 添加课程简介
        String cid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean flag = eduCourseDescriptionService.save(eduCourseDescription);
        if (flag == false) {
            throw new GuliException(20001, "添加失败~");
        }

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        // 1. 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        // 2. 查询课程简介表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        System.out.println("#################################" + courseInfoVo);
        return courseInfoVo;
    }

    @Override
    public String updateCourseInfoById(CourseInfoVo courseInfoVo) {
        // 1. 课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int num = baseMapper.updateById(eduCourse);
        if (num <= 0) {
            throw new GuliException(20001, "修改失败课程~");
        }

        // 2. 简介表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean flag = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (flag == false) {
            throw new GuliException(20001, "修改失败简介~");
        }

        return courseInfoVo.getId();
    }
}
