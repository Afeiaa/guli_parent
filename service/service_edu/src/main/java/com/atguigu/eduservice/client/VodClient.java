package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod")
public interface VodClient {

    // 根据id删除阿里云上的单个视频
    @DeleteMapping("/eduvod/video/deleteVideo/{videoSourceId}")
    public R deleteAliyunVideo(@PathVariable("videoSourceId") String videoId);

    // 根据id删除阿里云上的多个视频
    @DeleteMapping("/eduvod/video/deleteVideo")
    public R deleteAliyunVideoBatch(@RequestParam("videoSourceIdList") List<String> videoSourceIdList);

}
