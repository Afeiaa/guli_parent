package com.atguigu.oss.controller;


import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/avatar")
//    @Headers("Content-Type:application/json; charset=utf-8")
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        if (url == null) {
            return R.error().message("上传失败！").data("url", null);
        }
        return R.ok().data("url", url);
    }

}
