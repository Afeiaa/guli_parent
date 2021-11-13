package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getCoursePublishVoByCourseId(String courseId);

}
