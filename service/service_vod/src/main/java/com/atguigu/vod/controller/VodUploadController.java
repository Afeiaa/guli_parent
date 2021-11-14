package com.atguigu.vod.controller;


import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // 根据时评的id删除阿里云上的视频
    @DeleteMapping("/deleteVideo/{videoSourceId}")
    public R deleteAliyunVideo(@PathVariable("videoSourceId") String videoId) {
        boolean flag = vodUploadService.deleteAliyunVideo(videoId);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

}
