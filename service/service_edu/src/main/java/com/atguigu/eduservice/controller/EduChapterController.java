package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class EduChapterController implements Serializable {

    // 版本控制
    private static final long serialVersionUID = 1L;

    @Autowired
    private EduChapterService eduChapterService;

}
