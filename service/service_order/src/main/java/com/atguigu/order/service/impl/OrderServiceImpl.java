package com.atguigu.order.service.impl;

import com.atguigu.commonutils.vo.CourseWebVo;
import com.atguigu.commonutils.vo.UserMemberVo;
import com.atguigu.order.client.EduClient;
import com.atguigu.order.client.UcenterClient;
import com.atguigu.order.entity.Order;
import com.atguigu.order.mapper.OrderMapper;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;


    @Override
    public String createOrder(String userId, String courseId) {
        // 获取购买人信息
        UserMemberVo uerInfoOrder = ucenterClient.getUerInfoOrder(userId);
        // 获取课程信息
        CourseWebVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        // 订单信息
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setMemberId(userId);
        order.setMobile(uerInfoOrder.getMobile());
        order.setNickname(uerInfoOrder.getNickname());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
