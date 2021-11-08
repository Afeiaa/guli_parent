package com.atguigu.eduservice.controller;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

}
