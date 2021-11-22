package com.atguigu.eduservice.client;

import org.springframework.stereotype.Component;

@Component    // 不要忘记
public class OrderClientFallback implements OrderClient {
    @Override
    public Boolean isBuyCourse(String memberId, String courseId) {
        return null;
    }
}
