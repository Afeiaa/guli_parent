package com.atguigu.order.client;


import com.atguigu.commonutils.vo.CourseWebVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    // 6. 获取课程信息
    @GetMapping("/eduservice/indexcourse/getCourseInfoOrder/{courseId}")
    public CourseWebVo getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
