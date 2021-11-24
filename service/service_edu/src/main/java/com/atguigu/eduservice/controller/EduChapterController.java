package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //课程大纲列表,根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId) {
        List<ChapterVo> characterList = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", characterList);
    }

    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.save(eduChapter);
        if (flag == true ) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/updateChapter")
    public R updateChapterById(@RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.updateChapterById(eduChapter);
        if (flag == true ) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/getChapter/{chapterId}")
    public R getChapterById(@PathVariable("chapterId") String chapterId) {
        EduChapter eduChapter = eduChapterService.getChapterById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapterById(@PathVariable("chapterId") String chapterId) {
        boolean flag = eduChapterService.deleteChapterById(chapterId);
        if (flag == true ) {
            return R.ok();
        }
        return R.error();
    }


}
