package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduCourseService eduCourseService;

    // 删除课程
    @DeleteMapping("/deleteCourse/{courseId}")
    public R deleteCourseById(@PathVariable("courseId") String courseId) {
        // 获取课程所有的chapterId, children中有全部的videoId
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);
        for (ChapterVo chapterVo : chapterVideoList) {
            for (VideoVo videoVo : chapterVo.getChildren()) {
                // 删除小节
                eduVideoService.deleteVideo(videoVo.getId());
            }
            // 删除章节
            eduChapterService.deleteChapterById(chapterVo.getId());
        }

        // 删除描述
        eduCourseDescriptionService.removeById(courseId);

        // 删除课程本身
        boolean flag = eduCourseService.removeById(courseId);

        // 删除视频  TODO

        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

    // 课程分页
    @PostMapping("/list/{current}/{limit}")
    public R pageQuery(@PathVariable("current") long current,
                       @PathVariable("limit")long limit,
                       @RequestBody EduCourse eduCourse) {
        Page<EduCourse> pageCourse = eduCourseService.getCourseList(current, limit, eduCourse);
        long total = pageCourse.getTotal();
        List<EduCourse> list = pageCourse.getRecords();
        return R.ok().data("total", total).data("list", list);
    }


    // 添加课程
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    // 获取课程表的课程信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("curseInfo", courseInfoVo);
    }

    // 更新课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = eduCourseService.updateCourseInfoById(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    @GetMapping("/getPublishCourse/{courseId}")
    public R getPublishCourseInfo(@PathVariable("courseId") String courseId) {
        CoursePublishVo publishCourseInfo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourseInfo", publishCourseInfo);
    }

    // 修改课程为发布状态
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId") String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean flag = eduCourseService.updateById(eduCourse);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

}
