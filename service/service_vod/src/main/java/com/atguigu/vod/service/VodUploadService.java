package com.atguigu.vod.service;


import org.springframework.web.multipart.MultipartFile;

public interface VodUploadService {
    String uploadAliyunVideo(MultipartFile file);

    boolean deleteAliyunVideo(String videoId);
}
