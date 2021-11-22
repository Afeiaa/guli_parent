package com.atguigu.order.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/orderservice/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. 创建订单
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId,
                         HttpServletRequest request) {
        String orderNo = orderService.createOrder(JwtUtils.getMemberIdByJwtToken(request), courseId);

        return R.ok().data("orderId", orderNo);
    }

    // 2. 根据订单的id获取订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable("orderId") String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order orderInfo = orderService.getOne(wrapper);
        // Order orderInfo = orderService.getById(orderId);
        return R.ok().data("item", orderInfo);
    }

    // 3. edu_service提供接口，查询用户对应课程的订单支付状态
    @GetMapping("/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable("memberId") String memberId,
                               @PathVariable("courseId") String courseId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("course_id", courseId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        return count > 0;
    }


}
