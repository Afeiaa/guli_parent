package com.atguigu.eduservice.service.impl;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean deleteVideo(String videoId) {
        // 删除小节的视频,多个
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("id", videoId);
//        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);
        List<String> list = new ArrayList<>();
        for(EduVideo eduVideo : videoList) {
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
                list.add(eduVideo.getVideoSourceId());
            }
        }
        if(list.size() > 0) {
            R r = vodClient.deleteAliyunVideoBatch(list);
            System.out.println(r);
        }

        // 删除小节
        int result = baseMapper.deleteById(videoId);
        return result > 0;
    }
}
