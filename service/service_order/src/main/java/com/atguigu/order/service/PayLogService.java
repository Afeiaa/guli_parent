package com.atguigu.order.service;

import com.atguigu.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface PayLogService extends IService<PayLog> {
    // 1. 获取微信支付的 map 信息
    Map<String, Object> createNative(String orderNo);

    // 2. 查询订单状态
    Map queryPayStatus(String orderNo);

    // 3. 更新订单状态
    void updateOrderStatus(Map<String, String> map);
}
