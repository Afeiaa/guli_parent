package com.atguigu.eduservice.controller;

import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

}
