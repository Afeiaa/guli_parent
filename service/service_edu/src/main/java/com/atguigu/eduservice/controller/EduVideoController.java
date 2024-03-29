package com.atguigu.eduservice.controller;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

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
        // 删除小节的视频,单个
//        EduVideo eduVideo = eduVideoService.getById(videoId);
//        String videoSourceId = eduVideo.getVideoSourceId();
//        if (!StringUtils.isEmpty(videoSourceId)) {
//            vodClient.deleteAliyunVideo(videoSourceId);
//        }

        // 删除小节
//        boolean flag = eduVideoService.removeById(videoId);

        boolean flag = eduVideoService.deleteVideo(videoId);

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
