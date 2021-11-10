package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("curseInfo", courseInfoVo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = eduCourseService.updateCourseInfoById(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

}
