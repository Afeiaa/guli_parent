package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    // 通过Id获取小节信息
    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId) {
        EduVideo videoInfo = eduVideoService.getById(videoId);
        return R.ok().data("videoInfo", videoInfo);
    }

    // 添加小节,需要章节id,课程id
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.save(eduVideo);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

    // 删除小节
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId) {
        boolean flag = eduVideoService.removeById(videoId);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

    // 修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.updateById(eduVideo);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

}
