package com.atguigu.order.controller;

import com.atguigu.commonutils.R;
import com.atguigu.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/orderservice/paylog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // 1. 获取微信支付的 map 信息
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo") String orderNo) {
        Map<String, Object> map = payLogService.createNative(orderNo);
        System.out.println("============获取微信二维码的map：" + map);
        return R.ok().data(map);
    }

    // 2. 查询订单状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo") String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("============获取订单状态的map：" + map);
        if (map == null) {       // 出错
            return R.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            // 3. 更改订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中！");
    }

}
