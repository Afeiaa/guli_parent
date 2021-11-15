package com.atguigu.vod.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodUploadService {
    String uploadAliyunVideo(MultipartFile file);

    boolean deleteAliyunVideo(String videoId);

    // 根据id删除阿里云上的多个视频
    boolean deleteAliyunVideoBatch(List<String> videoSourceIdList);
}
