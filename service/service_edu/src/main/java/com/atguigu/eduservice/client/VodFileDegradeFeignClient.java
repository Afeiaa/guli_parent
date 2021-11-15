package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteAliyunVideo(String videoId) {
        return R.error().message("删除视频失败，执行熔断！");
    }

    @Override
    public R deleteAliyunVideoBatch(List<String> videoSourceIdList) {
        return R.error().message("批量删除视频失败，执行熔断！");
    }
}
