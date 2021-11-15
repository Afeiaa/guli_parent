package com.atguigu.vod.controller;


import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodUploadController {

    @Autowired
    private VodUploadService vodUploadService;

    // 上传视频到阿里云视频点播
    @PostMapping("/uploadVideo")
    public R uploadAliyunVideo(@RequestParam MultipartFile file) {
        String videoId = vodUploadService.uploadAliyunVideo(file);
        return R.ok().data("videoId", videoId);
    }

    // 根据id删除阿里云上的单个视频
    @DeleteMapping("/deleteVideo/{videoSourceId}")
    public R deleteAliyunVideo(@PathVariable("videoSourceId") String videoId) {
        boolean flag = vodUploadService.deleteAliyunVideo(videoId);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

    // 根据id删除阿里云上的多个视频
    @DeleteMapping("/deleteVideo")
    public R deleteAliyunVideoBatch(@RequestParam("videoSourceIdList") List<String> videoSourceIdList) {
        boolean flag = vodUploadService.deleteAliyunVideoBatch(videoSourceIdList);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }


}
