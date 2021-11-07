package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.service.EduCourseDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EduCourseDescriptionController {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

}
