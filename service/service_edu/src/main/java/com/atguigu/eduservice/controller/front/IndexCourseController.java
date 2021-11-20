package com.atguigu.eduservice.controller.front;


import com.alibaba.excel.util.StringUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.utils.initVod;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexcourse")
public class IndexCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    // 1. 课程分页
    @PostMapping("/index/{page}/{limit}")
    public R index(@PathVariable("page") long page,
                   @PathVariable("limit") long limit,
                   @RequestBody CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page(page, limit);
        HashMap map = eduCourseService.indexCourseList(coursePage, courseFrontVo);
        return R.ok().data(map);
    }

    // 2. 课程详细信息
    @GetMapping("/courseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {
        // 返回课程详情+章节信息
        HashMap map = eduCourseService.indexCourseInfo(courseId);
        return R.ok()
                .data("course", map.get("courseIndexInfo"))
                .data("chapterVoList", map.get("characterList"));
    }

    // 3. 播放小节视频
    @GetMapping("/getPlayAuth/{videoSourceId}")
    public R getPlayAuth(@PathVariable("videoSourceId") String videoSourceId) {
        try {
            // 初始化对象
            DefaultAcsClient client = initVod.initVodClient("1", "1");
            // request
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 设置id
            request.setVideoId(videoSourceId);
            // response
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            if(StringUtils.isEmpty(playAuth)) {
                throw new GuliException(20001, "获取凭证失败！");
            }
            System.out.println(playAuth);
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001, "获取凭证失败,异常！");
        }

    }


}
