package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexteacher")
public class IndexTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    // 1. 分页查询讲师
    @GetMapping("/index/{current}/{limit}")
    public R indexTeacher(@PathVariable("current") long current,
                          @PathVariable("limit") long limit) {
        HashMap map = eduTeacherService.indexTeacherPage(current, limit);
        return R.ok().data(map);
    }

    // 2. 讲师详情+主讲课程
    @GetMapping("/teacherInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable("teacherId") String teacherId) {
        // 讲师详情
        EduTeacher teacherInfo = eduTeacherService.getById(teacherId);
        if(teacherId == null) {
            return R.error().message("不存在该讲师，请刷新重试！");
        }
        // 主讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);

        return R.ok().data("teacherInfo", teacherInfo).data("courseList", courseList);
    }


}
