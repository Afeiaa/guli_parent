package com.atguigu.eduservice.controller.front;


import com.alibaba.excel.util.StringUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontVo.CourseWebVo;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.utils.initVod;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
    @RequestMapping("/eduservice/indexcourse")
public class IndexCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private OrderClient orderClient;


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
    public R getCourseInfo(@PathVariable("courseId") String courseId,
                           HttpServletRequest request) {
        // 返回课程详情+章节信息
        HashMap map = eduCourseService.indexCourseInfo(courseId);
        // 查询支付状态
        Boolean isBuy = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);
        return R.ok()
                .data("course", map.get("courseIndexInfo"))
                .data("chapterVoList", map.get("characterList"))
                .data("isBuy", isBuy);
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

    // 4. 查询评论  ---->   直接迁移到课程信息中,一个请求获取课程页面所需要的的信息
    @GetMapping("/getComment/{page}/{limit}")
    public R getCourseComment(@PathVariable("page") long page,
                              @PathVariable("limit") long limit,
                              @RequestParam("courseId") String courseId) {
        Page<EduComment> commentPagePage  = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        eduCommentService.page(commentPagePage, wrapper);

        List<EduComment> records = commentPagePage.getRecords();
        long current = commentPagePage.getCurrent();
        long size = commentPagePage.getSize();
        long total = commentPagePage.getTotal();
        long pages = commentPagePage.getPages();
        boolean hasNext = commentPagePage.hasNext();
        boolean hasPrevious = commentPagePage.hasPrevious();

        return R.ok().data("items", records)
                .data("current", current)
                .data("size", size)
                .data("total", total)
                .data("pages", pages)
                .data("hasNext", hasNext)
                .data("hasPrevious", hasPrevious);
    }

    // 5. 添加评论
    @PostMapping("/addComment")
    public R addComment(@RequestBody EduComment eduComment) {
        // 查询评论人
        R r = ucenterClient.getUserById(eduComment.getMemberId());
        Map<String, Object> data = r.getData();
        System.out.println("****************************" + data);
        String nickname = (String) data.get("nickname");
        System.out.println("######################################" + nickname);
        String avatar = (String) data.get("avatar");
        eduComment.setNickname(nickname);
        eduComment.setAvatar(avatar);
        boolean result = eduCommentService.save(eduComment);
        if(result == false) {
            return R.error().message("评论失败！");
        }
        return R.ok();
    }

    // 6. 获取课程信息
    @GetMapping("/getCourseInfoOrder/{courseId}")
    public CourseWebVo getCourseInfoOrder(@PathVariable("courseId") String courseId) {
        CourseWebVo courseWebVo = eduCourseService.getCourseInfoOrder(courseId);
        return courseWebVo;
    }

}
